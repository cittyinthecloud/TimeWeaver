package io.github.famous1622.tickweaver.mixin;

import io.github.famous1622.tickweaver.duck.SoundSystemHolder;
import io.github.famous1622.tickweaver.duck.TickrateConfigurable;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.client.sound.SoundManager;
import net.minecraft.client.sound.SoundSystem;
import net.minecraft.util.NonBlockingThreadExecutor;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(MinecraftClient.class)
public abstract class MixinMinecraftClient extends NonBlockingThreadExecutor<Runnable> implements TickrateConfigurable, SoundSystemHolder {

    @Shadow @Final
    private RenderTickCounter renderTickCounter;

    @Shadow
    private SoundManager soundManager;

    @SuppressWarnings("unused")
    public MixinMinecraftClient(String string_1) {
        super(string_1);
    }

    public float getTickrate() {
        return ((TickrateConfigurable) renderTickCounter).getTickrate();
    }

    @Override
    public void setTickrate(float tickrate) {
        ((TickrateConfigurable) renderTickCounter).setTickrate(tickrate);
        if (getSoundSystem() != null) {
            ((TickrateConfigurable) getSoundSystem()).setTickrate(tickrate);
        }
    }

    @Override
    public SoundSystem getSoundSystem() {
        if (this.soundManager == null) {
            return null;
        }
        return ((SoundSystemHolder) this.soundManager).getSoundSystem();
    }
}
