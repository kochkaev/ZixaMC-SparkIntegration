package ru.kochkaev.zixamc.sparkintegration

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import me.lucko.spark.common.heapdump.HeapDump
import me.lucko.spark.common.monitor.memory.MemoryInfo
import net.fabricmc.api.ModInitializer
import net.fabricmc.loader.api.FabricLoader
import net.minecraft.server.MinecraftServer
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import ru.kochkaev.zixamc.sparkintegration.Config.Companion.config
import ru.kochkaev.zixamc.api.Initializer
import ru.kochkaev.zixamc.api.telegram.ServerBot.bot
import ru.kochkaev.zixamc.api.sql.SQLGroup
import ru.kochkaev.zixamc.api.formatLang
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class ZixaMCSparkIntegration : ModInitializer {

    companion object {
        val logger: Logger = LoggerFactory.getLogger("ZixaMCSparkIntegration")
        private val scheduler = Executors.newScheduledThreadPool(1)
        val groups: List<SQLGroup>
            get() = SQLGroup.groups.fold(arrayListOf<SQLGroup>()) { aac, sql ->
                if (sql.features.contains(SparkFeatureType)) {
                    aac.add(sql)
                }
                aac
            }
        private var isAlive = true

        fun tick() {
            val memoryUsage = getMemoryUsage()
            if (memoryUsage >= config.memoryThreshold) {
                isAlive = false
                logger.error("[ZixaMC Spark Integration] Out of memory detected!")
                FabricLoader.getInstance().gameDir.resolve("spark_heapdumps/").toFile().mkdirs()
                val filename = "heapdump-${LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HHmmssSSS"))}.hprof"
                HeapDump.dumpHeap(FabricLoader.getInstance().gameDir.resolve("spark_heapdumps/$filename"), false)
                logger.warn("[ZixaMC Spark Integration] Heap dump was saved to ./spark_heapdumps/$filename")
                groups.forEach {
                    Initializer.coroutineScope.launch {
                        bot.sendMessage(
                            chatId = it.id,
                            messageThreadId = it.features.getCasted(SparkFeatureType)?.topicId,
                            text = config.outOfMemoryReport.formatLang(
                                "filename" to filename,
                            )
                        )
                    }
                }
                FabricLoader.getInstance().gameInstance.let {
                    if (it is MinecraftServer) Initializer.coroutineScope.launch {
                        it.playerManager.broadcast(config.outOfMemorySay.getMinecraft(), false)
                        ChatSyncIntegration.sendAsSay("Server", config.outOfMemorySayTelegram)
                        logger.warn("[ZixaMC Spark Integration] Server will be stopped in ${config.delayBeforeStop.toDouble()/1000.0} seconds...")
                        delay(config.delayBeforeStop)
                        ArrayList(it.playerManager.playerList).forEach { player ->
                            player.networkHandler.disconnect(config.outOfMemoryKickReason.getMinecraft())
                        }
                        logger.error("[ZixaMC Spark Integration] Stopping the server!")
                        it.stop(false)
                    }
                }
            }
        }
        fun scheduleTick() {
            scheduler.scheduleAtFixedRate({ if (isAlive) tick() }, 0, config.millisecondsPerTick, TimeUnit.MILLISECONDS)
        }
        fun getMemoryUsage(): Float =
            (MemoryInfo.getUsedPhysicalMemory().toFloat() + MemoryInfo.getUsedSwap().toFloat()) / (MemoryInfo.getTotalPhysicalMemory().toFloat() + MemoryInfo.getTotalSwap().toFloat())
    }

    override fun onInitialize() {
        scheduleTick()
    }
}
