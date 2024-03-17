package fr.laptoff.authplugin.listeners;

import fr.laptoff.authplugin.managers.member.Member;
import io.papermc.paper.event.player.AsyncChatEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class OnPlayerSendChat implements Listener {

    @EventHandler
    public void OnPlayerSendChat(AsyncChatEvent e){

        Player player = e.getPlayer();

        if (!Member.getMember(player.getUniqueId()).isAuthenticate())
            e.setCancelled(true);

    }

}
