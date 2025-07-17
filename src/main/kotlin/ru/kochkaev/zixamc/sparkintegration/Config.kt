package ru.kochkaev.zixamc.sparkintegration

import net.fabricmc.loader.api.FabricLoader
import ru.kochkaev.zixamc.api.config.ConfigFile
import ru.kochkaev.zixamc.api.config.TextData
import java.io.File

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
    companion object: ConfigFile<Config>(
        file = File(FabricLoader.getInstance().configDir.toFile(), "ZixaMC-SparkIntegration.json"),
        model = Config::class.java,
        supplier = ::Config,
    )
}
