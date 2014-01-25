/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author DELL
 */
public class SaveTable {

    public SaveTable(DefaultTableModel dataModel) {
        JFileChooser chooser = null;
        BufferedWriter bfw = null;
        chooser = new JFileChooser();
        chooser.setAcceptAllFileFilterUsed(false);
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Excel Sheet", ".CSV", "CSV");
        chooser.setFileFilter(filter);
        int actionDialog = chooser.showSaveDialog(null);
        if (actionDialog == JFileChooser.APPROVE_OPTION) {

            try {
                File fileName = new File(chooser.getSelectedFile().toString());
                if (fileName == null) {
                    return;
                }
                if (fileName.exists()) {
                    actionDialog = JOptionPane.showConfirmDialog(null,
                            "Replace existing file?");
                    // may need to check for cancel option as well
                    if (actionDialog == JOptionPane.NO_OPTION) {
                        return;
                    }
                }

                System.out.println("Writing to File" + fileName);
                bfw = new BufferedWriter(new FileWriter(fileName + ".csv"));
                //bfw.write("hello world");
                for (int i = 0; i < dataModel.getColumnCount(); i++) {
                    try {
                        bfw.write(dataModel.getColumnName(i));
                        bfw.write(", ");
                    } catch (IOException ex) {
                        Logger.getLogger(SaveTable.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                for (int i = 0; i < dataModel.getRowCount(); i++) {
                    try {
                        bfw.newLine();
                        for (int j = 0; j < dataModel.getColumnCount(); j++) {
                            bfw.write(dataModel.getValueAt(i, j).toString());
                            bfw.write(", ");
                        }
                    } catch (IOException ex) {
                        Logger.getLogger(SaveTable.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                bfw.close();
            } catch (IOException ex) {
                Logger.getLogger(SaveTable.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                bfw.close();
            } catch (IOException ex) {
                Logger.getLogger(SaveTable.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
