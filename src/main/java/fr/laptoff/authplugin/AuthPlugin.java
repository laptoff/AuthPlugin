package fr.laptoff;

import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public final class AuthPlugin extends JavaPlugin {

    private static AuthPlugin instance;
    public static final Logger LOGGER = Logger.getLogger("CivilisationPlot");

    @Override
    public void onEnable(){
        instance = this;

        LOGGER.info("The authentication system started !");
    }

    @Override
    public void onDisable(){

        LOGGER.info("The authentication system is disabled !");

    }

    public static AuthPlugin getInstance(){
        return instance;
    }
}