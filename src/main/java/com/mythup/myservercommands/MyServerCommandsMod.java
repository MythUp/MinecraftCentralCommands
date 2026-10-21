package com.mythup.myservercommands;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.Minecraft;
import com.mythup.myservercommands.brigadier.CommandDispatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyServerCommandsMod implements ClientModInitializer {
    public static final String MOD_ID = "myservercommands";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitializeClient() {
        LOGGER.info("Initializing myservercommands mod...");
        CommandDispatcher.buildTree();
        LOGGER.info("myservercommands mod initialized.");
    }

    /**
     * Check if the connected server matches the target network.
     *
     * To configure for your server:
     * 1. Replace "your-server.net" with your server's domain
     * 2. Add additional || conditions for multiple servers if needed
     */
    public static boolean isTargetServer() {
        Minecraft client = Minecraft.getInstance();
        if (client.level == null || client.player == null) {
            return false;
        }
        var server = client.getCurrentServer();
        if (server == null || server.ip == null) {
            return false;
        }
        return server.ip.toLowerCase().contains("your-server.net");
    }
}
