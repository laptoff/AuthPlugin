package fr.laptoff.authplugin.listeners;

import fr.laptoff.authplugin.managers.member.Member;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class OnPlayerCommand implements Listener {

    @EventHandler
    public void OnPlayerCommand(PlayerCommandPreprocessEvent e){

        Player player = e.getPlayer();

        String command = e.getMessage().split(" ")[0].substring(1);

        if (!Member.isExists(player.getUniqueId()) || !Member.getMember(player.getUniqueId()).isAuthenticate()){

            if (command.equalsIgnoreCase("account") || command.equalsIgnoreCase("ac"))
                return;

            e.setCancelled(true);
        }

    }

}
