/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package run;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class DBConnect implements DBInterface {

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
            System.out.println("Cant connect");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DBConnect.class.getName()).log(Level.SEVERE, null, ex);
        }

        Statement stmt, stmt1;

        String query1 = "CREATE TABLE " + SUBJECT_DETAILS + " ('" + SUB_USN + "' VARCHAR NOT NULL , '" + SUB_SUBNAME + "' VARCHAR NOT NULL , '" + SUB_INTERNAL + "' INTEGER, '" + SUB_EXTERNAL + "' INTEGER, '" + SUB_TOTAL + "' INTEGER, '" + SUB_RESULT + "' CHAR, PRIMARY KEY (" + SUB_USN + ", " + SUB_SUBNAME + "))";
        String query2 = "CREATE TABLE " + STUDENT_DETAILS + " ('" + ST_USN + "' VARCHAR, '" + ST_NAME + "' VARCHAR, '" + ST_TOTAL + "' INTEGER, '" + ST_RESULT + "' VARCHAR)";
        try {
            stmt = connection.createStatement();
            stmt1 = connection.createStatement();

            stmt.executeUpdate(query1);
            System.out.println("Result Table created");
            stmt1.executeUpdate(query2);
            System.out.println("Subject table created");
        } catch (SQLException ex) {
            // Logger.getLogger(DBConnect.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Table already exists .");
        }

    }
}
