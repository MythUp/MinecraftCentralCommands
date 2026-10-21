package com.mythup.myservercommands.brigadier;

import com.mythup.commandkit.CommandArgument;
import com.mythup.commandkit.CommandCompletion;
import com.mythup.commandkit.CommandNode;
import com.mythup.myservercommands.MyServerCommandsMod;
import com.mojang.brigadier.suggestion.Suggestions;

/**
 * Generic command dispatcher template using the CommandKit library.
 * 
 * This file defines the command tree structure for the mod.
 * To customize for your own commands:
 * 1. Modify the buildTree() method to add your own commands
 * 2. Add/remove subcommands using .then() 
 * 3. Add arguments using .argument() with available types:
 *    - player("name") - single player
 *    - string("name") - free-form string
 *    - choices("name", "val1", "val2") - restricted values
 *    - repeatablePlayer("name") - multiple players
 *    - playerOrLiteral("name", "val1", "val2") - player or literal
 *    - restMessage("name") - captures remaining input
 * 4. All commands are registered via the register() method
 */
public final class CommandDispatcher {
    private CommandDispatcher() {
    }

    /**
     * Build the command tree for the mod.
     *
     * Add your own commands here using the CommandKit fluent API.
     * Each command can have subcommands (via .then()) and arguments (via .argument()).
     *
     * Example structure:
     *
     *   CommandNode mycmd = command("mycmd")
     *       .then(command("sub1", player("player")))
     *       .then(command("sub2", choices("mode", "a", "b")));
     *   register(mycmd);
     */
    public static void buildTree() {
        // TODO: Add your own command definitions here
        // Example:
        // CommandNode chat = command("ch", "chat")
        //     .then(command("all"))
        //     .then(command("party"))
        //     .then(command("guild"));
        // register(chat);
    }

    /**
     * Entry point used by the mixin: returns local suggestions for the typed input.
     */
    public static Suggestions getSuggestions(String input) {
        return CommandCompletion.getSuggestions(input);
    }

    /**
     * Register a command node.
     *
     * The when() condition keeps suggestions active only on the configured
     * target server (see MyServerCommandsMod.isTargetServer()).
     *
     * @param command the command node to register
     */
    private static void register(CommandNode command) {
        command.when(context -> MyServerCommandsMod.isTargetServer());
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