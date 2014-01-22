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

public final class DBConnect {

    //SQLITE database Drivers
    public static String driver = "org.sqlite.JDBC";
    public static String DB_URL = "jdbc:sqlite:FetchResults.sqlite";
    public static Connection connection = null;

    public static void getConnection() {

        try {
            Class.forName(driver);
            connection = DriverManager.getConnection(DB_URL);
            System.out.println("Connected to database...");
        } catch (SQLException ex) {
            Logger.getLogger(DBConnect.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DBConnect.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
