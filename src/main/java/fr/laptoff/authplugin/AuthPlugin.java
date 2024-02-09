package fr.laptoff.authplugin;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.java.JavaPlugin;


public final class AuthPlugin extends JavaPlugin {

    private static AuthPlugin instance;
    private static final ConsoleCommandSender console = Bukkit.getConsoleSender();
    private static String onStartMessage;
    private static String onDisableMessage;

    @Override
    public void onEnable() {
        instance = this;

        onStartMessage = getConfig().getString("messages.onStart");
        onDisableMessage = getConfig().getString("messages.onDisable");

        if (onStartMessage == null)
            onStartMessage = "The authentication system started !";

        if (onDisableMessage == null)
            onDisableMessage = "The authentication system is disabled !";

        console.sendMessage(MiniMessage.miniMessage().deserialize(onStartMessage));

        //Start introduction.
        console.sendMessage(Component
                .text("by ")
                .color(NamedTextColor.YELLOW)
                .append(Component
                        .text("laptoff,")
                        .color(NamedTextColor.GOLD)
                        .decorate(TextDecoration.ITALIC)));

        console.sendMessage(Component.text(""));

        console.sendMessage(Component
                .text(" /|  ")
                .color(NamedTextColor.GREEN));

        console.sendMessage(Component
                .text("/-|")
                .color(NamedTextColor.GREEN)
                .append(Component
                        .text("|)")
                        .color(NamedTextColor.BLUE)));

        console.sendMessage(Component
                .text("   | ")
                .color(NamedTextColor.BLUE)
                .append(Component
                        .text("     Auth")
                        .color(NamedTextColor.GREEN))
                .append(Component
                        .text("Plugin")
                        .color(NamedTextColor.BLUE)));


    }

    @Override
    public void onDisable(){

        console.sendMessage(MiniMessage.miniMessage().deserialize(onDisableMessage));

    }

    public static AuthPlugin getInstance(){
        return instance;
    }
}