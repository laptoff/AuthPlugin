package fr.laptoff.authplugin.listeners;

import fr.laptoff.authplugin.BotVerifGenerator;
import fr.laptoff.authplugin.managers.data.Messages;
import fr.laptoff.authplugin.managers.member.Member;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.Random;


public class OnPlayerJoin implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e){

        Player player = e.getPlayer();
        Member member;

        String generatedString = BotVerifGenerator.generateRandomString(player.getUniqueId(), new Random().nextInt(6, 10));

        if (!Member.isExists(player.getUniqueId()))
            player.sendMessage(MiniMessage.miniMessage().deserialize(String.format(Messages.BOT_VERIFICATION.getMessage(), generatedString)));

        if (Member.isExists(player.getUniqueId())){

            if (!Member.getMember(player.getUniqueId()).isBotVerified())
                player.sendMessage(MiniMessage.miniMessage().deserialize(String.format(Messages.BOT_VERIFICATION.getMessage(), generatedString)));
        }

        if (Member.isExists(player.getUniqueId()) && Member.getMember(player.getUniqueId()).isBotVerified()) {
            member = Member.getMember(player.getUniqueId());
            member.setAuthenticate(false);
            member.save();

            player.sendMessage(Messages.CONNECTION_ACCOUNT.getComponent());
        }

    }
}
