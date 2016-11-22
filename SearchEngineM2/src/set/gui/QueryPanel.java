package set.gui;

import com.google.gson.Gson;
import com.homework5.DiskInvertedIndex;

import set.beans.TokenDetails;
import set.beans.JsonFile;
import set.docprocess.BiWordIndexing;
import set.docprocess.PorterStemmer;
import set.docprocess.PositionalInvertedIndex;
import set.queryprocessing.KGramIndex;
import set.queryprocessing.QueryLiterals;
import set.queryprocessing.QueryResultProcessing;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

import javax.swing.AbstractListModel;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;
import javax.swing.text.JTextComponent;
import javax.swing.text.Highlighter.HighlightPainter;

/**
 * @author Durvijay Sharma
 * @author Mangesh Adalinge
 * @author Surabhi Dixit
 */
public class QueryPanel extends javax.swing.JPanel {
    
    private PositionalInvertedIndex pindexobj;
    private BiWordIndexing bindexobj;
    private HashMap<Integer, File> fileList;
    private HashMap<String, File> fetchfile=new HashMap<>();
    private List<String> listOfWords=new ArrayList<>();
    private KGramIndex kIndex=new KGramIndex();
    private String folderPath;
    private Highlighter highlighter;
    DiskInvertedIndex diskInvertedIndex=new DiskInvertedIndex(IndexPanel.currentDirectory.toString(), "P");
    
    
    /**
     * constructor to set indexing object and set the complete filelist
     * @param bIndexing 
     * @param kGramIndex 
     * @param path 
     * @param fileList 
     * @param jTextField 
     * @param abc
     * @param index
     */
    public QueryPanel(String path, HashMap<Integer, File> fileLists) {
        this.folderPath=path;
        this.fileList=fileLists;
        initComponents();        
    }
    
	/**
	 * Query Frame U.I design components ex: buttons,left pane, right pane  etc.
	 * Button action event listeners
	 */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents(){

        jPopupMenu = new javax.swing.JPopupMenu();

        jScrollPane1 = new javax.swing.JScrollPane();
        txtQueryAreaField = new javax.swing.JTextArea();
        btnProcess = new javax.swing.JButton();
        lblOutput = new javax.swing.JLabel();
        txtCountField = new javax.swing.JTextField();
        scrollPaneFileList = new javax.swing.JScrollPane();
        listWindow = new javax.swing.JList<>();
        scrollPaneFileDisplay = new javax.swing.JScrollPane();
        txtFileDisplayArea = new javax.swing.JTextArea();
        
        txtQueryAreaField.setColumns(20);
        txtQueryAreaField.setRows(5);
        txtQueryAreaField.addFocusListener(new java.awt.event.FocusAdapter() {
		    public void focusLost(java.awt.event.FocusEvent evt) {
		        
					try {
						txtQueryAreaFieldFocusLost(evt);
					} catch (BadLocationException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				
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
                try {
					btnProcessActionPerformed(evt);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
        });

        lblOutput.setText("Total Count :");

        txtCountField.setEditable(false);

        listWindow.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION); //single selection of list
        listWindow.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                listWindowMouseClicked(evt);
            }
        });
        scrollPaneFileList.setViewportView(listWindow);

        txtFileDisplayArea.setEditable(false);
        txtFileDisplayArea.setColumns(20);
        txtFileDisplayArea.setLineWrap(true);
        txtFileDisplayArea.setRows(5);
        scrollPaneFileDisplay.setViewportView(txtFileDisplayArea);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblOutput)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtCountField, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(scrollPaneFileList, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 337, Short.MAX_VALUE)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING))
                        .addGap(28, 28, 28)
                        .addComponent(btnProcess)))
                .addGap(34, 34, 34)
                .addComponent(scrollPaneFileDisplay, javax.swing.GroupLayout.PREFERRED_SIZE, 581, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(20, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(55, 55, 55)
                        .addComponent(btnProcess)
                        .addGap(107, 107, 107)
                        .addComponent(scrollPaneFileList, javax.swing.GroupLayout.PREFERRED_SIZE, 332, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(scrollPaneFileDisplay, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(lblOutput, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtCountField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                .addContainerGap(26, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents
    
    /**
     * on click of Process button following action occurs
     * query is passed to QueryLiteral class
     * Result is retrieved and displayed
     * resetting of total count of files received and list
     * @param evt
     * @throws IOException 
     */
    @SuppressWarnings("serial")
	private void btnProcessActionPerformed(java.awt.event.ActionEvent evt) throws IOException {//GEN-FIRST:event_btnProcessActionPerformed
        txtCountField.setText(Integer.toString(0));
        displayResult=new ArrayList<>();
        int correctionYesNo=JOptionPane.YES_NO_OPTION,userSelectionkey=-1;
    	if (txtQueryAreaField.getText().trim().equals("")) {	
            JOptionPane.showMessageDialog(null, "Enter Query to Process");
        }
    	else{
    		if (highlighter.getHighlights().length>0) {
    			System.out.println("which is first"+ highlighter.getHighlights().length+"--");
				userSelectionkey=JOptionPane.showConfirmDialog(null, "There may be apelling mistake in your query. Would you like to proceed without correcting","warning",correctionYesNo);
			}
    		if(userSelectionkey==0){
    		
            QueryLiterals qL=new QueryLiterals();
            displayResult=qL.splitQueryString(txtQueryAreaField.getText().trim(),folderPath);
            if (displayResult!=null && displayResult.size()>0) {
                txtCountField.setText(Integer.toString(displayResult.size()));
                listWindow = new javax.swing.JList<>();                
                listWindow.setModel(new javax.swing.AbstractListModel<String>() {// result is added to display list
                    public int getSize() {
                        return displayResult.size(); }
                    public String getElementAt(int i) {
                        TokenDetails result = displayResult.get(i);
                        fetchfile.put(fileList.get(result.getDocId()).getName(),fileList.get(result.getDocId()));// recieved result file list is created
 //                       System.out.println("fetch file"+fetchfile.size());
                        return fileList.get(result.getDocId()).getName(); //file id is decoded with file id
                    }
                });
                listWindow.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
                // event listener for select file in the result
                listWindow.addMouseListener(new java.awt.event.MouseAdapter() {
                    public void mouseClicked(java.awt.event.MouseEvent evt) {
                        listWindowMouseClicked(evt);
                    }
                    
                });
                scrollPaneFileList.setViewportView(listWindow);
            }else{
            	// resetting of display result
            	JList<String> jlist = new JList<String>();
                AbstractListModel<String> abstractListModel = (AbstractListModel<String>)jlist.getModel();
                listWindow.setModel(abstractListModel);
            	JOptionPane.showMessageDialog(null, "No records Found");
            }
            
        }
    	}
    }//GEN-LAST:event_btnProcessActionPerformed
    /**
     * the selected file is decoded and its complete path is retrieved and passed on to Filereader to read
     * and the body of the selected file is displayed of on the file display text area
     * @param evt
     */
    private void listWindowMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_listWindowMouseClicked
        
        String Selectedfile=listWindow.getSelectedValue();
        if (Selectedfile!=null) {
            try {
                Gson gson = new Gson();
                File myFile = fetchfile.get(Selectedfile);
                FileReader fr = new FileReader(myFile.getAbsoluteFile());
                JsonFile fileclass = gson.fromJson(fr, JsonFile.class);
                
                
                txtFileDisplayArea.setText(fileclass.getBody());
            } catch (IOException ex) {
            	Logger.getLogger(MainJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null,ex);
                ex.printStackTrace();
            }
        }
        
        
    }//GEN-LAST:event_listWindowMouseClicked
    
	private boolean temp=false;
    private void txtQueryAreaFieldFocusLost(java.awt.event.FocusEvent evt) throws BadLocationException {//GEN-FIRST:event_txtQueryAreaFieldFocusLost
    	markerSetAndRemove();
    }//GEN-LAST:event_txtQueryAreaFieldFocusLost

    private void markerSetAndRemove() throws BadLocationException{
    	highlighter = txtQueryAreaField.getHighlighter();
        HighlightPainter painter =  new DefaultHighlighter.DefaultHighlightPainter(Color.blue);
        highlighter.removeAllHighlights();
        temp=true;
        System.out.println("SET.GUI.QueryPanel.txtQueryAreaFieldFocusLost()");
        String queryString=txtQueryAreaField.getText().trim();
        String[] splitQueryString=queryString.split(" ");
        for (String string : splitQueryString) {
			String temp=PorterStemmer.processToken(PorterStemmer.processWord(string)).trim();
			if (temp!=null && temp!="" && !temp.contains("*")) {
				if((diskInvertedIndex.binarySearchVocabulary(temp))<0){
					//kIndex.getSpellingCorrectionKGrams(temp);
					int completeQuery = txtQueryAreaField.getText().indexOf(string);
					int selectedWord = completeQuery + string.length();
					highlighter.addHighlight(completeQuery, selectedWord, painter );
				}
			}
		}
    }

	private void txtQueryAreaFieldMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtQueryAreaFieldMouseReleased
        System.out.println("SET.GUI.QueryPanel.txtQueryAreaFieldMouseReleased()");
        JMenuItem temp;
        
        if (evt.isPopupTrigger()) {
            JTextComponent myComponent = (JTextComponent) evt.getComponent();
            
            ActionListener menuListener=new ActionListener(){
                public void actionPerformed(ActionEvent event){
                	txtQueryAreaField.setText(txtQueryAreaField.getText().replace(myComponent.getSelectedText(), event.getActionCommand().trim()));
                    System.out.println(event.getActionCommand()+".actionPerformed()");
                }
            };
            jPopupMenu=new JPopupMenu();
             if (myComponent.getSelectedText() != null) {
            	 kIndex.getSpellingCorrectionKGrams(myComponent.getSelectedText().trim());
            	 System.out.println("topDictionarySuggestions "+KGramIndex.topDictionarySuggestions.size());
            	 List<String> popupValues=new ArrayList<>();
            	 System.out.println("Collections.min(KGramIndex.topDictionarySuggestions.keySet() "+Collections.min(KGramIndex.topDictionarySuggestions.keySet()));
            	 popupValues=(List<String>) KGramIndex.topDictionarySuggestions.get(Collections.min(KGramIndex.topDictionarySuggestions.keySet()));
            	 for (int i = 0; i<popupValues.size(); i++) {
                     temp=new JMenuItem();
                     temp.setText(popupValues.get(i));
                     jPopupMenu.add(temp);
                     temp.addActionListener(menuListener);
                     if (i==5) {
						break;
					}
                 }
            // JPopupMenu theMenu = myComponent.getComponentPopupMenu();
             jPopupMenu.show(this,evt.getX(),evt.getY());
         }
            
        }
    }//GEN-LAST:event_txtQueryAreaFieldMouseReleased

    private javax.swing.JPopupMenu jPopupMenu;
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnProcess;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea txtFileDisplayArea;
    private javax.swing.JLabel lblOutput;
    private javax.swing.JList<String> listWindow;
    private javax.swing.JScrollPane scrollPaneFileDisplay;
    private javax.swing.JScrollPane scrollPaneFileList;
    private javax.swing.JTextField txtCountField;
    private javax.swing.JTextArea txtQueryAreaField;
    // End of variables declaration//GEN-END:variables
    private List<TokenDetails> displayResult=new ArrayList<>();
}
