package fr.laptoff.authplugin.commands;

import fr.laptoff.authplugin.AuthPlugin;
import fr.laptoff.authplugin.managers.member.Member;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;

public class CreateAccount implements CommandExecutor {

    AuthPlugin plugin = AuthPlugin.getInstance();
    String argumentsError = plugin.getConfig().getString("messages.errors.wrong_number_of_arguments");
    String alreadyAnAccount = plugin.getConfig().getString("messages.errors.already_an_account");
    String alreadyConnected = plugin.getConfig().getString("messages.errors.already_connected");
    String hasNotAccount = plugin.getConfig().getString("messages.errors.has_not_account");
    String successConnection = plugin.getConfig().getString("messages.authenticator.success_connection");
    String successAccountCreation = plugin.getConfig().getString("messages.authenticator.success_creation");

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {

        if (!(sender instanceof Player player))
            return false;

        Member member = null;

        if (Member.isExists(player.getUniqueId()))
            member = Member.getMember(player.getUniqueId());


        if (member != null){
            if (member.isAuthenticate()){
                player.sendMessage(MiniMessage.miniMessage().deserialize(alreadyConnected));
                return false;
            }
        }



        //for account creation
        if (args[0].equalsIgnoreCase("new")){

            if (member != null){
                player.sendMessage(MiniMessage.miniMessage().deserialize(alreadyAnAccount));
                return false;
            }


            if (args.length != 3){
                player.sendMessage(MiniMessage.miniMessage().deserialize(argumentsError));
                return false;
            }

            Member mem = new Member(player.getName(), player.getUniqueId(), args[1], args[2]);
            mem.setAuthenticate(false);
            mem.save();
            player.sendMessage(MiniMessage.miniMessage().deserialize(successAccountCreation));
            return true;

        }

        //for account connection
        if (args[0].equalsIgnoreCase("connect")){

            if (member == null){
                player.sendMessage(MiniMessage.miniMessage().deserialize(hasNotAccount));
                return false;
            }

            if (args.length != 2){
                player.sendMessage(MiniMessage.miniMessage().deserialize(argumentsError));
                return false;
            }

            if (args[1].equalsIgnoreCase(member.getPassword())){
                member.setAuthenticate(true);
                member.save();
                player.sendMessage(MiniMessage.miniMessage().deserialize(successConnection));
                return true;
            } else {

            }

        }

        if (args.length == 0 || args[1].equalsIgnoreCase("help")){

            File file = new File(plugin.getDataFolder() + "/config/help.yml");
            YamlConfiguration config = YamlConfiguration.loadConfiguration(file);


            player.sendMessage(MiniMessage.miniMessage().deserialize(config.getString("help_message")));
        }

        return false;
    }
}
