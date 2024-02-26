package fr.laptoff.authplugin.Managers.member;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import fr.laptoff.authplugin.AuthPlugin;
import fr.laptoff.authplugin.Managers.Data.Database;
import fr.laptoff.authplugin.Managers.Data.FileManager;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class Member {

    private String Pseudo;
    private UUID Uuid;
    private String Mail;
    private String Password;
    private File file;
    private static final AuthPlugin plugin = AuthPlugin.getInstance();
    private Gson gson = new GsonBuilder().create();
    private Database database = plugin.getDatabase();

    public Member(String pseudo, UUID uuid, String mail, String password){

        this.Pseudo = pseudo;
        this.Uuid = uuid;
        this.Mail = mail;
        this.Password = password;
        file = new File(plugin.getDataFolder() + "/Data/Members/" + this.Uuid + ".json");

    }

    public String getPseudo(){
        return this.Pseudo;
    }

    public UUID getUuid(){
        return this.Uuid;
    }

    public String getMail(){
        return this.Mail;
    }

    public String getPassword(){
        return this.Password;
    }

    public void setPseudo(String pseudo){
        this.Pseudo = pseudo;
    }

    public void setUuid(UUID uuid){
        this.Uuid = uuid;
    }

    public void setMail(String mail){
        this.Mail = mail;
    }

    public void setPassword(String password){
        this.Password = password;
    }

    public String getJson(){
        return gson.toJson(this);
    }

    public boolean isExist(){
        if (getJsonFromDatabase() != null)
            return true;

        return file.exists();
    }

    public static String getJsonFromLocal(UUID uuid) throws IOException {
        return new GsonBuilder().create().toJson(Files.readString(Path.of(plugin.getDataFolder() + "/Data/Members/" + uuid + ".json")), Member.class);
    }

    public String getJsonFromDatabase(){

        if (!database.isConnected())
            return null;

        try {
            PreparedStatement pstmt = database.getConnection().prepareStatement("SELECT json FROM members WHERE uuid = '" + this.Uuid + "';");
            ResultSet result = pstmt.executeQuery();

            while(result.next()){
                return result.getString("json");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void saveToLocal(){
        FileManager.createFile(file);
        FileManager.rewrite(file, getJson());
    }

    public void saveToDatabase(){

        if (!database.isConnected())
            return;

        //if it doesn't exist into the database.
        if (getJsonFromDatabase() == null){
            try{
                PreparedStatement pstmt = database.getConnection().prepareStatement("INSERT INTO members (uuid, json) VALUES ('" + this.Uuid + "', '" + this.getJson() + "');");
                pstmt.execute();
            } catch (SQLException e){
                e.printStackTrace();
            }
        }

        //if it already exists into the database
        if (getJsonFromDatabase() != null){
            try{
                PreparedStatement pstmt = database.getConnection().prepareStatement("UPDATE members SET json = '" + this.getJson() + "' WHERE uuid = '" + this.Uuid + "';");
                pstmt.execute();
            } catch(SQLException e){
                e.printStackTrace();
            }
        }
    }

    public void save(){
        saveToLocal();
        saveToDatabase();
    }

    public void deleteFromLocal(){
        file.delete();
    }

    public void deleteFromdatabase(){

        if (!database.isConnected())
            return;

        try{
            PreparedStatement pstmt = database.getConnection().prepareStatement("DELETE FROM members WHERE uuid= '" + this.Uuid + "';");
            pstmt.execute();
        } catch(SQLException e){
            e.printStackTrace();
        }
    }

    public void delete(){
        deleteFromdatabase();
        deleteFromLocal();
    }

}
