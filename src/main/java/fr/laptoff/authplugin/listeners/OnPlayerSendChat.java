package fr.laptoff.authplugin.listeners;

import fr.laptoff.authplugin.AuthPlugin;
import fr.laptoff.authplugin.BotVerifGenerator;
import fr.laptoff.authplugin.managers.data.Messages;
import fr.laptoff.authplugin.managers.member.Member;
import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.text.Component;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.Random;

public class OnPlayerSendChat implements Listener {

    private static final AuthPlugin plugin = AuthPlugin.getInstance();
    private static final ConsoleCommandSender console = plugin.getConsole();

    @EventHandler
    public void OnPlayerSendChat(AsyncChatEvent e){

        Player player = e.getPlayer();

        if (!Member.getMember(player.getUniqueId()).isBotVerified()){
            if (e.message().equals(Component.text(BotVerifGenerator.getGeneratedString(player.getUniqueId())))){
                Member.getMember(player.getUniqueId()).setBotVerified(true);
                Member.getMember(player.getUniqueId()).save();
                player.sendMessage(Messages.CREATE_ACCOUNT.getComponent());
                e.setCancelled(true);
            }

            if (!e.message().equals(Component.text(BotVerifGenerator.getGeneratedString(player.getUniqueId())))) {
                String s = BotVerifGenerator.generateRandomString(player.getUniqueId(), new Random().nextInt(6, 10));
                player.sendMessage(Component.text("This is not the good captcha... Please retry with " + s));
                e.setCancelled(true);
            }
        }

    }



}
