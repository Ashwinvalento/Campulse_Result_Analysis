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
            System.out.println("Cant connect");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DBConnect.class.getName()).log(Level.SEVERE, null, ex);
        }

        Statement stmt, stmt1;
        String query1 = "CREATE TABLE RESULTTABLE\n"
                + "(\n"
                + "	USN VARCHAR(12),\n"
                + "	NAME VARCHAR(50),\n"
                + "	SUB1_INT INTEGER,\n"
                + "	SUB1_EXT INTEGER,\n"
                + "	SUB1_TOT INTEGER,\n"
                + "	SUB1_RES CHAR(1),\n"
                + "	SUB2_INT INTEGER,\n"
                + "	SUB2_EXT INTEGER,\n"
                + "	SUB2_TOT INTEGER,\n"
                + "	SUB2_RES CHAR(1),\n"
                + "	SUB3_INT INTEGER,\n"
                + "	SUB3_EXT INTEGER,\n"
                + "	SUB3_TOT INTEGER,\n"
                + "	SUB3_RES CHAR(1),\n"
                + "	SUB4_INT INTEGER,\n"
                + "	SUB4_EXT INTEGER,\n"
                + "	SUB4_TOT INTEGER,\n"
                + "	SUB4_RES CHAR(1),\n"
                + "	SUB5_INT INTEGER,\n"
                + "	SUB5_EXT INTEGER,\n"
                + "	SUB5_TOT INTEGER,\n"
                + "	SUB5_RES CHAR(1),\n"
                + "	SUB6_INT INTEGER,\n"
                + "	SUB6_EXT INTEGER,\n"
                + "	SUB6_TOT INTEGER,\n"
                + "	SUB6_RES CHAR(1),\n"
                + "	SUB7_INT INTEGER,\n"
                + "	SUB7_EXT INTEGER,\n"
                + "	SUB7_TOT INTEGER,\n"
                + "	SUB7_RES CHAR(1),\n"
                + "	SUB8_INT INTEGER,\n"
                + "	SUB8_EXT INTEGER,\n"
                + "	SUB8_TOT INTEGER,\n"
                + "	SUB8_RES CHAR(1),\n"
                + "	TOTAL INTEGER,\n"
                + "	MARKCLASS VARCHAR(50)\n"
                + ")";

        String query2 = "CREATE TABLE \"SUBJECTTABLE\" (\"subjectname\" VARCHAR DEFAULT \"SUBJECT\" )";
        try {
            stmt = connection.createStatement();
            stmt1 = connection.createStatement();

            stmt.executeUpdate(query1);
            System.out.println("Result Table created");
            stmt1.executeUpdate(query2);
            System.out.println("Subject table created");
            
        } catch (SQLException ex) {
            //Logger.getLogger(DBConnect.class.getName()).log(Level.SEVERE, null, ex);
            
            System.out.println("Table already exists .");
        }

    }
}
