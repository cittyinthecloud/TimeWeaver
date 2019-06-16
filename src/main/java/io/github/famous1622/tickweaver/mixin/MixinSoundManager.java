package io.github.famous1622.tickweaver.mixin;

import io.github.famous1622.tickweaver.duck.SoundSystemHolder;
import net.minecraft.client.sound.SoundManager;
import net.minecraft.client.sound.SoundSystem;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(SoundManager.class)
public class MixinSoundManager implements SoundSystemHolder {
    @Shadow @Final
    private SoundSystem soundSystem;

    @Override
    public SoundSystem getSoundSystem() {
        return this.soundSystem;
    }
}
