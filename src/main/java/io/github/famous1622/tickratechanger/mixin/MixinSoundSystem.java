package io.github.famous1622.tickratechanger.mixin;

import io.github.famous1622.tickratechanger.duck.TickrateConfigurable;
import net.minecraft.client.sound.SoundInstance;
import net.minecraft.client.sound.SoundSystem;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SoundSystem.class)
public class MixinSoundSystem implements TickrateConfigurable {

    private float game_speed = 1F;

    @Inject(method = "getAdjustedPitch", at = @At("RETURN"), cancellable = true)
    private void onGetAdjustedPitch(SoundInstance soundInstance_1, CallbackInfoReturnable<Float> cir) {
        cir.setReturnValue(game_speed * MathHelper.clamp(soundInstance_1.getPitch(), 0.5F, 2.0F));
        cir.cancel();
    }

    @Override
    public float getTickrate() {
        return 20 * game_speed;
    }

    @Override
    public void setTickrate(float tickrate) {
        game_speed = tickrate / 20;
    }
}
