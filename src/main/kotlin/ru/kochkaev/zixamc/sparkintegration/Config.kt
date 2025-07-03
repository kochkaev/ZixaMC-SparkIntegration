package ru.kochkaev.zixamc.sparkintegration

import net.fabricmc.loader.api.FabricLoader
import org.apache.commons.io.IOUtils
import org.apache.commons.lang3.exception.ExceptionUtils
import ru.kochkaev.zixamc.tgbridge.config.ConfigManager
import ru.kochkaev.zixamc.tgbridge.config.GsonManager.gson
import ru.kochkaev.zixamc.tgbridge.config.TextData
import java.io.File
import java.io.FileOutputStream
import java.nio.charset.StandardCharsets

data class Config (
    val featureName: String = "Spark Integration",
    val featureDescription: String = "Бот будет отправлять в выбранный вами топик отчёт при переполнении оперативной памяти.",
    val featureOnDone: String = "<b>Готово!</b> 🎉\nТеперь, при переполнении оперативной памяти, вы будете информированны в этот чат.",
    val featureOptions: String = "» ID топика -> <code>{topicId}</code>",
    val outOfMemoryReport: String = "<b>Out of memory report!</b>\nServer will be automatically saved and stopped.\n\nHeap dump was saved to <code>./spark_heapdumps/{filename}</code> on server.",
    val outOfMemorySay: TextData = TextData("[<red>Объявление</red>] Server » Сервер перегружен! Происходит автоматический перезапуск..."),
    val outOfMemoryKickReason: TextData = TextData("Сервер перегружен! Происходит автоматический перезапуск..."),
    val outOfMemorySayTelegram: String = "Сервер перегружен! Происходит автоматический перезапуск...",
    val memoryThreshold: Float = 0.9F,
    val millisecondsPerTick: Long = 60000,
    val delayBeforeStop: Long = 5000,
) {
    companion object {
        lateinit var config: Config
            private set
        private val file = File(FabricLoader.getInstance().configDir.toFile(), "ZixaMCSparkIntegration.json")
        fun init() {
            ConfigManager.init(file, Config::class.java, ::Config, Config::config) { new -> new?.let { config = it } }
        }
        fun load() {
            ConfigManager.load(file, Config::class.java)
        }
        fun update() {
            ConfigManager.update(file, config)
        }
    }
}
