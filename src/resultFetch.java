/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.IOException;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

/**
 *
 * @author Jickson
 */
public class resultFetch implements run.MapInterface {

    String result;
    int sem;
    int marks[][] = new int[8][3];
    String res[] = new String[8];
    String subjects[] = new String[8];
    String totalmarks = null;
    String mk;
    String name;

    resultFetch(String usn) {
        String url = "http://results.vtualerts.com/get_res.php?usn=" + usn;
        Document doc;

        try {
            //System.out.println(url);
            doc = Jsoup.connect(url).userAgent("Mozilla").get();

            // System.out.print(doc);
            Element firstTableMarks = doc.select("table:eq(3)").first();
            Element tbody = firstTableMarks.select("tbody").first();
            Element tmtbody = tbody;

            for (int i = 0; i < 8; i++) {
                String whichTr = "tr:eq(" + (i + 1) + ")";
                subNamesV.add(tmtbody.select(whichTr).first().child(0).text());
            }
        } catch (IOException e) {
            //e.printStackTrace();
            JOptionPane.showMessageDialog(null, e);
        }

    }

    resultFetch() {

    }

    public boolean FetchTheresult(String usn) {
        String temp;
        Document doc;
        String url = "http://results.vtualerts.com/get_res.php?usn=" + usn;
        //String url="http://www.example.com";
        try {
            doc = Jsoup.connect(url).userAgent("Mozilla").get();

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
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
            return false;//if result is not found
        }
        return true;//result is found
    }
}
