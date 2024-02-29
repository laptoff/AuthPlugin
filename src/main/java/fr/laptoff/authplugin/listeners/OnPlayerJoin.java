package fr.laptoff.authplugin.listeners;

import fr.laptoff.authplugin.AuthPlugin;
import fr.laptoff.authplugin.managers.member.Member;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;


public class OnPlayerJoin implements Listener {

    AuthPlugin plugin = AuthPlugin.getInstance();
    String accountCreationMessage = plugin.getConfig().getString("messages.authenticator.create_account");
    String accountConnectionMessage = plugin.getConfig().getString("messages.authenticator.connect_to_account");

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e){

        Player player = e.getPlayer();
        Member member;

        if (!Member.isExists(player.getUniqueId())){
            player.sendMessage(MiniMessage.miniMessage().deserialize(accountCreationMessage));

        } else if (Member.isExists(player.getUniqueId())) {
            member = Member.getMember(player.getUniqueId());
            member.setAuthenticate(false);
            member.save();

            player.sendMessage(MiniMessage.miniMessage().deserialize(accountConnectionMessage));
        }

    }
}
