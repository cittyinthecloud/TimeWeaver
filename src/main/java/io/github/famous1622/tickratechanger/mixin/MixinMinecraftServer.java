package io.github.famous1622.tickratechanger.mixin;

import io.github.famous1622.tickratechanger.duck.TickrateConfigurable;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.ServerTask;
import net.minecraft.util.NonBlockingThreadExecutor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(MinecraftServer.class)
public abstract class MixinMinecraftServer extends NonBlockingThreadExecutor<ServerTask> implements TickrateConfigurable {

    private long mspt = 50;

    //Make the compiler happy or smthng
    public MixinMinecraftServer(String string_1) {
        super(string_1);
    }

    //Patch that MSPT real good
    @ModifyConstant(method = "run", constant = @Constant(longValue = 50L))
    private long serverTickWaitTime(long ignored) {
        return this.mspt;
    }

    @Override
    public float getTickrate() {
        return 1000f / this.mspt;
    }

    @Override
    public void setTickrate(float tickrate) {
        this.mspt = (long) Math.floor(1000f / tickrate);
    }
}
