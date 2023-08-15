package Utils;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MySQLConnection {
    private Connection connection;
    private String url, user, password;

    public MySQLConnection() {
        this.connection = null;
    }

    public Connection create() {
        GetProperties getProperties = new GetProperties("C:\\Users\\jarro\\OneDrive\\Documents\\NetBeansProjects\\JokeServer\\src\\java\\Properties\\config.properties");
        do {
            try {
                getProperties.printAll();
                this.url =  getProperties.get("url");
                this.user = getProperties.get("dbuser");
                this.password = getProperties.get("dbpassword");
                connection = DriverManager.getConnection(url, user, password);
            } catch (SQLException ex) {
                Logger.getLogger(MySQLConnection.class.getName()).log(Level.SEVERE, null, ex);
            }
        } while(connection==null);
        //        System.out.println("Made connection...>>>");
        return connection;
    }

    public void close() {
        if (connection!=null) {
            try {
                connection.close();
//                System.out.println("Closed connection...<<<");
            } catch (SQLException ex) {
                Logger.getLogger(MySQLConnection.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
