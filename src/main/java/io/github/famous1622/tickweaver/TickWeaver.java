package io.github.famous1622.tickweaver;

import io.github.famous1622.tickweaver.duck.SoundSystemHolder;
import io.github.famous1622.tickweaver.duck.TickrateConfigurable;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.server.ServerStartCallback;
import net.fabricmc.fabric.api.registry.CommandRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.network.chat.ChatMessageType;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.mojang.brigadier.arguments.FloatArgumentType.floatArg;
import static com.mojang.brigadier.arguments.FloatArgumentType.getFloat;
import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

public class TickWeaver implements ModInitializer, ServerStartCallback, ClientModInitializer {

    public static int TICKRATE = 20;

    private static MinecraftServer server;

    static Logger LOGGER = LogManager.getLogger("TickWeaver");

    @Override
    public void onInitialize() {
        LOGGER.info("TickWeaver loaded, ticks shall fear me uwu");
        ServerStartCallback.EVENT.register(this);
        CommandRegistry.INSTANCE.register(false, (dispatcher) -> dispatcher.register(
                literal("tickrate")
                        .then(
                                argument("tickrate", floatArg(1, 1000))
                                        .executes(command -> {
                                            setClientTickrate(getFloat(command, "tickrate"));
                                            setServerTickrate(getFloat(command, "tickrate"));
                                            return 1;
                                        })
                        )
                        .executes(command -> {
                            command.getSource().sendFeedback(new TextComponent(String.format("Tickrate for server: %f", ((TickrateConfigurable) server).getTickrate())), false);
                            command.getSource().sendFeedback(new TextComponent(String.format("Tickrate for client: %f", ((TickrateConfigurable) MinecraftClient.getInstance()).getTickrate())), false);
                            command.getSource().sendFeedback(new TextComponent(String.format("Tickrate for sound: %f", ((TickrateConfigurable) ((SoundSystemHolder) MinecraftClient.getInstance()).getSoundSystem()).getTickrate())), false);

                            return 1;
                        })
        ));

        CommandRegistry.INSTANCE.register(true, (dispatcher) -> dispatcher.register(
                literal("tickrate")
                        .then(
                                argument("tickrate", floatArg(1, 1000))
                                        .executes(command -> {
                                            setServerTickrate(getFloat(command, "tickrate"));
                                            return 1;
                                        })
                        )
                        .executes(command -> {
                            ServerPlayerEntity player = command.getSource().getPlayer();
                            player.sendChatMessage(new TextComponent(String.format("Tickrate for server: %d", ((TickrateConfigurable) server).getTickrate())), ChatMessageType.SYSTEM);
                            return 1;
                        })
        ));
    }


    @Override
    public void onStartServer(MinecraftServer minecraftServer) {
        LOGGER.info("OwO set server tickrate");
        server = minecraftServer;
        setServerTickrate(TICKRATE);
    }

    public void setClientTickrate(float tickrate) {
        ((TickrateConfigurable) MinecraftClient.getInstance()).setTickrate(tickrate);
    }

    public void setServerTickrate(float tickrate) {
        ((TickrateConfigurable) server).setTickrate(tickrate);
    }

    @Override
    public void onInitializeClient() {
        LOGGER.info("OwO client tickrate time");
        setClientTickrate(TICKRATE);
    }
}
