package ru.kochkaev.zixamc.sparkintegration.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;
import ru.kochkaev.zixamc.api.sql.SQLGroup;
import ru.kochkaev.zixamc.chatsync.ChatSyncBotLogic;
import ru.kochkaev.zixamc.chatsync.TBPlayerEventData;

@Mixin(ChatSyncBotLogic.class)
public interface ChatSyncBotLogicMixin {

    @Invoker(remap = false)
    void invokeOnSayMessage(TBPlayerEventData e, SQLGroup group);

}
