package ru.kochkaev.zixamc.sparkintegration

import ru.kochkaev.zixamc.tgbridge.sql.data.AccountType
import ru.kochkaev.zixamc.tgbridge.telegram.ServerBot
import ru.kochkaev.zixamc.tgbridge.telegram.feature.TopicFeatureType
import ru.kochkaev.zixamc.tgbridge.telegram.feature.chatSync.parser.TextParser
import ru.kochkaev.zixamc.tgbridge.telegram.feature.data.TopicFeatureData

object SparkFeatureType: TopicFeatureType<TopicFeatureData>(
    model = TopicFeatureData::class.java,
    serializedName = "SPARK_INTEGRATION_FEATURE",
    tgDisplayName = { Config.config.featureName },
    tgDescription = { Config.config.featureDescription },
    tgOnDone = { Config.config.featureOnDone },
    checkAvailable = { it.hasProtectedLevel(AccountType.ADMIN) },
    getDefault = { TopicFeatureData(null, it) },
    optionsResolver = {
        TextParser.formatLang(
            text = Config.config.featureOptions,
            "topicId" to (it.topicId?.toString() ?: ServerBot.config.integration.group.settings.nullTopicPlaceholder),
        )
    },
)