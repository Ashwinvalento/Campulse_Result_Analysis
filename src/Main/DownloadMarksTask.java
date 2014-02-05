package Main;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import Forms.EnterUsnForm;
import Forms.MainForm;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;
import run.DBConnect;
import run.DBInterface;

public class DownloadMarksTask extends SwingWorker<Integer, Integer> implements DBInterface {

    PreparedStatement pstmtSub, pstmtStud;

    public DownloadMarksTask() {
        //initialize 
        Connection con = DBConnect.connection;

        String subQuery = "Insert into " + SUBJECT_DETAILS + " values (?,?,?,?,?,?)";
        String stdQuery = "Insert into " + STUDENT_DETAILS + " values (?,?,?,?)";
        try {
            pstmtSub = con.prepareStatement(subQuery);
        } catch (SQLException ex) {
            Logger.getLogger(DownloadMarksTask.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            pstmtStud = con.prepareStatement(stdQuery);
        } catch (SQLException ex) {
            Logger.getLogger(DownloadMarksTask.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public Integer doInBackground() throws InterruptedException {
        resultFetch r = new resultFetch();
        if (MainForm.stopFlag == true) {
            stopFetching();
        }
        try {
            for (int i = 0; i < MainForm.usnList.size() && MainForm.stopFlag == false; i++) {
                int colCount = 1;
                setProgress((i + 1) * 100 / MainForm.usnList.size());
                System.out.println(MainForm.usnList.get(i));

                if (r.FetchTheresult(MainForm.usnList.get(i), EnterUsnForm.sem)) {
                    pstmtStud.setString(1, MainForm.usnList.get(i));
                    pstmtStud.setString(2, r.name);
                    pstmtStud.setInt(3, Integer.parseInt(r.totalmarks));
                    pstmtStud.setString(4, r.mk);
                    pstmtStud.execute();
                    pstmtSub.setString(1, MainForm.usnList.get(i));
                    for (int x = 0; x < 8; x++) {
                        pstmtSub.setString(2, r.subjects[x]);
                        pstmtSub.setInt(3, r.marks[x][1]);
                        pstmtSub.setInt(4, r.marks[x][0]);
                        pstmtSub.setInt(5, r.marks[x][2]);
                        pstmtSub.setString(6, r.res[x]);
                        pstmtSub.execute();
                    }
                }
            }

        } catch (Exception e) {
            System.out.println("Error:" + e);
            JOptionPane.showMessageDialog(null, e + " Download marks task");
        }
        return 1;
    }

    @Override
    protected void process(List<Integer> chunks) {

    }

    @Override
    protected void done() {
        if (MainForm.autoRetry && MainForm.retrylimit>0 && !MainForm.RetryList.isEmpty() && MainForm.stopFlag == false ) {
            MainForm.objCopy.clickRestart();
            MainForm.retrylimit--;
        } else {
            System.out.println("DONE!!!");
            MainForm.log("Download Completed.");
            MainForm.log("Please check download details to see list of USN failed to fetch.");
            MainForm.objCopy.clickStop();
        }

    }

    public void stopFetching() {
        this.cancel(true);
    }
}
