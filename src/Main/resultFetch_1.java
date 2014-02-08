package Main;

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
import org.jsoup.select.Elements;

public class resultFetch_1 {

    int marks[][] = new int[20][3];
    String res[] = new String[8];
    String subjects[] = new String[20];
    String totalmarks = null;
    String mk;
    String name;
    int subs = 0;

    public resultFetch_1() {
        setProxy();
    }

    public boolean FetchTheresult(String usn, int sem) {
        Document doc;
        //String url = "http://results.vtualerts.com/get_res.php?usn=" + usn ;
        String url = "http://results.vtualerts.com/get_res.php?usn=" + usn+ "&sem=" + sem;
        if (Forms.EnterUsnForm.sem > 0 && Forms.EnterUsnForm.sem < 9) {
            url = "http://results.vtualerts.com/get_res.php?usn=" + usn + "&sem=" + sem;
        }
        //System.out.println(url);
        try {
            doc = Jsoup.connect(url).userAgent("Mozilla").get();
            Element StdName = doc.select("div").select("B:eq(0)").first();
            name = StdName.toString().split(">")[1].split(Pattern.quote("("))[0];

            Element Stdsem = doc.select("div").get(1).select("B").get(2);
            System.out.println(Stdsem.text());

            if (sem < Integer.parseInt(Stdsem.text())) {
                System.out.println("Year out");
                return false;
            }
            /* System.out.println("name is : " + name);
             if (name.equalsIgnoreCase("Semester:</b")) {
             System.out.println("Year out");

             return false;
             }*/
            Element markclass = doc.select("table:eq(1)").select("td:eq(3)").select("b:eq(0)").first();
            mk = markclass.toString().split(";")[2].split("<")[0];
            Element firstTableMarks = doc.select("table:eq(3)").first();

            Element tmtbody = firstTableMarks.select("tbody").first();

            if (sem == 8) {
                subs = 6;
            } else {
                subs = 8;
            }
            for (int i = 0; i < subs; i++) {
                String whichTr = "tr:eq(" + (i + 1) + ")";
                subjects[i] = tmtbody.select(whichTr).first().child(0).text();
                marks[i][0] = Integer.parseInt(tmtbody.select(whichTr).first().child(1).text());
                marks[i][1] = Integer.parseInt(tmtbody.select(whichTr).first().child(2).text());
                marks[i][2] = Integer.parseInt(tmtbody.select(whichTr).first().child(3).text());
                res[i] = tmtbody.select(whichTr).first().child(4).text();

                Element totMks = doc.select("table:eq(4)").select("td:eq(3)").first();
                totalmarks = totMks.toString().split(" ")[1];
            }

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
                //              MainForm.log("Setting Proxy : No proxy ");
            } else if (prop.getProperty("SysProxy").equals("true")) {
//                MainForm.log("Setting Proxy : System Proxy");
                setSysProx();
            } else {
                // MainForm.log("Setting Proxy : User Defined Proxy");
                ProxyIP = prop.getProperty("HTTPProxy");
                ProxyPort = prop.getProperty("HTTPPort");
                SecureIP = prop.getProperty("HTTPSProxy");
                SecurePort = prop.getProperty("HTTPSPort");
                System.setProperty("http.proxyHost", ProxyIP);
                System.setProperty("http.proxyPort", ProxyPort);
                System.setProperty("https.proxyHost", SecureIP);
                System.setProperty("https.proxyPort", SecurePort);
                //    MainForm.log("Proxy Set to : " + ProxyIP + ":" + ProxyPort);
            }
        } catch (IOException ex) {
            //        MainForm.logError("Can not read configuration file... using System proxy");
            setSysProx();
        }
    }

    private void setSysProx() {
        System.setProperty("java.net.useSystemProxies", "true");
        //  MainForm.log("detecting proxies");
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
                    //       MainForm.log("Setting Proxy : No Proxy");
                } else {
                    System.out.println("proxy hostname : " + addr.getHostName());
                    System.setProperty("http.proxyHost", addr.getHostName());
                    System.out.println("proxy port : " + addr.getPort());
                    System.setProperty("http.proxyPort", Integer.toString(addr.getPort()));
                    //            MainForm.log("Proxy Set to : " + addr.getHostName() + ":" + addr.getPort());
                }
            }
        }
    }

    private boolean checkIfResultisAvailable(String usn, int sem) {
        Document doc;
        //MainForm.log(usn + " Checking if result is available");
        System.out.println("Checking if result is available");
        String resultNotAvail = "<h3>Sorry Results are not yet available for this university seat number<br />or it might not be a valid university seat number</h3>";
        String url = "http://results.vtualerts.com/get_res.php?usn=" + usn;
        if (Forms.EnterUsnForm.sem > 0 && Forms.EnterUsnForm.sem < 9) {
            url = "http://results.vtualerts.com/get_res.php?usn=" + usn + "&sem=" + sem;
        }
        try {
            doc = Jsoup.connect(url).userAgent("Mozilla").timeout(Forms.MainForm.timeout * 1000).get();
            System.out.println("comes to try result available");
            Element StdName = doc.select("center").first().child(0);
            System.out.println(StdName);
            if (StdName.toString().equals(resultNotAvail)) {
                //  MainForm.log(usn + " Download : Result not available");
                //  MainForm.DF.setStatus(usn, "Not Available");
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
        subs = 0;
        System.out.println("Checking IF back paper");
        // MainForm.log(usn + " Checking For back papers");
        int totMark = 0;
        Document doc;
        String url = "http://results.vtualerts.com/get_res.php?usn=" + usn + "&sem=" + sem;

        try {
            doc = Jsoup.connect(url).userAgent("Mozilla").timeout(Forms.MainForm.timeout * 1000).get();
            System.out.println("comes to try back paper");

            Element StdName = doc.select("div").select("B:eq(0)").first();
            name = StdName.toString().split(">")[1].split(Pattern.quote("("))[0];
            System.out.println("name is : " + name);

            Element markclass = doc.select("table:eq(1)").select("td:eq(3)").select("b:eq(0)").first();
            mk = markclass.toString().split(";")[2].split("<")[0];
            System.out.println("Class is : " + mk);

            int skipTable = 0, skipRow = 0; // skip the google adds table and name and sem table
            for (Element table : doc.select("table")) {
                if (skipTable < 2) {
                    skipTable++;
                    System.out.println("skipped");
                    continue;
                }
                System.out.println("--------------------\nNEW TABLE\n");
                for (Element row : table.select("tr")) {
                    if (skipRow < 1) {
                        System.out.println("1 row skipped");
                        skipRow++;
                        continue;
                    }
                    System.out.println("\nNEW ROW\n");
                    Elements tds = row.select("td");
                    if (tds.size() > 0) {
                        System.out.println("Subject = " + tds.get(0).select("i").text());
                        subjects[subs] = tds.get(0).select("i").text();

                        System.out.println("Internal = " + tds.get(1).select("td").text());
                        marks[subs][0] = Integer.parseInt(tds.get(1).select("td").text());

                        System.out.println("External = " + tds.get(2).select("td").text());
                        marks[subs][1] = Integer.parseInt(tds.get(2).select("td").text());

                        System.out.println("Total = " + tds.get(3).select("td").text());
                        marks[subs][2] = Integer.parseInt(tds.get(3).select("td").text());

                        System.out.println("Result = " + tds.get(4).select("td").text());
                        res[subs++] = tds.get(4).select("td").text();

                    }
                }
                skipTable = 1;
                skipRow = 0;
                System.out.println("--------------------\n");
            }
            totalmarks = String.valueOf(totMark);

            //      MainForm.DF.setStatus(usn, "Success");
            //      MainForm.log(usn + " Download : Success");
        } catch (Exception e) {
            //    MainForm.RetryList.add(usn);
            System.out.println(usn + " added for retry !");
            //   MainForm.DF.setStatus(usn, "failed");
            //  MainForm.log(usn + " Download : failed");
            return false;
        }

        return true;
    }

    public static void main(String[] args) {
        resultFetch_1 rs = new resultFetch_1();
        rs.FetchTheresult("4PA10CS072", 6);
    }
}
