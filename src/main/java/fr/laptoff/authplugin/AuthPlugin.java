package fr.laptoff.authplugin;

import fr.laptoff.authplugin.commands.CreateAccount;
import fr.laptoff.authplugin.listeners.OnPlayerDamage;
import fr.laptoff.authplugin.listeners.OnPlayerJoin;
import fr.laptoff.authplugin.listeners.OnPlayerMove;
import fr.laptoff.authplugin.managers.data.Database;
import fr.laptoff.authplugin.managers.data.FileManager;
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
    private static String onStartMessage;
    private static String onDisableMessage;
    private static String databaseConnectionMessage;
    private static String databaseDisconnectionMessage;

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();
        FileManager.createResourceFile(new File("config/help.yml"));

        onStartMessage = getConfig().getString("messages.onStart");
        onDisableMessage = getConfig().getString("messages.onDisable");
        databaseConnectionMessage = getConfig().getString("messages.database.success_connection");
        databaseDisconnectionMessage = getConfig().getString("messages.database.success_disconnection");

        if (onStartMessage == null)
            onStartMessage = "The authentication system started !";

        if (onDisableMessage == null)
            onDisableMessage = "The authentication system is disabled !";

        if (databaseConnectionMessage == null)
            databaseConnectionMessage = "Authenticator connected to database !";

        if (databaseDisconnectionMessage == null)
            databaseDisconnectionMessage = "Authenticator disconnected to database !";

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

        if (getConfig().getBoolean("database.enable")){

            database.connection();

            database.setup();

            console.sendMessage(MiniMessage.miniMessage().deserialize(databaseConnectionMessage));
        }

        Bukkit.getServer().getPluginManager().registerEvents(new OnPlayerJoin(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new OnPlayerMove(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new OnPlayerDamage(), this);

        getCommand("account").setExecutor(new CreateAccount());


    }

    @Override
    public void onDisable(){

        console.sendMessage(MiniMessage.miniMessage().deserialize(onDisableMessage));

        if (Database.isOnline()){
            database.disconnection();

            console.sendMessage(MiniMessage.miniMessage().deserialize(databaseDisconnectionMessage));
        }

    }

    public static AuthPlugin getInstance(){
        return instance;
    }

    public Database getDatabase(){
        return database;
    }
}