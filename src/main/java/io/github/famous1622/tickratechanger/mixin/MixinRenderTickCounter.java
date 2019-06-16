package io.github.famous1622.tickratechanger.mixin;

import io.github.famous1622.tickratechanger.duck.TickrateConfigurable;
import net.minecraft.client.render.RenderTickCounter;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(RenderTickCounter.class)
public class MixinRenderTickCounter implements TickrateConfigurable {


    @Mutable @Shadow @Final private float timeScale;


    @Override
    public float getTickrate() {
        return 1000 / timeScale;
    }

    @Override
    public void setTickrate(float tickrate) {
        this.timeScale = 1000f / tickrate;
    }
}
