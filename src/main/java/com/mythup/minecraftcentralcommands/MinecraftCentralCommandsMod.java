package com.mythup.minecraftcentralcommands;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.Minecraft;
import com.mythup.minecraftcentralcommands.brigadier.CommandDispatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MinecraftCentralCommandsMod implements ClientModInitializer {
    public static final String MOD_ID = "minecraftcentralcommands";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitializeClient() {
        LOGGER.info("Initializing minecraftcentralcommands mod...");
        CommandDispatcher.buildTree();
        LOGGER.info("minecraftcentralcommands mod initialized.");
    }

    public static boolean isTargetServer() {
        Minecraft client = Minecraft.getInstance();
        if (client.level == null || client.player == null) {
            return false;
        }
        var server = client.getCurrentServer();
        if (server == null || server.ip == null) {
            return false;
        }
        String ip = server.ip.toLowerCase();
        if (!ip.contains("mccentral.org")) {
            return false;
        }
        return server.ip.toLowerCase().contains("mccentral.org");
    }
}
