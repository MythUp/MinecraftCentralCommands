# MyServer Commands (Template)

A Fabric client-only mod template for Minecraft that improves in-chat command completion by adding local client-side suggestions for server-specific commands and subcommands. The completion engine is published separately as the `CommandKit` Fabric library mod, so other client mods can register their own definitions.

---

## 📋 Template Guide

This section provides a step-by-step guide on how to configure and extend this mod for your own server.

### Server URL Configuration

The mod needs to know which server to apply command completion to. By default, it checks if the connected server's IP contains `your-server.net` (a placeholder to replace with your domain).

**Where to configure:** `src/main/java/com/mythup/myservercommands/MyServerCommandsMod.java`

**How to modify:** Open `MyServerCommandsMod.java` and find the `isTargetServer()` method:

```java
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
```

**To configure for your server:**
1. Replace `"your-server.net"` with your server's domain (e.g., `"hypixel.net"`)
2. For multiple servers, add additional `||` conditions:
   ```java
   return server.ip.toLowerCase().contains("your-server.net") || 
          server.ip.toLowerCase().contains("another-server.net");
   ```
3. Rebuild the mod

### Multi-World / Network Support

If your mod needs to work across multiple server networks, you can configure checks for several domains:

```java
var ip = server.ip.toLowerCase();
return ip.contains("your-server.net") || 
       ip.contains("another-network.net") || 
       ip.contains("third-network.org");
```

You can also make the check case-insensitive by ensuring `.toLowerCase()` is used on both sides.

---

### Defining Commands

Commands are defined in `src/main/java/com/mythup/myservercommands/brigadier/CommandDispatcher.java` using the CommandKit library's fluent API.

**Command structure pattern:**

```java
// Basic command with subcommands
command("friend")
    .then(command("add", player("player")))
    .then(command("list", choices("page", "best")))
    .then(command("remove", player("player")));
```

**Where to define:** `CommandDispatcher.java` in the `buildTree()` method

**Registration:** All commands are automatically registered via the `register()` method which also applies the target-server check (`MyServerCommandsMod.isTargetServer()`).

---

### Available Parameter Types

The CommandKit library provides these argument types for command definitions:

| Type | Method | Example |
|------|--------|---------|
| **Player** | `player("name")` | `command("msg", player("player"))` |
| **String** | `string("name")` | `command("set", string("value"))` |
| **Choices** | `choices("name", "val1", "val2")` | `command("page", choices("page", "best", "recent"))` |
| **Repeatable Players** | `repeatablePlayer("name")` | `command("party", invite(repeatablePlayer("player")))` |
| **Player or Literal** | `playerOrLiteral("name", "val1", "val2")` | `command("mute", playerOrLiteral("target", "everyone"))` |
| **Rest Message** | `restMessage("name")` | `command("msg", player("player"), restMessage("message"))` |

**Usage examples:**

#### Player argument
```java
command("tpa", player("player"))
```

#### Choices argument
```java
command("quality", choices("quality", "low", "medium", "high"))
```

#### Repeatable players
```java
command("party", invite(repeatablePlayer("player")))
```

#### Player or literal values
```java
command("guild", mute(playerOrLiteral("target", "everyone")))
```

#### Combined usage
```java
command("settings", set(player("player"), choices("setting", "allinvite", "mute", "private")))
```

---

### Adding New Commands

1. **Open `CommandDispatcher.java`**
2. **Add your command in the `buildTree()` method** using the fluent API

**Example: Adding a `/donate` command**

```java
// Add after existing commands
CommandNode donate = command("donate")
    .then(command("link", string("url")))
    .then(command("status"));
register(donate);
```

**Example: Adding a command with all argument types**

```java
CommandNode example = command("example")
    .then(command("set", player("target"), choices("mode", "public", "private")))
    .then(command("get", restMessage("notes")));
register(example);
```

3. **Rebuild the mod** to apply changes

---

### Development Setup

**Prerequisites:**
- Java 25 (or as specified in `build.gradle`)
- Gradle 8.x
- Fabric Loader 0.16.0+
- Minecraft 26.2+

**Building the mod:**
```bash
./gradlew build
```

**Running the mod:**
```bash
./gradlew runClient
```

**Testing command completion:**
1. Start Minecraft with the mod installed
2. Connect to your target server (or modify `isTargetServer()` in `MyServerCommandsMod.java` for your domain)
3. Type `/` in chat to see available commands
4. Type a command name (e.g., `/friend`) to see subcommands and arguments

---
