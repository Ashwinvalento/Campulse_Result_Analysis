package Main;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import Forms.EnterUsnForm;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;

public class extractUSN {

    boolean allExtractSuccess = true;

    /**
     * @param args the command line arguments
     */
    //public static Vector<String> usnList = new Vector<String>();
    public extractUSN(String inFile) {

        BufferedReader br;

        Pattern p = Pattern.compile("4[pP][aA][0-9]{2}[a-zA-Z]{2}[0-9]{3}");
        try {
            br = new BufferedReader(new FileReader(inFile));
            String line;

            while ((line = br.readLine()) != null) {
                Matcher m = p.matcher(line);
                while (m.find()) {
                    /*if (EnterUsnForm.usnMatcher.equals("")) {
                     EnterUsnForm.usnMatcher = m.group(0).substring(5, 7);
                     System.out.println("Matcher set in extractUsn" + EnterUsnForm.usnMatcher);
                     }*/
                    // if (!EnterUsnForm.usnMatcher.equals("") && m.group(0).substring(0, 7).equals(EnterUsnForm.usnMatcher)) {
                    EnterUsnForm.localUsnList.add(m.group(0));
                    /* } else {
                     allExtractSuccess = false;
                     }*/
                }
            }
            br.close();
        } catch (Exception e) {
            System.out.println("Error: " + e);
            JOptionPane.showMessageDialog(null, e);
            Logger.getLogger(EnterUsnForm.class.getName()).log(Level.SEVERE, null, e);
        }

        if (!allExtractSuccess) {
            JOptionPane.showMessageDialog(null, "Some USN have not been added \n Please check the input file if the USN is correct");
        }
    }
}
