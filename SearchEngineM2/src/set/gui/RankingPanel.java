/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package set.gui;

import set.beans.TokenDetails;
import set.docprocess.Indexing;
import set.queryprocessing.QueryLiterals;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.text.JTextComponent;

/**
 *
 * @author DURVIJAY
 */
public class RankingPanel extends javax.swing.JPanel {
    
    /**
     * Creates new form QueryPanel
     */
    private Indexing indexobj;
    private Indexing biIndexobj;
    private HashMap<Integer, File> fileList;
    private HashMap<String, File> fetchfile=new HashMap<>();
    private List<String> listOfWords=new ArrayList<>();
    
    public RankingPanel(String path, HashMap<Integer, File> fileLists) {
        //indexobj=biWordIndex;
  //      System.out.println("File list size :"+abc.size() + " -"+biWordIndex.getmIndex().size());

        fileList=fileLists;
        initComponents();        
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings({ "unchecked", "serial" })
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPopupMenu1 = new javax.swing.JPopupMenu();
        edit = new javax.swing.JMenuItem();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtQueryAreaField = new javax.swing.JTextArea();
        btnProcess = new javax.swing.JButton();
        lblOutput = new javax.swing.JLabel();
        txtCountField = new javax.swing.JTextField();
        scrollPaneFileDisplay = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

        edit.setText("editing");
        edit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editActionPerformed(evt);
            }
        });
        jPopupMenu1.add(edit);

        txtQueryAreaField.setColumns(20);
        txtQueryAreaField.setRows(5);
        txtQueryAreaField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtQueryAreaFieldFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtQueryAreaFieldFocusLost(evt);
            }
        });
        txtQueryAreaField.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                txtQueryAreaFieldMouseReleased(evt);
            }
        });
        jScrollPane1.setViewportView(txtQueryAreaField);

        btnProcess.setText("Process");
        btnProcess.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnProcessActionPerformed(evt);
            }
        });

        lblOutput.setText("Total Count :");

        txtCountField.setEditable(false);
        txtCountField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCountFieldActionPerformed(evt);
            }
        });

        jTextArea1.setEditable(false);
        jTextArea1.setColumns(20);
        jTextArea1.setLineWrap(true);
        jTextArea1.setRows(5);
        scrollPaneFileDisplay.setViewportView(jTextArea1);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Document Name", "Score"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class
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
        jTable1.setColumnSelectionAllowed(true);
        jTable1.getTableHeader().setReorderingAllowed(false);
        jScrollPane2.setViewportView(jTable1);
        jTable1.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblOutput)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtCountField, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 337, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(28, 28, 28)
                                .addComponent(btnProcess)))
                        .addGap(34, 34, 34))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)))
                .addComponent(scrollPaneFileDisplay, javax.swing.GroupLayout.PREFERRED_SIZE, 581, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(20, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(scrollPaneFileDisplay, javax.swing.GroupLayout.PREFERRED_SIZE, 496, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(34, 34, 34)
                                .addComponent(btnProcess))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(lblOutput, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtCountField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents
    
    private void txtCountFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCountFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCountFieldActionPerformed
    
    private void btnProcessActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProcessActionPerformed
        txtCountField.setText(Integer.toString(0));
       // listWindow = new javax.swing.JList<>(); 
    	if (txtQueryAreaField.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Enter Query to Process");
        }else{
            if (temp==true) {
                return;
            }
            System.out.println("11112222SET.GUI.QueryPanel.btnProcessActionPerformed()");
            
            
            
            QueryLiterals qL=new QueryLiterals();
     //       displayResult=qL.splitQueryString(txtQueryAreaField.getText().trim(),biIndexobj);
            if (displayResult!=null && displayResult.size()>0) {
                txtCountField.setText(Integer.toString(displayResult.size()));
//                listWindow = new javax.swing.JList<>();                
//                listWindow.setModel(new javax.swing.AbstractListModel<String>() {
//                    public int getSize() {
//                        return displayResult.size(); }
//                    public String getElementAt(int i) {
//                        DocumentList result = displayResult.get(i);
//                        fetchfile.put(fileList.get(result.getDocId()).getName(),fileList.get(result.getDocId()));
//                        return fileList.get(result.getDocId()).getName(); }
//                });
//                listWindow.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
//                listWindow.addMouseListener(new java.awt.event.MouseAdapter() {
//                    public void mouseClicked(java.awt.event.MouseEvent evt) {
//                        listWindowMouseClicked(evt);
//                    }
//                    
//                });
//                scrollPaneFileList.setViewportView(listWindow);
            }
            
        }
    }//GEN-LAST:event_btnProcessActionPerformed
        
    private boolean temp=false;
    private void txtQueryAreaFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtQueryAreaFieldFocusLost
        
        temp=true;
        System.out.println("SET.GUI.QueryPanel.txtQueryAreaFieldFocusLost()");
    }//GEN-LAST:event_txtQueryAreaFieldFocusLost

    private void txtQueryAreaFieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtQueryAreaFieldFocusGained
        System.out.println("SET.GUI.QueryPanel.txtQueryAreaFieldFocusGained()");
    }//GEN-LAST:event_txtQueryAreaFieldFocusGained

    private void txtQueryAreaFieldMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtQueryAreaFieldMouseReleased
        System.out.println("SET.GUI.QueryPanel.txtQueryAreaFieldMouseReleased()");
        JMenuItem temp;
        ActionListener menuListener=new ActionListener(){
            public void actionPerformed(ActionEvent event){
                System.out.println(event.getActionCommand()+".actionPerformed()");
            }
        };
        if (evt.isPopupTrigger()) {
            JTextComponent myComponent = (JTextComponent) evt.getComponent();
            jPopupMenu1=new JPopupMenu();
             if (myComponent.getSelectedText() != null) {
                 for (String listOfWord : listOfWords) {
                     temp=new JMenuItem();
                     temp.setText(listOfWord);
                     jPopupMenu1.add(temp);
                     temp.addActionListener(menuListener);
                 }
            // JPopupMenu theMenu = myComponent.getComponentPopupMenu();
             jPopupMenu1.show(this,evt.getX(),evt.getY());
         }
            
        }
    }//GEN-LAST:event_txtQueryAreaFieldMouseReleased

    private void editActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editActionPerformed
        System.out.println("SET.GUI.QueryPanel.editActionPerformed()");
    }//GEN-LAST:event_editActionPerformed
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnProcess;
    private javax.swing.JMenuItem edit;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JLabel lblOutput;
    private javax.swing.JScrollPane scrollPaneFileDisplay;
    private javax.swing.JTextField txtCountField;
    private javax.swing.JTextArea txtQueryAreaField;
    // End of variables declaration//GEN-END:variables
    private List<TokenDetails> displayResult=new ArrayList<>();
}
