package fr.laptoff.authplugin.listeners;

import fr.laptoff.authplugin.AuthPlugin;
import fr.laptoff.authplugin.managers.data.Messages;
import fr.laptoff.authplugin.managers.member.Member;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.Random;


public class OnPlayerJoin implements Listener {

    AuthPlugin plugin = AuthPlugin.getInstance();
    private static String generatedString = generateRandomString(new Random().nextInt(6, 10));

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e){

        Player player = e.getPlayer();
        Member member;

        if (!Member.getMember(player.getUniqueId()).isBotVerified())
            player.sendMessage(MiniMessage.miniMessage().deserialize("<red> You need to pass the bot verification test, please send: <gold>" + generatedString));

        if (Member.isExists(player.getUniqueId()) && Member.getMember(player.getUniqueId()).isBotVerified()) {
            member = Member.getMember(player.getUniqueId());
            member.setAuthenticate(false);
            member.save();

            player.sendMessage(Messages.CONNECTION_ACCOUNT.getComponent());
        }

    }

    public static String generateRandomString(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder sb = new StringBuilder();

        Random random = new Random();

        for (int i = 0; i < length; i++) {
            int index = random.nextInt(characters.length());
            char randomChar = characters.charAt(index);
            sb.append(randomChar);
        }

        return sb.toString();
    }

    public static String getGeneratedString(){
        return generatedString;
    }

    public static void reGenerateString(){
        generatedString = generateRandomString(6);
    }
}
