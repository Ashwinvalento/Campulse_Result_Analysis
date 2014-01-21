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

/**
 *
 * @author Jickson
 */
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
        resultextractor.curUsnDownloadLabel.show();
        resultFetch r = new resultFetch();
        try {
            //  BufferedWriter writer = new BufferedWriter(new FileWriter(resultextractor.outFile));
            for (int i = 0; i < extractUSN.usnList.size(); i++) {
                int colCount = 1;
                setProgress(i);
                resultextractor.setCurStatusLabel("USN " + extractUSN.usnList.get(i) + " is parsed");
                System.out.println(extractUSN.usnList.get(i));

                if (r.FetchTheresult(extractUSN.usnList.get(i))) {
                    //display marks

                    pstmt.setString(colCount++, extractUSN.usnList.get(i));
                    pstmt.setString(colCount++, r.name);
                    //writer.write(r.usn + "," + r.name + "," + r.sem + "," + r.totalmarks + "," + r.result);
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
            //JOptionPane.showMessageDialog(null, "All marks finished downloading and saved to file ", "USN EXTRACTOR", JOptionPane.PLAIN_MESSAGE);

        } catch (Exception e) {
            System.out.println("Error:" + e);
            JOptionPane.showMessageDialog(null, e);
        }
        return 1;
    }

    @Override
    protected void process(List<Integer> chunks) {

    }

    @Override
    protected void done() {
        //progFrame.setVisible(false); // hide my progress bar JFrame
        resultextractor.objCopy.setButtons();
        resultextractor.objCopy.usnProgressBar.hide();
        System.out.println("DONE!!!");
    }
}
