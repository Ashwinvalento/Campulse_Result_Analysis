/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import Forms.MainForm;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;
import run.DBConnect;
import run.DBInterface;


public class GenReport {

    int slNo;
    int failArray[];        //Retriveing per subject
    int passArray[];
    double passPercentArray[];
    int fcdArray[];
    int firstClassArray[];
    int secondClassArray[];
    int registeredArray[];
    String failQuery;
    String passQuery;
    String fcdQuery;
    String firstClassQuery;
    String secondClassQuery;
    private String subName;
    private int subjectSize;

    public DefaultTableModel fillReportTable() {
        retrieveSubjectNames();
        subjectSize=MainForm.subNamesV.size();
        DefaultTableModel model = new DefaultTableModel() {
            Class[] types = new Class[]{
                java.lang.String.class, java.lang.Integer.class, java.lang.Double.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class
            };
            @Override
            public Class getColumnClass(int columnIndex) {
                return types[columnIndex];
            }
        };
        failArray=new int[subjectSize];
        passArray=new int[subjectSize];
        passPercentArray=new double[subjectSize];
        fcdArray=new int[subjectSize];
        firstClassArray=new int[subjectSize];
        secondClassArray=new int[subjectSize];
        registeredArray=new int[subjectSize];
        //model.addColumn("Sl. no");
        model.addColumn("Subject Names ");
        model.addColumn("Registered");
        model.addColumn("Pass %");
        model.addColumn("Fail");
        model.addColumn("FCD");
        model.addColumn("FC");
        model.addColumn("SC");
        slNo = 0;
        
        for (int i = 0; i < subjectSize; i++) {
            subName=MainForm.subNamesV.get(i);
            System.out.println("sub name : "+ subName);
            failArray[i] = getFailCount(subName);
            passArray[i] = getPassCount(subName);
            fcdArray[i] = getFCDCount(subName);
            firstClassArray[i] = getFCCount(subName);
            secondClassArray[i] = getSCCount(subName);
            registeredArray[i] = failArray[i] + passArray[i];
            passPercentArray[i] = ((double) passArray[i] /(double) registeredArray[i]) * 100;
            System.out.println(subName+" "+failArray[i]);
            model.insertRow(slNo++, new Object[]{subName,registeredArray[i],passPercentArray[i],failArray[i],fcdArray[i],firstClassArray[i],secondClassArray[i] });
        }
        return model;
    }

    private int getFailCount(String subName) {
        int FailCount = 0;
        Connection con = DBConnect.connection;
        ResultSet rs = null;
        failQuery = "SELECT COUNT(*) FROM " + DBInterface.SUBJECT_DETAILS + " WHERE " + DBInterface.SUB_SUBNAME + "='" + subName + "' AND " + DBInterface.SUB_RESULT + "='F'";
        System.out.println("fail Query :  "+ failQuery);
        Statement stmt;
        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery(failQuery);
            if(rs.next()){
                FailCount=rs.getInt(1);
                System.out.println("failCount "+FailCount);
            }
        } catch (SQLException ex) {
            
            System.out.println("error : "+ ex.getMessage());
           // Logger.getLogger(GenReport.class.getName()).log(Level.SEVERE, null, ex);
        }

        return FailCount;
    }

    private int getPassCount(String subName) {
        int PassCount = 0;
        Connection con = DBConnect.connection;
        ResultSet rs = null;
        passQuery = "SELECT COUNT(*) FROM " + DBInterface.SUBJECT_DETAILS + " WHERE " + DBInterface.SUB_SUBNAME + "='" + subName + "' AND " + DBInterface.SUB_RESULT + "='P'";
        Statement stmt;
        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery(passQuery);
            if(rs.next()){
                PassCount=rs.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(GenReport.class.getName()).log(Level.SEVERE, null, ex);
        }

        return PassCount;
    
    }

    private int getFCDCount(String subName) {
        int fcdCount = 0;
        Connection con = DBConnect.connection;
        ResultSet rs = null;
        fcdQuery = "SELECT COUNT(*) FROM " + DBInterface.SUBJECT_DETAILS + " WHERE " + DBInterface.SUB_SUBNAME + "='" + subName + "' AND " + DBInterface.SUB_TOTAL+" BETWEEN 87.5 AND 126";
        Statement stmt;
        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery(fcdQuery);
            if(rs.next()){
                fcdCount=rs.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(GenReport.class.getName()).log(Level.SEVERE, null, ex);
        }

        return fcdCount;
    
    }

    private int getFCCount(String subName) {
        int fcdCount = 0;
        Connection con = DBConnect.connection;
        ResultSet rs = null;
        firstClassQuery = "SELECT COUNT(*) FROM " + DBInterface.SUBJECT_DETAILS + " WHERE " + DBInterface.SUB_SUBNAME + "='" + subName + "' AND " + DBInterface.SUB_TOTAL+" BETWEEN 75 AND 87";
        Statement stmt;
        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery(firstClassQuery);
            if(rs.next()){
                fcdCount=rs.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(GenReport.class.getName()).log(Level.SEVERE, null, ex);
        }

        return fcdCount;
    
    }

    private int getSCCount(String subName) {
 int scCount = 0;
        Connection con = DBConnect.connection;
        ResultSet rs = null;
        secondClassQuery = "SELECT COUNT(*) FROM " + DBInterface.SUBJECT_DETAILS + " WHERE " + DBInterface.SUB_SUBNAME + "='" + subName + "' AND " + DBInterface.SUB_TOTAL+" BETWEEN 62.5 AND 74.5";
        Statement stmt;
        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery(secondClassQuery);
            if(rs.next()){
                scCount=rs.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(GenReport.class.getName()).log(Level.SEVERE, null, ex);
        }

        return scCount;
    
    }
    public void retrieveSubjectNames() {
        MainForm.subNamesV.clear();
        Connection con = DBConnect.connection;
        ResultSet rs = null;
        String sql = "Select DISTINCT " + DBInterface.SUB_SUBNAME + " From " + DBInterface.SUBJECT_DETAILS;
        try {
            Statement stmt = con.createStatement();
            rs = stmt.executeQuery(sql);

            while (rs.next()) {
                MainForm.subNamesV.add(rs.getString(DBInterface.SUB_SUBNAME));
            }
        } catch (SQLException ex) {
            Logger.getLogger(resultFetch.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
