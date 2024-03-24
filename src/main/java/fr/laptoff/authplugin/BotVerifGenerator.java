package fr.laptoff.authplugin;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

public class BotVerifGenerator {

    private static final Map<UUID, String> generatedStrings = new HashMap<UUID, String>();

    public static String generateRandomString(UUID player, int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder sb = new StringBuilder();

        Random random = new Random();

        for (int i = 0; i < length; i++) {
            int index = random.nextInt(characters.length());
            char randomChar = characters.charAt(index);
            sb.append(randomChar);
        }

        if (generatedStrings.containsKey(player)){
            generatedStrings.replace(player, sb.toString());
            return sb.toString();
        }

        generatedStrings.put(player, sb.toString());

        return sb.toString();
    }

    public static String getGeneratedString(UUID uuid){
        return generatedStrings.get(uuid);
    }


}
