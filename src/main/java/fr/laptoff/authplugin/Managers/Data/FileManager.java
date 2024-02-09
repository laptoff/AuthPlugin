package fr.laptoff.authplugin.Managers.Data;

import fr.laptoff.authplugin.AuthPlugin;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileManager {
    public static void createResourceFile(File file){
        File file_ = new File(AuthPlugin.getInstance().getDataFolder() + "/" + file.getPath());
        if (file_.exists())
            return;

        file.getParentFile().mkdirs();

        AuthPlugin.getInstance().saveResource(file.getPath(), false);
    }

    public static void createFile(File file){
        if (file.exists())
            return;

        file.getParentFile().mkdirs();
        try {
            file.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void rewrite(File file, String text){
        //This method erase the text into the file and write the new text
        if (!file.exists())
            return;

        try {
            FileWriter writer = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(writer);
            bw.write(text);
            bw.flush();
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
