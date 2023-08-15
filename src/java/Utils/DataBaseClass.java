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
 * @author luaan
 */
public class DataBaseClass {

    private Connection conn;
    private PreparedStatement ps;
    private ResultSet rs;

    public DataBaseClass() {

        try {
            conn = DBManager.getConnection();
        } catch (SQLException ex) {
            Logger.getLogger(DataBaseClass.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public Connection getConn() {
        try {
            conn = DBManager.getConnection();
        } catch (SQLException ex) {
            Logger.getLogger(DataBaseClass.class.getName()).log(Level.SEVERE, null, ex);
        }
        return conn;
    }

    public class DBManager {

        private static BasicDataSource dataSource1;

        public DBManager() {
        }

        static {
            dataSource1 = new BasicDataSource();
            dataSource1.setDriverClassName("com.mysql.cj.jdbc.Driver");
            dataSource1.setUrl("jdbc:mysql://localhost:3306/JokeServer?autoReconnect=true&useSSL=false");
            dataSource1.setUsername("root");
            dataSource1.setPassword("root");
            dataSource1.setMinIdle(10);
            dataSource1.setMaxIdle(10);
            dataSource1.setMaxOpenPreparedStatements(100);

        }

        public static Connection getConnection() throws SQLException {
            return dataSource1.getConnection();
        }
    }

}
