package com.mythup.minecraftcentralcommands.brigadier;

import com.mythup.commandkit.CommandArgument;
import com.mythup.commandkit.CommandCompletion;
import com.mythup.commandkit.CommandNode;
import com.mythup.minecraftcentralcommands.MinecraftCentralCommandsMod;

public final class CommandDispatcher {
    private CommandDispatcher() {
    }
    public static void buildTree() {
        CommandNode claim = command("claim")
                .then(command("abandon"))
                .then(command("ban", player("player")))
                .then(command("banlist", string("page")))
                .then(command("demote", player("player")))
                .then(command("desc", string("desc")))
                .then(command("expand", string("amount")))
                .then(command("expel", player("player")))
                .then(command("help", choices("page", "1", "2", "3", "4")))
                .then(command("info"))
                .then(command("inspect"))
                .then(command("kit"))
                .then(command("list", string("page")))
                .then(command("manager", player("player")))
                .then(command("managerlist", string("page")))
                .then(command("map"))
                .then(command("name", string("name")))
                .then(command("pay", player("player"), string("amount")))
                .then(command("perms"))
                .then(command("settings"))
                .then(command("trust", player("player")))
                .then(command("trustlist", string("page")))
                .then(command("tutorial"))
                .then(command("unban", player("player")))
                .then(command("untrust", player("player")))
                .then(command("withdraw", string("amount")));
        register(claim);

        CommandNode friend = command("friend", "friends")
                .then(command("accept", player("player")))
                .then(command("add", player("player")))
                .then(command("deny", player("player")))
                .then(command("help", choices("page", "1", "2")))
                .then(command("list"))
                .then(command("remove", player("player")))
                .then(command("requests"))
                .then(command("settings"));
        register(friend);

        CommandNode gang = command("gang")
                .then(command("ally", string("gang")))
                .then(command("bank"))
                .then(command("chat"))
                .then(command("chest"))
                .then(command("create", string("name")))
                .then(command("demote", player("player")))
                .then(command("deposit", string("amount")))
                .then(command("disband"))
                .then(command("focus", playerOrLiteral("player", "none")))
                .then(command("help", choices("page", "1", "2", "3")))
                .then(command("info", string("gang")))
                .then(command("invite", player("player")))
                .then(command("kick", player("player")))
                .then(command("leader", player("player")))
                .then(command("leave"))
                .then(command("list", string("gang"), string("page")))
                .then(command("neutral", string("gang")))
                .then(command("perms"))
                .then(command("promote", player("player")))
                .then(command("tag", string("tag")))
                .then(command("unfocus"))
                .then(command("upgrade"))
                .then(command("withdraw", string("amount")));
        register(gang);

        CommandNode island = command("island")
                .then(command("ban", player("player")))
                .then(command("banlist", string("page")))
                .then(command("bank"))
                .then(command("banner"))
                .then(command("bedrock"))
                .then(command("blockvalues"))
                .then(command("border"))
                .then(command("chat"))
                .then(command("chest"))
                .then(command("conquest"))
                .then(command("create"))
                .then(command("croptop"))
                .then(command("delete"))
                .then(command("delwarp", string("name")))
                .then(command("demote", player("player")))
                .then(command("deposit", string("amount")))
                .then(command("desc", string("desc")))
                .then(command("digsite", player("player")))
                .then(command("end", player("player")))
                .then(command("expel", player("player")))
                .then(command("help", choices("page", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10")))
                .then(command("home", player("player")))
                .then(command("info", player("player")))
                .then(command("inspect"))
                .then(command("invite", player("player")))
                .then(command("join", player("player")))
                .then(command("kick", player("player")))
                .then(command("leader", player("player")))
                .then(command("leave"))
                .then(command("level", player("player")))
                .then(command("lock"))
                .then(command("logs"))
                .then(command("mobtop"))
                .then(command("music"))
                .then(command("name", string("name")))
                .then(command("nether", player("player")))
                .then(command("perms", player("player")))
                .then(command("playtime"))
                .then(command("promote", player("player")))
                .then(command("seechunk"))
                .then(command("sethome"))
                .then(command("settings"))
                .then(command("setwarp", string("name")))
                .then(command("tags"))
                .then(command("top"))
                .then(command("trust", player("player")))
                .then(command("trustlist", string("page")))
                .then(command("unban", player("player")))
                .then(command("unlock"))
                .then(command("unsethome"))
                .then(command("untrust", player("player")))
                .then(command("upgrade"))
                .then(command("warp", string("warp")))
                .then(command("warps"))
                .then(command("withdraw", string("amount")));
        register(island);

        register(command("msg", "msgf")
                .argument(player("player"))
                .argument(restMessage("message")));

        CommandNode party = command("party")
                .then(command("accept", player("player")))
                .then(command("disband"))
                .then(command("help"))
                .then(command("invite", player("player")))
                .then(command("leave"))
                .then(command("list"))
                .then(command("promote", player("player")))
                .then(command("remove", player("player")));
        register(party);
        
        // From the wiki. Unfindable in-game. Possibly removed or renamed.
        /* CommandNode plot = command("plot")
                .then(command("add", player("player")))
                .then(command("auto"))
                .then(command("biome", string("biome")))
                .then(command("chat"))
                .then(command("claim"))
                .then(command("clear"))
                .then(command("deny", player("player")))
                .then(command("home", player("player")))
                .then(command("info"))
                .then(command("kick", player("player")))
                .then(command("middle"))
                .then(command("remove", player("player")))
                .then(command("trust", player("player")));
        register(plot); */

        register(command("pc", "rf")
                .argument(string("message")));
    }

    /**
     * Register a command node.
     *
     * The when() condition keeps suggestions active only on the configured
     * target server (see MinecraftCentralCommandsMod.isTargetServer()).
     *
     * @param command the command node to register
     */
    private static void register(CommandNode command) {
            command.when(context -> MinecraftCentralCommandsMod.isTargetServer()).serverIp("mccentral.org");
        CommandCompletion.register(command);
    }

    private static CommandNode command(String name, CommandArgument first, CommandArgument... rest) {
        CommandNode command = CommandNode.literal(name).argument(first);
        for (CommandArgument argument : rest) {
            command.argument(argument);
        }
        return command;
    }

    private static CommandNode command(String... names) {
        if (names.length == 0) {
            throw new IllegalArgumentException("A command needs a name");
        }
        return CommandNode.literal(names[0], java.util.Arrays.copyOfRange(names, 1, names.length));
    }

    private static CommandArgument player(String name) {
        return CommandArgument.player(name);
    }

    private static CommandArgument repeatablePlayer(String name) {
        return CommandArgument.repeatablePlayer(name);
    }

    private static CommandArgument repeatable(CommandArgument argument) {
        return new CommandArgument(argument.name(), argument.type(), argument.literals(), true);
    }

    private static CommandArgument playerOrLiteral(String name, String... values) {
        return CommandArgument.playerOrLiteral(name, values);
    }

    private static CommandArgument string(String name) {
        return CommandArgument.string(name);
    }

    private static CommandArgument choices(String name, String... values) {
        return CommandArgument.choices(name, values);
    }

    private static CommandArgument restMessage(String name) {
        return CommandArgument.restMessage(name);
    }
}