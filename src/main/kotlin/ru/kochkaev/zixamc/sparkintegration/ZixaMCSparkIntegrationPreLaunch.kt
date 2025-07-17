package ru.kochkaev.zixamc.sparkintegration

import net.fabricmc.loader.api.entrypoint.PreLaunchEntrypoint
import ru.kochkaev.zixamc.api.config.ConfigManager
import ru.kochkaev.zixamc.api.sql.feature.FeatureTypes

class ZixaMCSparkIntegrationPreLaunch : PreLaunchEntrypoint {

    override fun onPreLaunch() {
        ConfigManager.registerConfig(Config)
        FeatureTypes.registerType(SparkFeatureType)
        TelegramBotLogic.init()
    }
}
