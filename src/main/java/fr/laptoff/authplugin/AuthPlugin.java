package fr.laptoff.authplugin;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.checkerframework.checker.units.qual.N;

import java.util.logging.Logger;

public final class AuthPlugin extends JavaPlugin {

    private static AuthPlugin instance;
    public static final Logger LOGGER = Logger.getLogger("AuthPlugin");

    @Override
    public void onEnable() {
        instance = this;

        LOGGER.info("The authentication system started !");

        //Start introduction.
        Bukkit.getConsoleSender().sendMessage(Component.text("by ")
                .color(NamedTextColor.YELLOW)
                .append(Component.text("laptoff,")
                        .color(NamedTextColor.GOLD)
                        .decorate(TextDecoration.ITALIC)));

        Bukkit.getConsoleSender().sendMessage(Component.text(""));

        Bukkit.getConsoleSender().sendMessage(Component.text(" /|  ")
                .color(NamedTextColor.GREEN));

        Bukkit.getConsoleSender().sendMessage(Component.text("/-|")
                .color(NamedTextColor.GREEN)
                .append(Component.text("|)")
                        .color(NamedTextColor.BLUE)));

        Bukkit.getConsoleSender().sendMessage(Component.text("   | ")
                .color(NamedTextColor.BLUE)
                .append(Component.text("     Auth")
                        .color(NamedTextColor.GREEN))
                .append(Component.text("Plugin")
                        .color(NamedTextColor.BLUE)));


    }

    @Override
    public void onDisable(){

        LOGGER.info("The authentication system is disabled !");

    }

    public static AuthPlugin getInstance(){
        return instance;
    }
}