package fr.laptoff.authplugin.managers.data;

import fr.laptoff.authplugin.AuthPlugin;

import java.sql.*;
import java.util.Objects;

public class Database {

    private static Connection co;
    private static final AuthPlugin plugin = AuthPlugin.getInstance();

    public Connection getConnection()
    {
        return co;
    }

    public void connection()
    {
        if(!isConnected())
        {
            try
            {
                String motorDriver = "org.mariadb";

                if (Objects.requireNonNull(plugin.getConfig().getString("database.motor")).equalsIgnoreCase("mysql"))
                    motorDriver = "com.mysql.cj";

                Class.forName(motorDriver + ".jdbc.Driver"); //loading of the driver.
                co = DriverManager.getConnection("jdbc:" + plugin.getConfig().getString("database.motor") + "://" + plugin.getConfig().getString("database.host") + ":" + plugin.getConfig().getString("Database.port") + "/" + plugin.getConfig().getString("Database.database_name"), plugin.getConfig().getString("Database.user"), plugin.getConfig().getString("Database.password"));
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void disconnection()
    {
        if (isConnected())
        {
            try
            {
                co.close();
            }
            catch(SQLException e)
            {
                e.printStackTrace();
            }
        }
    }

    public boolean isConnected()
    {
        try {
            return co != null && !co.isClosed() && plugin.getConfig().getBoolean("database.enable") && plugin.getDatabase() != null;
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean isOnline() {
        try {
            if (co != null)
                return co.isClosed();

            return false;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean doesTableExist(String tableName) {
        try {
            DatabaseMetaData metaData = co.getMetaData();
            ResultSet resultSet = metaData.getTables(null, null, tableName, null);

            return resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }



    public void setup(){
        try {
            if (!doesTableExist("members")){
                PreparedStatement pstmt = this.getConnection().prepareStatement("CREATE TABLE members (id INT AUTO_INCREMENT PRIMARY KEY, uuid VARCHAR(50), json VARCHAR(50));");
                pstmt.execute();
            }
        } catch(SQLException e){
            e.printStackTrace();
        }



    }

}
