package Main;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;



public class extractUSN {

    /**
     * @param args the command line arguments
     */
    public static Vector<String> usnList = new Vector<String>();
    public extractUSN(String inFile) {
       
        BufferedReader br;

        Pattern p = Pattern.compile("4[pP][aA][0-9]{2}[a-zA-Z]{2}[0-9]{3}");
        try {
            br = new BufferedReader(new FileReader(inFile));
            String line;

            while ((line = br.readLine()) != null) {
                Matcher m = p.matcher(line);
                while (m.find()) {
                    usnList.add(m.group(0));
                }
            }
            br.close();
        } catch (Exception e) {
            System.out.println("Error: " + e);
            JOptionPane.showMessageDialog(null, e);
        }
    } 
}
