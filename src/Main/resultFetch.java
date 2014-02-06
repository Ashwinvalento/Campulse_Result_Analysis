package Main;

import Forms.MainForm;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ProxySelector;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.regex.Pattern;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class resultFetch {

    String result;
    int marks[][] = new int[8][3];
    String res[] = new String[8];
    String subjects[] = new String[8];
    String totalmarks = null;
    String mk;
    String name;

    public resultFetch() {
        setProxy();
    }

    public boolean FetchTheresult(String usn, int sem) {
        Document doc;
        //String url = "http://results.vtualerts.com/get_res.php?usn=" + usn ;
        String url = "http://results.vtualerts.com/get_res.php?usn=" + usn;
        if (Forms.EnterUsnForm.sem > 0 && Forms.EnterUsnForm.sem < 9) {
            url = "http://results.vtualerts.com/get_res.php?usn=" + usn + "&sem=" + sem;
        }
        //System.out.println(url);
        try {
            doc = Jsoup.connect(url).userAgent("Mozilla").timeout(Forms.MainForm.timeout * 1000).get();
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
            MainForm.DF.setStatus(usn, "Success");
            MainForm.log(usn + " Download : Success");            
        } catch (Exception e) {
            return checkIfResultisAvailable(usn, sem);
            /*
             MainForm.RetryList.add(usn);
             System.out.println("usn added for retry !");
             System.out.println("fetch failed for " + usn);
             MainForm.DF.setStatus(usn, "failed");*/
            ///return false;//if result is not found
        }
        return true;//result is found
    }

    private void setProxy() {
        try {
            String ProxyIP, SecureIP, ProxyPort, SecurePort;
            Properties prop = new Properties();
            prop.load(new FileInputStream("config.ini"));

            if (prop.getProperty("NoProxy").equals("true")) {
                MainForm.log("Setting Proxy : No proxy ");
            } else if (prop.getProperty("SysProxy").equals("true")) {
                MainForm.log("Setting Proxy : System Proxy");
                setSysProx();
            } else {
                MainForm.log("Setting Proxy : User Defined Proxy");
                ProxyIP = prop.getProperty("HTTPProxy");
                ProxyPort = prop.getProperty("HTTPPort");
                SecureIP = prop.getProperty("HTTPSProxy");
                SecurePort = prop.getProperty("HTTPSPort");
                System.setProperty("http.proxyHost", ProxyIP);
                System.setProperty("http.proxyPort", ProxyPort);
                System.setProperty("https.proxyHost", SecureIP);
                System.setProperty("https.proxyPort", SecurePort);
                MainForm.log("Proxy Set to : " + ProxyIP + ":" + ProxyPort);
            }
        } catch (IOException ex) {
            MainForm.logError("Can not read configuration file... using System proxy");
            setSysProx();
        }
    }

    private void setSysProx() {
        System.setProperty("java.net.useSystemProxies", "true");
        MainForm.log("detecting proxies");
        List l = null;
        try {
            l = ProxySelector.getDefault().select(new URI("http://foo/bar"));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        if (l != null) {
            for (Iterator iter = l.iterator(); iter.hasNext();) {
                java.net.Proxy proxy = (java.net.Proxy) iter.next();
                System.out.println("proxy hostname : " + proxy.type());

                InetSocketAddress addr = (InetSocketAddress) proxy.address();

                if (addr == null) {
                    MainForm.log("Setting Proxy : No Proxy");
                } else {
                    System.out.println("proxy hostname : " + addr.getHostName());
                    System.setProperty("http.proxyHost", addr.getHostName());
                    System.out.println("proxy port : " + addr.getPort());
                    System.setProperty("http.proxyPort", Integer.toString(addr.getPort()));
                    MainForm.log("Proxy Set to : " + addr.getHostName() + ":" + addr.getPort());
                }
            }
        }
    }

    private boolean checkIfResultisAvailable(String usn, int sem) {
        Document doc;
        MainForm.log(usn + " Checking if result is available");
        System.out.println("Checking if result is available");
        String resultNotAvail = "<h3>Sorry Results are not yet available for this university seat number<br />or it might not be a valid university seat number</h3>";
        String url = "http://results.vtualerts.com/get_res.php?usn=" + usn;
        if (Forms.EnterUsnForm.sem > 0 && Forms.EnterUsnForm.sem < 9) {
            url = "http://results.vtualerts.com/get_res.php?usn=" + usn + "&sem=" + sem;
        }
        try {
            doc = Jsoup.connect(url).userAgent("Mozilla").timeout(Forms.MainForm.timeout * 1000).get();
            Element StdName = doc.select("center").first().child(0);
            System.out.println(StdName);
            if (StdName.toString().equals(resultNotAvail)) {
                MainForm.log(usn + " Download : Result not available");
                MainForm.DF.setStatus(usn, "Not Available");
            }

        } catch (Exception e) {
            return checkIfHeHasBackPaper(usn, sem);
            /*MainForm.RetryList.add(usn);
             System.out.println("usn added for retry !");
             System.out.println("fetch failed for " + usn);
             MainForm.DF.setStatus(usn, "failed");
             */
        }
        return false;
    }

    private boolean checkIfHeHasBackPaper(String usn, int sem) {

        System.out.println("Checking IF back paper");
        MainForm.log(usn + " Checking For back papers");
        int totMark = 0;
        Document doc;
        String url = "http://results.vtualerts.com/get_res.php?usn=" + usn;

        try {
            doc = Jsoup.connect(url).userAgent("Mozilla").timeout(Forms.MainForm.timeout * 1000).get();
            Element markclass = doc.select("div").select("div").select("table").get(2);

            Element tmtbody = markclass.select("tbody").first();
            //System.out.println(tmtbody);
            totalmarks = null;
            for (int i = 0; i < 8; i++) {
                String whichTr = "tr:eq(" + (i + 1) + ")";
                subjects[i] = tmtbody.select(whichTr).first().child(0).text();
                marks[i][0] = Integer.parseInt(tmtbody.select(whichTr).first().child(1).text());
                marks[i][1] = Integer.parseInt(tmtbody.select(whichTr).first().child(2).text());
                marks[i][2] = Integer.parseInt(tmtbody.select(whichTr).first().child(3).text());
                res[i] = tmtbody.select(whichTr).first().child(4).text();
                totMark += marks[i][2];

            }
            totalmarks = String.valueOf(totMark);
            mk = "NA / Has back paper";
            MainForm.DF.setStatus(usn, "Success");
            MainForm.log(usn + " Download : Success");
        } catch (Exception e) {
            MainForm.RetryList.add(usn);
            System.out.println(usn + " added for retry !");
            MainForm.DF.setStatus(usn, "failed");
            MainForm.log(usn + " Download : failed");
            return false;
        }

        return true;
    }
}
