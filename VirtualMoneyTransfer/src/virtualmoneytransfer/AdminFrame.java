/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package virtualmoneytransfer;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.DriverManager;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author varun
 */
public class AdminFrame extends javax.swing.JFrame {

    /**
     * Creates new form AdminFrame
     */
    public AdminFrame() {
        initComponents();
    }
public Connection startConnection()
{
    Connection con = null;
    try {
        Class.forName("org.postgresql.Driver");
        con = DriverManager.getConnection
        ("jdbc:postgresql://localhost:5432/vmt","postgres","student");
    }
    catch (Exception e)
    {
        errorMessage();
    }
    if (con == null)
        errorMessage();
    return con;
}
String mostActiveAdmin()
{String result = "";
	try
	{
                
		Connection con = startConnection();
		Statement stmt = con.createStatement();
                Statement stmt1 = con.createStatement();
                Statement stmt2 = con.createStatement();
		ResultSet res = stmt.executeQuery("select a_id,count(*) as transactions_approved from transaction group by a_id limit 1;");
		if (res.next())
                    result += "Most active admin (maximum transaction authenticated) : " + res.getInt(1) + "\n";
                ResultSet res1 = stmt1.executeQuery("select count(*) from transaction where date(creation_time) = date(now());");
                if (res1.next())
                    result += "Number of transactions today : " + res1.getInt(1) + "\n";
                ResultSet res2 = stmt2.executeQuery("select sum(transaction.amount * rates.rate) as Transaction_Volume from transaction,rates where transaction.currency = rates.symbol;");
                if (res2.next())
                    result += "Total transaction volume :  Rs. " + res2.getDouble(1) + "\n";
		stmt.close(); stmt2.close(); stmt1.close(); res.close(); res2.close(); res1.close();
		con.close();
                
	}
	catch(Exception E)	
	{
		errorMessage();
	}
        return result;

}
public void rejectTransaction(int t_id)
{
        Connection con = null;
	try
	{
		con = startConnection();
		Statement stmt = con.createStatement();
		con.setAutoCommit(false);
		ResultSet res = stmt.executeQuery("select from_id,to_id,currency,amount from token where t_id = " + t_id + ";");
                res.next();
		stmt.executeUpdate("update account set balance = balance + " + res.getDouble(4) + " where u_id = " + res.getInt(1) + " and currency ='" + res.getString(3) + "';");
		stmt.executeUpdate("delete from token where t_id = " + t_id + ";");
		stmt.close();
                con.commit();
		con.close();
                JOptionPane.showMessageDialog(null, "Transaction rejected.");
	}
	catch(Exception e)
        {
                try {con.rollback();}
                catch (Exception E) {;}
		errorMessage();
        }
}

public void confirmTransaction(int t_id,int a_id)
{
        Connection con = null;
	try{
		con = startConnection();
		Statement stmt = con.createStatement();
                Statement stmt2 = con.createStatement();
                Statement stmt3 = con.createStatement();
                Statement stmt4 = con.createStatement();
                Statement stmt5 = con.createStatement();
                con.setAutoCommit(false);
		ResultSet res = stmt.executeQuery("select * from token where t_id = " + t_id + ";");
                //System.out.println("71");
		res.next();
                int from_id = res.getInt(2);
                int to_id = res.getInt(3);
                String currency = res.getString(4);
                double amount = res.getDouble(5);
                //System.out.println("77");
                //System.out.println("insert into transaction values(" + t_id + "," + from_id + "," + to_id + ",'" + currency + "'," + amount + ",'" + res.getString(6) + "'," + a_id + ");");
                
		stmt2.executeUpdate("insert into transaction values(" + t_id + "," + from_id + "," + to_id + ",'" + currency + "'," + amount + ",'" + res.getString(6) + "'," + a_id + ");"); 
                //System.out.println("79");
                //System.out.println("update account set balance = balance + " + amount + " where u_id = " + to_id + " and currency = '" + currency + "';");
                //System.out.println("81");
                stmt3.executeUpdate("update account set balance = balance + " + amount + " where u_id = " + to_id + " and currency = '" + currency + "';");
                //System.out.println("84");
                stmt4.executeUpdate("delete from token where t_id = " + t_id + ";");
           
                t_id = 0;
        	stmt.close();
                stmt2.close();
                stmt3.close();
                stmt4.close();
                stmt5.close();
                res.close();
                con.commit();
		con.close();
                
                JOptionPane.showMessageDialog(null, "Transaction confirmed!");
	}
	catch(Exception e)
        {
            try
            {
                con.rollback();
            }
            catch (Exception E)
            {
                ;
            }
            errorMessage();
        }
}
        
int t_id = 0;
public void errorMessage()
{
    JOptionPane.showMessageDialog(null, "Something went wrong, please try again later.");
}
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tokenTable = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        userTable = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        confirmBtn = new javax.swing.JButton();
        declineBtn = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        typeCombo = new javax.swing.JComboBox<>();
        jLabel9 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        TA = new javax.swing.JTextArea();
        jButton3 = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        contactTbl = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        statsTA = new javax.swing.JTextArea();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Virtual Money Tranfer Administrator");
        setLocation(new java.awt.Point(350, 150));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        jTabbedPane1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jTabbedPane1.setTabPlacement(javax.swing.JTabbedPane.LEFT);
        jTabbedPane1.setFont(new java.awt.Font("Ubuntu", 1, 18)); // NOI18N

        tokenTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null}
            },
            new String [] {
                "From ID", "To ID", "Currency", "Amount"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.Integer.class, java.lang.String.class, java.lang.Double.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tokenTable);

        jLabel1.setText("Please confirm the validity of this Transaction");

        userTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "ID", "First Name", "Email", "Balance"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.Double.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(userTable);
        if (userTable.getColumnModel().getColumnCount() > 0) {
            userTable.getColumnModel().getColumn(0).setResizable(false);
            userTable.getColumnModel().getColumn(1).setResizable(false);
            userTable.getColumnModel().getColumn(2).setResizable(false);
            userTable.getColumnModel().getColumn(3).setResizable(false);
        }

        jLabel2.setText("Users' Details");

        confirmBtn.setBackground(java.awt.Color.green);
        confirmBtn.setText("CONFIRM");
        confirmBtn.setEnabled(false);
        confirmBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                confirmBtnActionPerformed(evt);
            }
        });

        declineBtn.setBackground(java.awt.Color.red);
        declineBtn.setText("DECLINE");
        declineBtn.setEnabled(false);
        declineBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                declineBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(26, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(163, 163, 163))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addComponent(jScrollPane2)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(12, 12, 12)
                                .addComponent(jLabel2))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(152, 152, 152)
                                .addComponent(confirmBtn)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(declineBtn)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 169, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(declineBtn)
                    .addComponent(confirmBtn))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("VALIDATE", jPanel3);

        jLabel7.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        jLabel7.setText("Make an announcement to all users.");

        jLabel8.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        jLabel8.setText("Type");

        typeCombo.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        typeCombo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Greetings", "Bug fixes", "Features", "Milestone", "Other" }));

        jLabel9.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        jLabel9.setText("Message");

        TA.setColumns(20);
        TA.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        TA.setLineWrap(true);
        TA.setRows(5);
        TA.setWrapStyleWord(true);
        jScrollPane3.setViewportView(TA);

        jButton3.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        jButton3.setText("POST");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 359, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jButton3)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addComponent(jLabel9)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addComponent(jLabel8)
                                    .addGap(23, 23, 23)))
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(typeCombo, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 379, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(typeCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 152, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton3)
                .addContainerGap())
        );

        jTabbedPane1.addTab("ANNOUNCE", jPanel1);

        contactTbl.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "User ID", "Message"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane4.setViewportView(contactTbl);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 437, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(24, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(27, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("HELP USERS", jPanel4);

        jScrollPane5.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N

        statsTA.setEditable(false);
        statsTA.setColumns(20);
        statsTA.setRows(5);
        jScrollPane5.setViewportView(statsTA);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 465, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(167, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("STATS", jPanel2);

        jButton4.setText("Logout");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton5.setText("DUMMY");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 624, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(24, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton5)
                .addGap(186, 186, 186)
                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(42, 42, 42))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(17, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton4)
                    .addComponent(jButton5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 312, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void declineBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_declineBtnActionPerformed
rejectTransaction(t_id);    
jButton5.doClick();// TODO add your handling code here:
    }//GEN-LAST:event_declineBtnActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
Welcome.a_id = 0;
t_id = 0;
Welcome.u_id = 0;// TODO add your handling code here:
new Welcome().setVisible(true);
this.setVisible(false);
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
DefaultTableModel A = (DefaultTableModel)tokenTable.getModel();
DefaultTableModel B = (DefaultTableModel)userTable.getModel();
DefaultTableModel C = (DefaultTableModel)contactTbl.getModel();
for(int i = A.getRowCount() - 1; i>=0; i--) //Empties the table first
        A.removeRow(i);
for(int i = B.getRowCount() - 1; i>=0; i--) //Empties the table first
        B.removeRow(i);
for(int i = C.getRowCount() - 1; i>=0; i--) //Empties the table first
        C.removeRow(i);
try
    {
        Connection con = startConnection();
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("Select * from token order by creation_time limit 1;");
        if (!rs.next())
            JOptionPane.showMessageDialog(null, "No pending transactions!");
        else 
        {
            t_id = rs.getInt(1);
            int from_id = rs.getInt(2);
            int to_id = rs.getInt(3);
            String currency = rs.getString(4);
            A.addRow(new Object[] {from_id, to_id, currency, rs.getDouble(5)});
            String query = "select u_id, fname, email, balance from (users natural join account) where (u_id = " + from_id + " or u_id = " + to_id + ") and currency = '" + currency +"';";
            rs = stmt.executeQuery(query);
            while(rs.next())
                B.addRow(new Object[] {rs.getInt(1), rs.getString(2), rs.getString(3), rs.getDouble(4)});
            confirmBtn.setEnabled(true);
            declineBtn.setEnabled(true);
        }
        Statement stmt2 = con.createStatement();
        ResultSet rs2 = stmt2.executeQuery("select u_id, message from contacts order by creation_time;");
        while(rs2.next())
            C.addRow(new Object[] {rs2.getInt(1), rs2.getString(2)});
        rs.close(); stmt.close(); rs2.close(); stmt2.close(); con.close();
        
    }
catch(Exception e)
    {
        errorMessage();
    }
statsTA.setText(mostActiveAdmin());
// TODO add your handling code here:
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
String type = (String)typeCombo.getSelectedItem();
String msg = TA.getText();
try
{
    Connection con = startConnection();
    Statement stmt = con.createStatement();
    stmt.executeUpdate("INSERT INTO announcement values (" + Welcome.a_id+ ", '" + type + ": " + msg + "');" );
    stmt.close();
    con.close();
    JOptionPane.showMessageDialog(null, "Announcement posted.");
}
catch(Exception e)
{
    errorMessage();
}

TA.setText("");
// TODO add your handling code here:
    }//GEN-LAST:event_jButton3ActionPerformed

    private void confirmBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_confirmBtnActionPerformed
confirmTransaction(t_id, Welcome.a_id);
jButton5.doClick();// TODO add your handling code here:
    }//GEN-LAST:event_confirmBtnActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
jButton5.setVisible(false);
jButton5.doClick(); 
// TODO add your handling code here:
    }//GEN-LAST:event_formWindowOpened

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
            java.util.logging.Logger.getLogger(AdminFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AdminFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AdminFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AdminFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AdminFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea TA;
    private javax.swing.JButton confirmBtn;
    private javax.swing.JTable contactTbl;
    private javax.swing.JButton declineBtn;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTextArea statsTA;
    private javax.swing.JTable tokenTable;
    private javax.swing.JComboBox<String> typeCombo;
    private javax.swing.JTable userTable;
    // End of variables declaration//GEN-END:variables
}
