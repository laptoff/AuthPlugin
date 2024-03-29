package fr.laptoff.authplugin.commands;

import fr.laptoff.authplugin.AuthPlugin;
import fr.laptoff.authplugin.managers.data.Messages;
import fr.laptoff.authplugin.managers.member.Member;
import net.kyori.adventure.text.Component;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class CreateAccount implements CommandExecutor {

    private final AuthPlugin plugin = AuthPlugin.getInstance();
    private final Component argumentsError = Messages.ARGUMENTS_ERROR.getComponent();
    private final Component alreadyAnAccount = Messages.ALREADY_AN_ACCOUNT.getComponent();
    private final Component alreadyConnected = Messages.ALREADY_CONNECTED.getComponent();
    private final Component hasNotAccount = Messages.HAS_NOT_ACCOUNT.getComponent();
    private final Component successConnection = Messages.SUCCESS_CONNECTION.getComponent();
    private final Component successAccountCreation = Messages.SUCCESS_ACCOUNT_CREATION.getComponent();
    private final Component incorrectPassword = Messages.INCORRECT_PASSWORD.getComponent();
    private final Component successForgot = Messages.SUCCESS_FORGOT.getComponent();

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {

        if (!(sender instanceof Player player)){
            sender.sendMessage(Messages.INCORRECT_ENTITY.getComponent());
            return false;
        }


        Member member = null;

        if (Member.isExists(player.getUniqueId()))
            member = Member.getMember(player.getUniqueId());

        if (args.length == 0 || args[0].equalsIgnoreCase("help")){
            player.sendMessage(Messages.HELP_COMMAND.getComponent());
            return true;
        }

        //for account creation
        if (args[0].equalsIgnoreCase("new")){

            if (member != null){
                player.sendMessage(alreadyAnAccount);
                return false;
            }


            if (args.length != 3){
                player.sendMessage(argumentsError);
                return false;
            }

            Member mem = new Member(player.getName(), player.getUniqueId(), args[1], args[2]);
            mem.setAuthenticate(false);
            mem.setBotVerified(true);
            mem.save();
            player.sendMessage(successAccountCreation);
            return true;

        }

        //for account connection
        if (args[0].equalsIgnoreCase("connect")){

            if (member == null){
                player.sendMessage(hasNotAccount);
                return false;
            }

            if (member.isAuthenticate()){
                player.sendMessage(alreadyConnected);
                return false;
            }

            if (args.length != 2){
                player.sendMessage(argumentsError);
                return false;
            }

            if (args[1].equals(member.getPassword())){
                member.setAuthenticate(true);
                member.save();
                player.sendMessage(successConnection);
                return true;
            } else {
                player.sendMessage(incorrectPassword);
                return false;
            }

        }

        if (args[0].equalsIgnoreCase("forgot")){

            if (args.length != 3){
                player.sendMessage(argumentsError);
                return false;
            }

            if (!Member.isExists(player.getUniqueId())){
                player.sendMessage(Messages.HAS_NOT_ACCOUNT.getComponent());
                return false;
            }

            if (!args[1].equals(Member.getMember(player.getUniqueId()).getPassword())){
                player.sendMessage(incorrectPassword);
                return false;
            }

            if (!args[2].equals(Member.getMember(player.getUniqueId()).getIdentifier())){
                player.sendMessage(Messages.INCORRECT_IDENTIFIER.getComponent());
                return false;
            }

            Member.getMember(player.getUniqueId()).delete();
            player.sendMessage(successForgot);
            return true;

        }

        return false;
    }
}
