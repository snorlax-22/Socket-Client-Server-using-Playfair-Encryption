/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import dbconnection.dbaccess;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author hesac
 */
public class ServerUI extends javax.swing.JFrame {

    /**
     * Creates new form ServerUI
     */
    public ServerUI() {
        initComponents();
        initServer();
    }

    private void initServer() {
        //this.objServer = new Server();
        ServerRunner.objJTextArea = this.jTextAreaLogging;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jDialogHistory = new javax.swing.JDialog();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextAreaHistory = new javax.swing.JTextArea();
        jButtonStart = new javax.swing.JButton();
        jButtonStop = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextAreaLogging = new javax.swing.JTextArea();
        jLabel1 = new javax.swing.JLabel();
        jTextFieldServerStatus = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jButtonHistory = new javax.swing.JButton();

        jDialogHistory.setMinimumSize(new java.awt.Dimension(545, 578));

        jTextAreaHistory.setColumns(20);
        jTextAreaHistory.setRows(5);
        jScrollPane2.setViewportView(jTextAreaHistory);

        javax.swing.GroupLayout jDialogHistoryLayout = new javax.swing.GroupLayout(jDialogHistory.getContentPane());
        jDialogHistory.getContentPane().setLayout(jDialogHistoryLayout);
        jDialogHistoryLayout.setHorizontalGroup(
            jDialogHistoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDialogHistoryLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 525, Short.MAX_VALUE)
                .addContainerGap())
        );
        jDialogHistoryLayout.setVerticalGroup(
            jDialogHistoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDialogHistoryLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 556, Short.MAX_VALUE)
                .addContainerGap())
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jButtonStart.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        jButtonStart.setText("Khởi chạy");
        jButtonStart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonStartActionPerformed(evt);
            }
        });

        jButtonStop.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        jButtonStop.setText("Dừng lại");
        jButtonStop.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonStopActionPerformed(evt);
            }
        });

        jTextAreaLogging.setColumns(20);
        jTextAreaLogging.setRows(5);
        jScrollPane1.setViewportView(jTextAreaLogging);

        jLabel1.setText("Trạng thái server :");

        jTextFieldServerStatus.setEditable(false);

        jLabel2.setText("Log :");

        jButtonHistory.setText("Lịch sử");
        jButtonHistory.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonHistoryActionPerformed(evt);
            }
        });
        //jButtonHistory.setVisible(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(203, 203, 203)
                .addComponent(jButtonStart)
                .addGap(107, 107, 107)
                .addComponent(jButtonStop)
                .addContainerGap(199, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(166, 166, 166)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(jTextFieldServerStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButtonHistory)
                .addGap(25, 25, 25))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonStart)
                    .addComponent(jButtonStop))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 26, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jTextFieldServerStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonHistory))
                .addGap(5, 5, 5)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 258, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonStartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonStartActionPerformed
        if (objServer == null || !objServer.isBolRunning()) {
            objServer = new Server();
            objServer.start();
            if (objServer.isBolIsConnected() && objServer.isBolRunning()) {
                this.jTextFieldServerStatus.setText("Server đang chạy");
            }
        } else {
            objServer.start();
            if (objServer.isBolRunning()) {
                this.jTextFieldServerStatus.setText("Server đang chạy");
            }
        }
    }//GEN-LAST:event_jButtonStartActionPerformed

    private void jButtonStopActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonStopActionPerformed
        this.objServer.stopServer();
        if (!objServer.isBolRunning()) {
            this.jTextFieldServerStatus.setText("Server đã dừng");
        }
    }//GEN-LAST:event_jButtonStopActionPerformed

    private void jButtonHistoryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonHistoryActionPerformed
        this.jTextAreaLogging.setText("");
        String str = getHistory();
        this.jTextAreaHistory.setText(str);
        this.jDialogHistory.setVisible(true);
        this.jDialogHistory.setLocationRelativeTo(null);
    }//GEN-LAST:event_jButtonHistoryActionPerformed

    private String getHistory() {

        try {
            String str = "";
            dbaccess con = new dbaccess();
            ResultSet rs = con.getAll("SELECT * FROM Cypher");
            while (rs.next()) {
                str += rs.getInt("id") + " ";
                str += "  |  ";
                str += rs.getString("cypher") + " ";
                str += "  |  ";
                str += rs.getString("keyy") + " ";
                str += "\n";
            }
            con.close();
            return str;
        } catch (SQLException | IOException ex) {
            return "";
        }

    }

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
            java.util.logging.Logger.getLogger(ServerUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ServerUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ServerUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ServerUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ServerUI().setVisible(true);
            }
        });
    }

    private Server objServer;

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonHistory;
    private javax.swing.JButton jButtonStart;
    private javax.swing.JButton jButtonStop;
    private javax.swing.JDialog jDialogHistory;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea jTextAreaHistory;
    private javax.swing.JTextArea jTextAreaLogging;
    private javax.swing.JTextField jTextFieldServerStatus;
    // End of variables declaration//GEN-END:variables
}
