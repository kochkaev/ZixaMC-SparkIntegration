package ru.kochkaev.zixamc.sparkintegration

import net.fabricmc.loader.api.FabricLoader
import org.apache.commons.io.IOUtils
import org.apache.commons.lang3.exception.ExceptionUtils
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
    val outOfMemorySay: TextData = TextData("<red><bold>Сервер перегружен!</bold> Происходит автоматический перезапуск..."),
    val memoryThreshold: Float = 0.9F,
    val millisecondsPerTick: Long = 60000,
    val delayBeforeStop: Long = 5000,
) {
    companion object {
        lateinit var config: Config
            private set
        private val file = File(FabricLoader.getInstance().configDir.toFile(), "ZixaMCSparkIntegration.json")
        fun init() {
            if (file.length() != 0L) {
                try {
                    load()
                    update()
                } catch (e: Exception) {
                    ZixaMCSparkIntegration.logger.error(ExceptionUtils.getStackTrace(e))
                }
            } else {
                create()
            }
        }
        fun create() {
            val content = Config()
            try {
                FileOutputStream(file).use { outputStream ->
                    val jsonString = gson.toJson(content)
                    IOUtils.write(jsonString, outputStream, StandardCharsets.UTF_8)
                    config = content
                }
            } catch (e: Exception) {
                ZixaMCSparkIntegration.logger.error(ExceptionUtils.getStackTrace(e))
            }
        }
        fun load() {
            var content: Config? = null
            try {
                content = gson.fromJson(
                    IOUtils.toString(file.toURI(), StandardCharsets.UTF_8),
                    Config::class.java
                )
            } catch (e: Exception) {
                ZixaMCSparkIntegration.logger.error(ExceptionUtils.getStackTrace(e))
            }
            content?.let { config = it }
        }
        fun update() {
            try {
                FileOutputStream(file).use { outputStream ->
                    val jsonString = gson.toJson(config)
                    IOUtils.write(jsonString, outputStream, StandardCharsets.UTF_8)
                }
            } catch (e: Exception) {
                ZixaMCSparkIntegration.logger.error(ExceptionUtils.getStackTrace(e))
            }
        }
    }
}
