package ru.kochkaev.zixamc.sparkintegration.mixin;

import com.google.common.collect.ImmutableMap;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;
import ru.kochkaev.zixamc.sparkintegration.ChatSyncIntegration;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class SparkIntegrationMixinPlugin implements IMixinConfigPlugin {

    private static final Map<String, Boolean> CONDITIONS = ImmutableMap.of(
        "ChatSyncBotLogicMixin", ChatSyncIntegration.INSTANCE.getEnabled()
    );

    @Override
    public void onLoad(String mixinPackage) {

    }

    @Override
    public String getRefMapperConfig() {
        return "";
    }

    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
        var className = mixinClassName.replace("ru.kochkaev.zixamc.sparkintegration.mixin.", "");
        return CONDITIONS.getOrDefault(className, true);
    }

    @Override
    public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) {

    }

    @Override
    public List<String> getMixins() {
        return null;
    }

    @Override
    public void preApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {

    }

    @Override
    public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {

    }
}
