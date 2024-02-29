package fr.laptoff.authplugin.managers.member;

import fr.laptoff.authplugin.AuthPlugin;
import fr.laptoff.authplugin.managers.data.Database;
import fr.laptoff.authplugin.managers.data.FileManager;
import fr.laptoff.authplugin.managers.data.JsonManager;

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
    private String Identifier;
    private String Password;
    private static final AuthPlugin plugin = AuthPlugin.getInstance();
    private JsonManager gson = new JsonManager();
    private Database database = plugin.getDatabase();
    private boolean isAuthenticate;

    public Member(String pseudo, UUID uuid, String identifier, String password){

        this.Pseudo = pseudo;
        this.Uuid = uuid;
        this.Identifier = identifier;
        this.Password = password;
        isAuthenticate = false;

    }

    public String getPseudo(){
        return this.Pseudo;
    }

    public UUID getUuid(){
        return this.Uuid;
    }

    public String getMail(){
        return this.Identifier;
    }

    public String getPassword(){
        return this.Password;
    }

    public boolean isAuthenticate() {
        return isAuthenticate;
    }

    public void setPseudo(String pseudo){
        this.Pseudo = pseudo;
    }

    public void setUuid(UUID uuid){
        this.Uuid = uuid;
    }

    public void setMail(String identifier){
        this.Identifier = identifier;
    }

    public void setPassword(String password){
        this.Password = password;
    }

    public String getJson(){
        return gson.serialize(this);
    }

    public void setAuthenticate(boolean bool){
        isAuthenticate = bool;
    }

    public boolean isRegistered(){
        if (getJsonFromDatabase() != null)
            return true;

        return new File(plugin.getDataFolder() + "/Data/Members/" + this.Uuid + ".json").exists();
    }

    public static boolean isExists(UUID uuid){
        if (getJsonFromDatabase(uuid) != null)
            return true;

        return new File(plugin.getDataFolder() + "/Data/Members/" + uuid + ".json").exists();
    }

    public static String getJsonFromLocal(UUID uuid) throws IOException {
        return Files.readString(Path.of(plugin.getDataFolder() + "/Data/Members/" + uuid + ".json"));
    }

    public String getJsonFromDatabase(){

        if (!database.isConnected())
            return null;

        try {
            PreparedStatement pstmt = database.getConnection().prepareStatement("SELECT json FROM members WHERE uuid = '" + this.Uuid.toString() + "';");
            ResultSet result = pstmt.executeQuery();

            while(result.next()){
                return result.getString("json");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getJsonFromDatabase(UUID uuid){
        if (!plugin.getDatabase().isConnected())
            return null;

        try {
            PreparedStatement pstmt = plugin.getDatabase().getConnection().prepareStatement("SELECT json FROM members WHERE uuid = '" + uuid.toString() + "';");
            ResultSet result = pstmt.executeQuery();

            while(result.next()){
                return result.getString("json");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Member getMember(UUID uuid){

        JsonManager gson = new JsonManager();

        Member member1 = gson.deserialize(getJsonFromDatabase(uuid));
        Member member2;
        try {
             member2 = gson.deserialize(getJsonFromLocal(uuid));
        } catch (IOException e) {
            e.printStackTrace();
            return  null;
        }

        if (member1 != member2 && member1 != null){
            member1.delete();
            member2.delete();
            return null;
        }

        return member2;
    }

    public void saveToLocal(){
        FileManager.createFile(new File(plugin.getDataFolder() + "/Data/Members/" + this.Uuid + ".json"));
        FileManager.rewrite(new File(plugin.getDataFolder() + "/Data/Members/" + this.Uuid + ".json"), getJson());
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
        new File(plugin.getDataFolder() + "/Data/Members/" + this.Uuid + ".json").delete();
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
