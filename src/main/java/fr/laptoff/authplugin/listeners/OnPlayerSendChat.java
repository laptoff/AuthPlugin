package fr.laptoff.authplugin.listeners;

import fr.laptoff.authplugin.AuthPlugin;
import fr.laptoff.authplugin.BotVerifGenerator;
import fr.laptoff.authplugin.managers.data.Messages;
import fr.laptoff.authplugin.managers.member.Member;
import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
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

        if (!Member.isExists(player.getUniqueId())){
            if (PlainTextComponentSerializer.plainText().serialize(e.message()).equals(BotVerifGenerator.getGeneratedString(player.getUniqueId()))){
                player.sendMessage(Messages.CREATE_ACCOUNT.getComponent());
                e.setCancelled(true);
            }

            if (!PlainTextComponentSerializer.plainText().serialize(e.message()).equals(BotVerifGenerator.getGeneratedString(player.getUniqueId()))) {
                String s = BotVerifGenerator.generateRandomString(player.getUniqueId(), new Random().nextInt(6, 10));
                player.sendMessage(Component.text(String.format(Messages.BOT_VERIFICATION_FAILED.getMessage(), s)));
                e.setCancelled(true);
            }
        }

    }
}
