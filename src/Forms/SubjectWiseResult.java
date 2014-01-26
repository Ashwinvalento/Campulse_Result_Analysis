/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Forms;

import Main.SaveTable;
import Main.resultFetch;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.table.DefaultTableModel;
import run.DBConnect;

public class SubjectWiseResult extends javax.swing.JFrame {

    private int whichROw;
    DefaultTableModel model;

    public SubjectWiseResult() {
        initComponents();
        this.setLocationRelativeTo(null);
        retrieveSubjectNames();
        fillSubjectCombo();
        

        model = new DefaultTableModel() {
            Class[] types = new Class[]{
                //COL. TYPES ARE HERE!!!  
                java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.String.class
            };

            @Override
            public Class getColumnClass(int columnIndex) {
                return types[columnIndex];
            }
        };
        // StudentMarksTable.setModel(model);
        model.addColumn("USN");
        model.addColumn("NAME");
        model.addColumn("INTERNAL");
        model.addColumn("EXTERNAL");
        model.addColumn("TOTAL");
        model.addColumn("CLASS");
        bSubmit.doClick();
        studentMarksTable.setAutoCreateRowSorter(true);
        studentMarksTable.getColumn("USN").setPreferredWidth(100);
        studentMarksTable.getColumn("NAME").setPreferredWidth(150);
        studentMarksTable.getColumn("INTERNAL").setPreferredWidth(50);
        studentMarksTable.getColumn("EXTERNAL").setPreferredWidth(50);
        studentMarksTable.getColumn("TOTAL").setPreferredWidth(40);
        studentMarksTable.getColumn("CLASS").setPreferredWidth(40);
        examType.setSelectedIndex(2);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        subjectCombo = new javax.swing.JComboBox();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        bSubmit = new javax.swing.JButton();
        lastValue = new javax.swing.JSpinner();
        firstValue = new javax.swing.JSpinner();
        examType = new javax.swing.JComboBox();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        studentMarksTable = new javax.swing.JTable();
        lcount = new javax.swing.JLabel();
        bSave = new javax.swing.JButton();
        bClose = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Subject Wise Result");

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel3.setText("in");

        subjectCombo.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        subjectCombo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "All", "Any" }));
        subjectCombo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                subjectComboActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel2.setText("and");

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel1.setText("How many got  ");

        bSubmit.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        bSubmit.setText("OK");
        bSubmit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bSubmitActionPerformed(evt);
            }
        });

        lastValue.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lastValue.setModel(new javax.swing.SpinnerNumberModel(125, 0, 200, 1));

        firstValue.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        firstValue.setModel(new javax.swing.SpinnerNumberModel(50, 0, 125, 1));

        examType.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        examType.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Internal", "External", "Total" }));
        examType.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                examTypeActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel4.setText("mark between");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(53, 53, 53)
                        .addComponent(subjectCombo, javax.swing.GroupLayout.PREFERRED_SIZE, 315, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(46, 46, 46)
                        .addComponent(bSubmit, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(examType, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel4)
                        .addGap(18, 18, 18)
                        .addComponent(firstValue, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel2)
                        .addGap(18, 18, 18)
                        .addComponent(lastValue, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(24, Short.MAX_VALUE))
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {firstValue, lastValue});

        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(firstValue, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(lastValue, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(examType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(subjectCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bSubmit))
                .addContainerGap(29, Short.MAX_VALUE))
        );

        studentMarksTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "USN", "Name", "Internal", "External", "Total"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(studentMarksTable);

        lcount.setForeground(new java.awt.Color(0, 0, 255));
        lcount.setText("COUNT WILL BE DIPLAYED HERE");

        bSave.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        bSave.setText("Save Results");
        bSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bSaveActionPerformed(evt);
            }
        });

        bClose.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        bClose.setText("Close");
        bClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bCloseActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(bSave)
                .addGap(39, 39, 39)
                .addComponent(bClose)
                .addGap(47, 47, 47))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 650, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lcount)
                            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(21, Short.MAX_VALUE))
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {bClose, bSave});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lcount)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 233, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(bSave, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bClose, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(16, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void subjectComboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_subjectComboActionPerformed
        bSubmit.doClick();
    }//GEN-LAST:event_subjectComboActionPerformed

    private void bSubmitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bSubmitActionPerformed
        Statement stmt = null;
        ResultSet rs = null;
        String query = "";
        Connection con = DBConnect.connection;
        int lowLimit = Integer.parseInt(firstValue.getValue().toString()) - 1;
        int highLimit = Integer.parseInt(lastValue.getValue().toString()) + 1;
        int typeValue=examType.getSelectedIndex()+1;
        //get USN from user

        model.setRowCount(0);
        studentMarksTable.setModel(model);
        query = "select DISTINCT * from RESULTTABLE";
        //query = "select DISTINCT NAME,USN from RESULTTABLE";
        try {
            //stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            stmt = con.createStatement();
            rs = stmt.executeQuery(query);
            int rowCount = 0;
            while (rs.next()) {
                if (subjectCombo.getSelectedIndex() == 8) {
                    
                    int flag = 0;
                    for (int i = 3+examType.getSelectedIndex(); i < 27; i += 4) {
                        if (Integer.parseInt(rs.getString(i)) < lowLimit+1 || Integer.parseInt(rs.getString(i)) > highLimit) {
                            flag = 1;
                 //           System.out.println("i is " + i + "value at ith row is :  "+rs.getString(i));
                            break;
                        }
                    }
                    if (flag == 0) {
                        model.insertRow(rowCount++, new Object[]{rs.getString(1), rs.getString(2), rs.getString(35), rs.getString(36), rs.getString(36)});
                    }

                } else if (subjectCombo.getSelectedIndex() == 9) {
                    int flag = 0;
                    for (int i = 3+examType.getSelectedIndex(); i < 27; i += 4) {
                        if (Integer.parseInt(rs.getString(i)) > lowLimit && Integer.parseInt(rs.getString(i)) < highLimit) {
                            flag = 1;
                            break;
                        }
                    }
                    if (flag == 1) {
                        model.insertRow(rowCount++, new Object[]{rs.getString(1), rs.getString(2), rs.getString(35), rs.getString(36), rs.getString(36)});
                    }
                } else {
                    whichROw = subjectCombo.getSelectedIndex() * 4 +3;
                   // System.out.println("whichROw : "+ whichROw );
                    //System.out.println("typeValue : "+ typeValue);
                    //System.out.println(rs.getString(1) + "  " + rs.getString(whichROw) + " " + rs.getString(whichROw + 1) + " " + rs.getString(whichROw + 2) + " " + rs.getString(whichROw + 3));
                  //  System.out.println("lowLimit is : "+lowLimit + " highLimit is : " +highLimit + " total is : " + Integer.parseInt(rs.getString(whichROw+2)));
                    if (Integer.parseInt(rs.getString(whichROw+ typeValue-1)) > lowLimit) {
                        if (Integer.parseInt(rs.getString(whichROw+ typeValue-1)) < highLimit) {
                            model.insertRow(rowCount++, new Object[]{rs.getString(1), rs.getString(2), Integer.parseInt(rs.getString(whichROw)), Integer.parseInt(rs.getString(whichROw + 1)), Integer.parseInt(rs.getString(whichROw + 2)), rs.getString(whichROw + 3)});
                            //System.out.println(rs.getString(1) + "  " + rs.getString(whichROw) + " " + rs.getString(whichROw + 1) + " " + rs.getString(whichROw + 2) + " " + rs.getString(whichROw + 3));
                        } else {

                        }
                    } else {
                        System.out.println(rs.getString(1) + " is not listed");
                    }
                }
            }

        } catch (SQLException ex) {
            System.out.println("Error : " + ex);
        }
        lcount.setText(Integer.toString(model.getRowCount()) + " of them scored between " + firstValue.getValue() + " and " + lastValue.getValue() +" in " + subjectCombo.getSelectedItem().toString() + " "+ examType.getSelectedItem().toString());

    }//GEN-LAST:event_bSubmitActionPerformed

    private void bCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bCloseActionPerformed
        this.dispose();
    }//GEN-LAST:event_bCloseActionPerformed

    private void bSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bSaveActionPerformed
       SaveTable ST =  new SaveTable(model);
    }//GEN-LAST:event_bSaveActionPerformed

    private void examTypeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_examTypeActionPerformed
        if(examType.getSelectedIndex()==0) {
            firstValue.setValue(15);
            lastValue.setValue(25);
        } else if(examType.getSelectedIndex()==1) {
            firstValue.setValue(35);
            lastValue.setValue(100);
        } else {
            firstValue.setValue(50);
            lastValue.setValue(125);
        }
        bSubmit.doClick();
        
    }//GEN-LAST:event_examTypeActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(SubjectWiseResult.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SubjectWiseResult.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SubjectWiseResult.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SubjectWiseResult.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new SubjectWiseResult().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bClose;
    private javax.swing.JButton bSave;
    private javax.swing.JButton bSubmit;
    private javax.swing.JComboBox examType;
    private javax.swing.JSpinner firstValue;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSpinner lastValue;
    private javax.swing.JLabel lcount;
    private javax.swing.JTable studentMarksTable;
    private javax.swing.JComboBox subjectCombo;
    // End of variables declaration//GEN-END:variables

    private void fillSubjectCombo() {

        Vector comboBoxItems = new Vector();

        for (int i = 0; i < MainForm.subNamesV.size(); i++) {
            comboBoxItems.add(MainForm.subNamesV.get(i));
        }
        comboBoxItems.add("All");
        comboBoxItems.add("Any");
        DefaultComboBoxModel model = new DefaultComboBoxModel(comboBoxItems);

        subjectCombo.setModel(model);

    }

    private void retrieveSubjectNames() {
        MainForm.subNamesV.clear();
        Connection con = DBConnect.connection;
        ResultSet rs = null;
        String sql = "Select * From SUBJECTTABLE";
        try {
            Statement stmt = con.createStatement();
            rs = stmt.executeQuery(sql);

            while (rs.next()) {
                MainForm.subNamesV.add(rs.getString(1));
            }

        } catch (SQLException ex) {
            Logger.getLogger(resultFetch.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
