/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package run;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Administrator
 */
public final class DBConnect {
    // JDBC driver name and database URL
    //Mysql Drivers

    public static String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    public static String DB_URL = "jdbc:derby://localhost:1527/FetchResults";
    static final String USER = "root";
    static final String PASS = "root";
    public static Connection connection = null;

    public static void getConnection() {

        try {
            connection = DriverManager.getConnection(DB_URL, USER, PASS);
            System.out.println("Connected to database...");
        } catch (SQLException ex) {
            Logger.getLogger(DBConnect.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
