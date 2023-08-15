/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utils;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.dbcp2.BasicDataSource;

/**
 *
 * @author jarro
 */
public class DBManager {

    private static BasicDataSource dataSource;
    
    public DBManager() {}

    static {
        GetProperties getProperties = new GetProperties("C:\\Users\\jarro\\OneDrive\\Documents\\NetBeansProjects\\JokeServer\\src\\java\\Properties\\config.properties");
        dataSource = new BasicDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/JokeServer?autoReconnect=true&useSSL=false");
        dataSource.setPassword("root");
        dataSource.setUsername("root");
        dataSource.setMinIdle(10);
        dataSource.setMaxIdle(10);
        dataSource.setMaxOpenPreparedStatements(100);
    }

    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
    
    public Connection getConn() {
        try {
            return dataSource.getConnection();
        } catch (SQLException ex) {
            Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
