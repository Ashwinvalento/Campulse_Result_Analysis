package Main;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;
import run.DBConnect;

public class DownloadMarksTask extends SwingWorker<Integer, Integer> {

    PreparedStatement pstmt;
    Statement stmt;

    DownloadMarksTask() {
        //initialize 
        Connection con = DBConnect.connection;
        String sql = " DELETE from RESULTTABLE ";
        String query = "INSERT INTO RESULTTABLE values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        try {
            stmt = con.createStatement();
            stmt.executeUpdate(sql);
            pstmt = con.prepareStatement(query);
        } catch (SQLException ex) {
            Logger.getLogger(DownloadMarksTask.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public Integer doInBackground() throws InterruptedException {
        MainForm.curUsnDownloadLabel.show();
        resultFetch r = new resultFetch();
        if (MainForm.stopFlag == true) {
            stopFetching();
        }
        try {
            for (int i = 0; i < MainForm.usnList.size() && MainForm.stopFlag == false; i++) {
                int colCount = 1;
                setProgress((i + 1) * 100 / MainForm.usnList.size());
                MainForm.setCurStatusLabel("USN " + MainForm.usnList.get(i) + " is parsed");
                System.out.println(MainForm.usnList.get(i));

                if (r.FetchTheresult(MainForm.usnList.get(i))) {
                    //display marks

                    pstmt.setString(colCount++, MainForm.usnList.get(i));
                    pstmt.setString(colCount++, r.name);
                    for (int x = 0; x < 8; x++) {
                        pstmt.setInt(colCount++, r.marks[x][1]);
                        pstmt.setInt(colCount++, r.marks[x][0]);
                        pstmt.setInt(colCount++, r.marks[x][2]);
                        pstmt.setString(colCount++, r.res[x]);
                    }
                    pstmt.setInt(colCount++, Integer.parseInt(r.totalmarks));
                    pstmt.setString(colCount++, r.mk);
                    pstmt.execute();
                }
            }

        } catch (Exception e) {
            System.out.println("Error:" + e);
            JOptionPane.showMessageDialog(null, e + " Download marks task");
            System.out.println("Exception is here");
        }
        return 1;
    }

    @Override
    protected void process(List<Integer> chunks) {

    }

    @Override
    protected void done() {
        MainForm.objCopy.usnProgressBar.hide();
        System.out.println("DONE!!!");
    }

    void stopFetching() {
        this.cancel(true);
    }
}
