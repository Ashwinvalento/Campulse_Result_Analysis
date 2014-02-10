/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

/**
 *
 * @author Jickson
 */
public class resultFetchVTU {

    int marks[][] = new int[8][3];
    String res[] = new String[8];
    String subjects[] = new String[8];
    String totalmarks = null;
    String mk;
    int total;
    String name;

    public boolean FetchTheresult(String usn, int sem) {
        String temp;
        Document doc;
        String url = "http://results.vtu.ac.in/vitavi.php?submit=true&rid=" + usn;
        total = 0;

        try {

            doc = Jsoup.connect(url).get();

            Element firstTableMarks = doc.select("table[bgColor=#ffffff]").first();
            Element tbody = firstTableMarks.select("tbody").first();
            Element firstTr = tbody.select("tr:eq(1)").first();
            Element ftrtd = firstTr.select("td:eq(0)").first();

            temp = ftrtd.select("b:eq(0)").first().text();
            name = temp.split("\\(")[0];

            System.out.println("name :" + name);
            sem = Integer.parseInt(ftrtd.select("table").first().select("tr:eq(0)").first().select("td:eq(1)").first().text());
            mk = ftrtd.select("table").first().select("tr:eq(0)").first().select("td:eq(3)").first().text().split(":")[1].replaceAll("[^A-Za-z0-9 ]", "");
            System.out.println("result :" + mk);
            Element tmtbody = ftrtd.select("table").get(1).select("tbody:eq(0)").first();

            for (int i = 0; i < 8; i++) {
                String whichTr = "tr:eq(" + (i + 1) + ")";
                subjects[i] = tmtbody.select(whichTr).first().child(0).text();
                marks[i][0] = Integer.parseInt(tmtbody.select(whichTr).first().child(1).text());
                marks[i][1] = Integer.parseInt(tmtbody.select(whichTr).first().child(2).text());
                marks[i][2] = Integer.parseInt(tmtbody.select(whichTr).first().child(3).text());
                res[i] = tmtbody.select(whichTr).first().child(4).text();
                total += marks[i][2];
                System.out.println(subjects[i]);
                System.out.println(marks[i][0]);
                System.out.println(marks[i][1]);
                System.out.println(marks[i][2]);
                System.out.println(res[i]);
            }

            Element tot = ftrtd.select("table").get(2).select("tbody:eq(0)").select("td:eq(3)").first();
            System.out.println(tot);
            totalmarks = tot.toString().split(">")[1].split("&")[0].trim();
            if (totalmarks.matches("[0-9][0-9][0-9]")) {
                System.out.println("inside");
            } else {
                System.out.println("outside");
                totalmarks = Integer.toString(total);
            }
            System.out.println(totalmarks);
        } catch (Exception e) {
            e.printStackTrace();
            // JOptionPane.showMessageDialog(null, e);
            return false;//if result is not found
        }
        return true;//result is found
    }

    public static void main(String[] args) {
        resultFetchVTU rs = new resultFetchVTU();
        rs.FetchTheresult("4PA11EC013", 2);
    }
}
