package ru.kochkaev.zixamc.sparkintegration

import ru.kochkaev.zixamc.api.sql.data.AccountType
import ru.kochkaev.zixamc.api.telegram.ServerBot
import ru.kochkaev.zixamc.api.sql.feature.TopicFeatureType
import ru.kochkaev.zixamc.api.formatLang
import ru.kochkaev.zixamc.api.sql.feature.data.TopicFeatureData

object SparkFeatureType: TopicFeatureType<TopicFeatureData>(
    model = TopicFeatureData::class.java,
    serializedName = "SPARK_INTEGRATION_FEATURE",
    tgDisplayName = { Config.config.featureName },
    tgDescription = { Config.config.featureDescription },
    tgOnDone = { Config.config.featureOnDone },
    checkAvailable = { it.hasProtectedLevel(AccountType.ADMIN) },
    getDefault = { TopicFeatureData(null, it) },
    optionsResolver = {
        Config.config.featureOptions.formatLang(
            "topicId" to (it.topicId?.toString() ?: ServerBot.config.group.settings.nullTopicPlaceholder),
        )
    },
)