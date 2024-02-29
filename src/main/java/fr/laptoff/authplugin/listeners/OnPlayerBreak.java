package fr.laptoff.authplugin.listeners;

import fr.laptoff.authplugin.managers.member.Member;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class OnPlayerBreak implements Listener {

    @EventHandler
    public void OnPlayerBreak(BlockBreakEvent e){

        Player player = e.getPlayer();

        if (!Member.isExists(player.getUniqueId()) || !Member.getMember(player.getUniqueId()).isAuthenticate())
            e.setCancelled(true);

    }

}
