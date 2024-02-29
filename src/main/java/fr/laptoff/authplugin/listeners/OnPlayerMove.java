package fr.laptoff.authplugin.listeners;

import fr.laptoff.authplugin.managers.member.Member;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class OnPlayerMove implements Listener {

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e){

        Player player = e.getPlayer();

        if (!Member.isExists(player.getUniqueId()) || !Member.getMember(player.getUniqueId()).isAuthenticate()){
            e.setCancelled(true);
        }

    }

}
