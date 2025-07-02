package ru.kochkaev.zixamc.sparkintegration

import kotlinx.coroutines.launch
import me.lucko.spark.api.SparkProvider
import me.lucko.spark.common.heapdump.HeapDump
import me.lucko.spark.common.heapdump.HeapDumpSummary
import me.lucko.spark.common.monitor.memory.MemoryInfo
import net.fabricmc.api.ModInitializer
import net.fabricmc.loader.api.FabricLoader
import net.fabricmc.loader.api.entrypoint.PreLaunchEntrypoint
import net.minecraft.server.MinecraftServer
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import ru.kochkaev.zixamc.tgbridge.telegram.feature.FeatureTypes
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import ru.kochkaev.zixamc.sparkintegration.Config.Companion.config
import ru.kochkaev.zixamc.tgbridge.Initializer
import ru.kochkaev.zixamc.tgbridge.telegram.ServerBot.bot
import ru.kochkaev.zixamc.tgbridge.sql.SQLGroup
import ru.kochkaev.zixamc.tgbridge.telegram.feature.chatSync.parser.TextParser
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class ZixaMCSparkIntegrationPreLaunch : PreLaunchEntrypoint {

    override fun onPreLaunch() {
        Config.init()
        FeatureTypes.registerType(SparkFeatureType)
        TelegramBotLogic.init()
    }
}
