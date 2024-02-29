package fr.laptoff.authplugin.listeners;

import fr.laptoff.authplugin.managers.member.Member;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class OnPlayerDamage implements Listener {

    @EventHandler
    public void OnPlayerDamage(EntityDamageEvent e){

        if (!(e.getEntity() instanceof Player player))
            return;

        if (!Member.isExists(player.getUniqueId()) || Member.getMember(player.getUniqueId()).isAuthenticate()){
            e.setCancelled(true);
        }

    }

}
