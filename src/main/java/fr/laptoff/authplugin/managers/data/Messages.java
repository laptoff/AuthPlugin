package fr.laptoff.authplugin.managers.data;

import fr.laptoff.authplugin.AuthPlugin;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public enum Messages {

    ARGUMENTS_ERROR("messages.errors.wrong_number_of_arguments", "<red>Your command has a wrong number of arguments !", new File(AuthPlugin.getInstance().getDataFolder() + "/config.yml")),
    ALREADY_AN_ACCOUNT("messages.errors.already_an_account", "<red> You have already an account !", new File(AuthPlugin.getInstance().getDataFolder() + "/config.yml")),
    ALREADY_CONNECTED("messages.errors.already_connected", "<red> You are already connected !", new File(AuthPlugin.getInstance().getDataFolder() + "/config.yml")),
    HAS_NOT_ACCOUNT("messages.errors.has_not_account", "<red> You must to create an account !", new File(AuthPlugin.getInstance().getDataFolder() + "/config.yml")),
    SUCCESS_CONNECTION("messages.authenticator.success_connection", "<green> Good Game! You are now connected !", new File(AuthPlugin.getInstance().getDataFolder() + "/config.yml")),
    SUCCESS_ACCOUNT_CREATION("messages.authenticator.success_creation", "<green>Good Game! You created your account! <yellow> Connect to your account now! (/account connect [password])", new File(AuthPlugin.getInstance().getDataFolder() + "/config.yml")),
    INCORRECT_PASSWORD("messages.errors.incorrect_password", "<red> This is the wrong password :(", new File(AuthPlugin.getInstance().getDataFolder() + "/config.yml")),
    SUCCESS_FORGOT("messages.authenticator.success_forgot", "<green> Your account is now deleted ! Please reconnect yourself !", new File(AuthPlugin.getInstance().getDataFolder() + "/config.yml")),
    ON_START_MESSAGE("messages.onStart", "The authentication system started !", new File(AuthPlugin.getInstance().getDataFolder() + "/config.yml")),
    ON_DISABLE_MESSAGE("messages.onDisable", "The authentication system is disabled !", new File(AuthPlugin.getInstance().getDataFolder() + "/config.yml")),
    DATABASE_CONNECTION_MESSAGE("messages.database.success_connection", "Authenticator connected to database !", new File(AuthPlugin.getInstance().getDataFolder() + "/config.yml")),
    DATABASE_DISCONNECTION_MESSAGE("messages.database.success_disconnection", "Authenticator disconnected to database !", new File(AuthPlugin.getInstance().getDataFolder() + "/config.yml")),
    INCORRECT_IDENTIFIER("incorrect_identifier", "<red> This is the wrong identifier :(", new File(AuthPlugin.getInstance().getDataFolder() + "/config.yml")),
    INCORRECT_ENTITY("incorrect_entity", "<red> You need to be a player to perform this command !", new File(AuthPlugin.getInstance().getDataFolder() + "/config.yml"));

    private final String Path;
    private final String DefaultValue;
    private final File File;

    private Messages(String path, String defaultValue, File file){
        this.Path = path;
        this.DefaultValue = defaultValue;
        this.File = file;
    }

    public String getPath(){
        return this.Path;
    }

    public String getDefaultValue(){
        return this.DefaultValue;
    }

    public File getFile(){
        return this.File;
    }
    public Component getComponent(){
        YamlConfiguration config = YamlConfiguration.loadConfiguration(getFile());

        if (config.getString(getPath()) == null)
            return MiniMessage.miniMessage().deserialize(getDefaultValue());

        return MiniMessage.miniMessage().deserialize(config.getString(getPath()));
    }

    public String getMessage(){
        YamlConfiguration config = YamlConfiguration.loadConfiguration(getFile());

        if (config.getString(getPath()) == null)
            return getDefaultValue();

        return config.getString(getPath());
    }


}
