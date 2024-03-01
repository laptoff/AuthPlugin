package fr.laptoff.authplugin;

import fr.laptoff.authplugin.commands.CreateAccount;
import fr.laptoff.authplugin.listeners.*;
import fr.laptoff.authplugin.managers.data.Database;
import fr.laptoff.authplugin.managers.data.FileManager;
import fr.laptoff.authplugin.managers.data.Messages;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;


public final class AuthPlugin extends JavaPlugin {

    private static AuthPlugin instance;
    private static final ConsoleCommandSender console = Bukkit.getConsoleSender();
    private static final Database database = new Database();
    private static Component onStartMessage;
    private static Component onDisableMessage;
    private static Component databaseConnectionMessage;
    private static Component databaseDisconnectionMessage;

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();
        FileManager.createResourceFile(new File("config/help.yml"));

        onStartMessage = Messages.ON_START_MESSAGE.getComponent();
        onDisableMessage = Messages.ON_DISABLE_MESSAGE.getComponent();
        databaseConnectionMessage = Messages.DATABASE_CONNECTION_MESSAGE.getComponent();
        databaseDisconnectionMessage = Messages.DATABASE_DISCONNECTION_MESSAGE.getComponent();

        console.sendMessage(onStartMessage);

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

        if (getConfig().getBoolean("database.enable")){

            database.connection();

            database.setup();

            console.sendMessage(databaseConnectionMessage);
        }

        Bukkit.getServer().getPluginManager().registerEvents(new OnPlayerJoin(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new OnPlayerMove(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new OnPlayerDamage(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new OnPlayerCommand(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new OnPlayerBreak(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new OnPlayerPlacingBlocks(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new OnPlayerDrop(), this);

        getCommand("account").setExecutor(new CreateAccount());


    }

    @Override
    public void onDisable(){

        console.sendMessage(onDisableMessage);

        if (Database.isOnline()){
            database.disconnection();

            console.sendMessage(databaseDisconnectionMessage);
        }

    }

    public static AuthPlugin getInstance(){
        return instance;
    }

    public Database getDatabase(){
        return database;
    }
}