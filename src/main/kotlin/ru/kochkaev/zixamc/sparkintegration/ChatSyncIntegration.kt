package ru.kochkaev.zixamc.sparkintegration

import net.fabricmc.loader.api.FabricLoader
import net.kyori.adventure.text.Component
import ru.kochkaev.zixamc.chatsync.ChatSyncBotLogic
import ru.kochkaev.zixamc.chatsync.TBPlayerEventData
import ru.kochkaev.zixamc.sparkintegration.mixin.ChatSyncBotLogicMixin

object ChatSyncIntegration {

    val enabled: Boolean
        get() = FabricLoader.getInstance().isModLoaded("zixamc-chatsync")

    @Suppress("CAST_NEVER_SUCCEEDS")
    fun sendAsSay(sender: String = "Server", text: String) {
        if (!enabled) return
        (ChatSyncBotLogic as ChatSyncBotLogicMixin).invokeOnSayMessage(TBPlayerEventData(
            username = sender,
            text = Component.text(text),
        ), ChatSyncBotLogic.DEFAULT_GROUP)
    }
}