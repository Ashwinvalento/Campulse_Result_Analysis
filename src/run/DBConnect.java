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
import javax.swing.JOptionPane;

/**
 *
 * @author Administrator
 */
public final class DBConnect {

    // JDBC driver name and database URL
    //SQLITE database Drivers
    public static String driver = "org.sqlite.JDBC";
    public static String DB_URL = "jdbc:sqlite:FetchResults.sqlite";

    //ApaChe derby Embeded drivers
    /*
     public String driver = "org.apache.derby.jdbc.EmbeddedDriver";
     // public static String DB_URL = "jdbc:derby://localhost:1527/FetchResults";
     public static String DB_URL = "jdbc:derby:FetchResults;create=true";
     static final String USER = "root1";
     static final String PASS = "roo1t";
     */
    public static Connection connection = null;

    public static void getConnection() {

        try {
            Class.forName(driver);
            //connection for embeded derby
            // connection = DriverManager.getConnection(DB_URL, USER, PASS);
            //connection for SQLITE
            connection = DriverManager.getConnection(DB_URL);
            System.out.println("Connected to database...");
        } catch (SQLException ex) {
            Logger.getLogger(DBConnect.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DBConnect.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
