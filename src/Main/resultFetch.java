package Main;

import Forms.MainForm;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import run.DBConnect;

public class resultFetch {

    String result;
    int sem;
    int marks[][] = new int[8][3];
    String res[] = new String[8];
    String subjects[] = new String[8];
    String totalmarks = null;
    String mk;
    String name;

    ImageIcon yesIcon = new ImageIcon("Resources/yes-icon.png");
    ImageIcon noIcon = new ImageIcon("Resources/no-icon.png");

    public void fetchSubjectNames(String usn) {

        //get proxy Settings
        setProxy();
        String url = "http://results.vtualerts.com/get_res.php?usn=" + usn;
        Document doc;

        try {
            doc = Jsoup.connect(url).userAgent("Mozilla").timeout(25 * 1000).get();

            Element firstTableMarks = doc.select("table:eq(3)").first();
            Element tmtbody = firstTableMarks.select("tbody").first();

            for (int i = 0; i < 8; i++) {
                String whichTr = "tr:eq(" + (i + 1) + ")";
                MainForm.subNamesV.add(tmtbody.select(whichTr).first().child(0).text().trim());
            }
        } catch (IOException e) {
            //MainForm.stopFlag = true;
            JOptionPane.showMessageDialog(null, e + " tyfghfg resultfetch");
        }

        //Store subject names to database
        Connection con = DBConnect.connection;
        String sql = "DELETE from SUBJECTTABLE";

        Statement stmt;
        try {
            stmt = con.createStatement();
            stmt.executeUpdate(sql);
        } catch (SQLException ex) {
            Logger.getLogger(resultFetch.class.getName()).log(Level.SEVERE, null, ex);
        }

        for (int i = 0; i < MainForm.subNamesV.size(); i++) {
            String sql1 = "INSERT INTO SUBJECTTABLE VALUES ('" + MainForm.subNamesV.get(i) + "')";
            try {

                Statement stmt1 = con.createStatement();
                stmt1.executeUpdate(sql1);

            } catch (SQLException ex) {
                Logger.getLogger(resultFetch.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    public resultFetch() {
        setProxy();
    }

    public boolean FetchTheresult(String usn) {
        String temp;
        Document doc;
        String url = "http://results.vtualerts.com/get_res.php?usn=" + usn;
        try {
            doc = Jsoup.connect(url).userAgent("Mozilla").timeout(25 * 1000).get();

            Element StdName = doc.select("div").select("B:eq(0)").first();
            name = StdName.toString().split(">")[1].split(Pattern.quote("("))[0];
            System.out.println("name is : " + name);

            Element markclass = doc.select("table:eq(1)").select("td:eq(3)").select("b:eq(0)").first();
            mk = markclass.toString().split(";")[2].split("<")[0];
            Element firstTableMarks = doc.select("table:eq(3)").first();

            Element tmtbody = firstTableMarks.select("tbody").first();

            for (int i = 0; i < 8; i++) {
                String whichTr = "tr:eq(" + (i + 1) + ")";
                subjects[i] = tmtbody.select(whichTr).first().child(0).text();

                marks[i][0] = Integer.parseInt(tmtbody.select(whichTr).first().child(1).text());
                marks[i][1] = Integer.parseInt(tmtbody.select(whichTr).first().child(2).text());
                marks[i][2] = Integer.parseInt(tmtbody.select(whichTr).first().child(3).text());
                res[i] = tmtbody.select(whichTr).first().child(4).text();

                Element totMks = doc.select("table:eq(4)").select("td:eq(3)").first();
                totalmarks = totMks.toString().split(" ")[1];
            }
           // MainForm.DF.setStatus(usn, yesIcon);
            MainForm.DF.setStatus(usn, "Success");
        } catch (Exception e) {
            //JOptionPane.showMessageDialog(null, e + " fetch the result");
            System.out.println("Here is the exception");
            // MainForm.stopFlag = true;
            MainForm.DF.setStatus(usn, "failed");
            //MainForm.DF.setStatus(usn, noIcon);
            return false;//if result is not found
        }
        return true;//result is found
    }

    private void setProxy() {
        System.out.println("hello world");
        try {
            String ProxyIP, SecureIP, ProxyPort, SecurePort;
            Properties prop = new Properties();
            prop.load(new FileInputStream("config.ini"));
            ProxyIP = prop.getProperty("HTTPProxy");
            ProxyPort = prop.getProperty("HTTPPort");
            SecureIP = prop.getProperty("HTTPSProxy");
            SecurePort = prop.getProperty("HTTPSPort");
            System.out.println("Proxy is set to " + ProxyIP + ":" + ProxyPort);
            if (!ProxyIP.equals("")) {
                System.out.println("Proxy is set to " + ProxyIP + ":" + ProxyPort);
                System.out.println("Secure Proxy is set to " + SecureIP + ":" + SecurePort);

                System.setProperty("http.proxyHost", ProxyIP);
                System.setProperty("http.proxyPort", ProxyPort);
                System.setProperty("https.proxyHost", SecureIP);
                System.setProperty("https.proxyPort", SecurePort);
            }

        } catch (IOException ex) {
            System.out.println("Can not read config file...");
        }
    }
}
