/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package set.gui;

import set.beans.JsonFile;
import set.beans.RankComparator;
import set.beans.TokenDetails;
import set.docprocess.PorterStemmer;
import set.queryprocessing.KGramIndex;
import set.queryprocessing.QueryLiterals;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.logging.Logger;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;
import javax.swing.text.JTextComponent;
import javax.swing.text.Highlighter.HighlightPainter;
import com.google.gson.Gson;

/**
 * @author Durvijay Sharma
 * @author Mangesh Adalinge
 * @author Surabhi Dixit
 */
public class QueryPanel extends javax.swing.JPanel {

	public QueryPanel(String path, boolean queryWindow) {
		this.queryWindowSelection = queryWindow;
		initComponents();
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	@SuppressWarnings({ "serial" })
	// <editor-fold defaultstate="collapsed" desc="Generated
	// Code">//GEN-BEGIN:initComponents
	private void initComponents() {
		jPopupMenu = new javax.swing.JPopupMenu();
		jScrollPane1 = new javax.swing.JScrollPane();
		txtQueryAreaField = new javax.swing.JTextArea();
		btnProcess = new javax.swing.JButton();
		lblOutput = new javax.swing.JLabel();
		txtCountField = new javax.swing.JTextField();
		scrollPaneFileDisplay = new javax.swing.JScrollPane();
		txtFileDisplayArea = new javax.swing.JTextArea();
		jScrollPane2 = new javax.swing.JScrollPane();
		jTable1 = new javax.swing.JTable();

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
		txtFileDisplayArea.setEditable(false);
		txtFileDisplayArea.setColumns(20);
		txtFileDisplayArea.setLineWrap(true);
		txtFileDisplayArea.setRows(5);
		scrollPaneFileDisplay.setViewportView(txtFileDisplayArea);
		if (this.queryWindowSelection) {
			jTable1.setModel(new javax.swing.table.DefaultTableModel(
					new Object[][] { { null }, { null }, { null }, { null }, { null }, { null }, { null }, { null },
							{ null }, { null }, { null }, { null }, { null }, { null }, { null }, { null }, { null },
							{ null }, { null }, { null }, { null }, { null }, { null } },
					new String[] { "Document Name" }) {
				Class[] types = new Class[] { java.lang.String.class };
				boolean[] canEdit = new boolean[] { false };

				public Class getColumnClass(int columnIndex) {
					return types[columnIndex];
				}

				public boolean isCellEditable(int rowIndex, int columnIndex) {
					return canEdit[columnIndex];
				}
			});
		} else {
			jTable1.setModel(new javax.swing.table.DefaultTableModel(
					new Object[][] { { null, null }, { null, null }, { null, null }, { null, null }, { null, null },
							{ null, null }, { null, null }, { null, null }, { null, null }, { null, null },
							{ null, null }, { null, null }, { null, null }, { null, null }, { null, null },
							{ null, null }, { null, null }, { null, null }, { null, null }, { null, null },
							{ null, null }, { null, null }, { null, null } },
					new String[] { "Document Name", "Score" }) {
				Class[] types = new Class[] { java.lang.String.class, java.lang.String.class };
				boolean[] canEdit = new boolean[] { false, false };

				public Class getColumnClass(int columnIndex) {
					return types[columnIndex];
				}

				public boolean isCellEditable(int rowIndex, int columnIndex) {
					return canEdit[columnIndex];
				}
			});
		}

		jTable1.setColumnSelectionAllowed(true);
		jTable1.getTableHeader().setReorderingAllowed(false);
		jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				jTable1MouseClicked(evt);
			}
		});
		jScrollPane2.setViewportView(jTable1);
		jTable1.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
		this.setLayout(layout);
		layout.setHorizontalGroup(
				layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(layout
						.createSequentialGroup().addGroup(layout
								.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(layout
										.createSequentialGroup().addGap(31, 31, 31).addGroup(
												layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
														.addGroup(layout.createSequentialGroup()
																.addComponent(lblOutput)
																.addPreferredGap(
																		javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
																.addComponent(txtCountField,
																		javax.swing.GroupLayout.PREFERRED_SIZE, 74,
																		javax.swing.GroupLayout.PREFERRED_SIZE))
														.addGroup(layout.createSequentialGroup()
																.addComponent(jScrollPane1,
																		javax.swing.GroupLayout.PREFERRED_SIZE, 337,
																		javax.swing.GroupLayout.PREFERRED_SIZE)
																.addGap(28, 28, 28).addComponent(btnProcess)))
										.addGap(34, 34, 34))
								.addGroup(javax.swing.GroupLayout.Alignment.TRAILING,
										layout.createSequentialGroup().addContainerGap()
												.addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														javax.swing.GroupLayout.PREFERRED_SIZE)
												.addGap(18, 18, 18)))
						.addComponent(scrollPaneFileDisplay, javax.swing.GroupLayout.PREFERRED_SIZE, 581,
								javax.swing.GroupLayout.PREFERRED_SIZE)
						.addContainerGap(20, Short.MAX_VALUE)));
		layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(layout
				.createSequentialGroup().addGap(21, 21, 21)
				.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addComponent(scrollPaneFileDisplay, javax.swing.GroupLayout.PREFERRED_SIZE, 496,
								javax.swing.GroupLayout.PREFERRED_SIZE)
						.addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
								.addGroup(layout.createSequentialGroup().addGap(34, 34, 34).addComponent(btnProcess))
								.addGroup(layout.createSequentialGroup()
										.addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addGap(18, 18, 18)
										.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
												.addComponent(lblOutput, javax.swing.GroupLayout.PREFERRED_SIZE, 23,
														javax.swing.GroupLayout.PREFERRED_SIZE)
												.addComponent(txtCountField, javax.swing.GroupLayout.PREFERRED_SIZE,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														javax.swing.GroupLayout.PREFERRED_SIZE))))
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(
										jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
				.addContainerGap(85, Short.MAX_VALUE)));
	}// </editor-fold>//GEN-END:initComponents

	private void btnProcessActionPerformed(java.awt.event.ActionEvent evt) throws IOException {// GEN-FIRST:event_btnProcessActionPerformed
		txtCountField.setText(Integer.toString(0));
		displayRankResult = new PriorityQueue<>();
		displayBooleanResult = new ArrayList<>();
		int correctionYesNo = JOptionPane.YES_NO_OPTION, userSelectionkey = -1;
		DefaultTableModel dtm = new DefaultTableModel();
		// listWindow = new javax.swing.JList<>();
		if (txtQueryAreaField.getText().trim().equals("")) {
			JOptionPane.showMessageDialog(null, "Enter Query to Process");
		} else {
			if (highlighter.getHighlights().length > 0) {
				userSelectionkey = JOptionPane.showConfirmDialog(null,
						"There may be a spelling mistake in your query. Would you like to proceed without correcting",
						"warning", correctionYesNo);
			}
			if (userSelectionkey == 0 || highlighter.getHighlights().length < 1) {

				QueryLiterals qL = new QueryLiterals();
				dtm = (DefaultTableModel) jTable1.getModel();
				dtm.getDataVector().removeAllElements();
				if (queryWindowSelection) {
					if (!txtQueryAreaField.getText().trim().contains(" ")
							&& txtQueryAreaField.getText().trim().startsWith("-")) {
						JOptionPane.showMessageDialog(null, "Single not operation is not allowed");
						return;
					}
					displayBooleanResult = qL.splitQueryString(txtQueryAreaField.getText().trim());
					if (null != displayBooleanResult && displayBooleanResult.size() > 0) {
						for (int i = 0; i < displayBooleanResult.size(); i++) {
							if (queryWindowSelection) {

								displayBooleanResult.get(i).getDocId();
							}
							dtm.insertRow(i, new Object[] {
									IndexPanel.fileNameLists.get(displayBooleanResult.get(i).getDocId()).getName() });
						}
						txtCountField.setText(Integer.toString(displayBooleanResult.size()));
						// add header of the table
						String header[] = new String[] { "Document Id" };
						// add header in table model
						dtm.setColumnIdentifiers(header);
					} else {
						// resetting of display result
						dtm.getDataVector().removeAllElements();
						jTable1.setModel(new DefaultTableModel());
						txtCountField.setText("0");
						txtFileDisplayArea.setText("");
						JOptionPane.showMessageDialog(null, "No records Found");
					}
				} else {
					displayRankResult = qL.rankQueryString(txtQueryAreaField.getText().trim());
					if (null != displayRankResult && displayRankResult.size() > 0) {
						txtCountField.setText(Integer.toString(displayRankResult.size()));
						int i = 0;
						while (displayRankResult.size() > 0) {
							RankComparator rCom = displayRankResult.poll();
							dtm.insertRow(i, new Object[] { IndexPanel.fileNameLists.get(rCom.getDocId()).getName(),
									rCom.getScore().toString() });
							i++;
							if (i == 10) {
								break;
							}
						}
						// add header of the table
						String header[] = new String[] { "Document Id", "Score" };
						// add header in table model
						dtm.setColumnIdentifiers(header);
					} else {
						// resetting of display result
						dtm.getDataVector().removeAllElements();
						jTable1.setModel(new DefaultTableModel());
						txtCountField.setText("0");
						txtFileDisplayArea.setText("");
						JOptionPane.showMessageDialog(null, "No records Found");
					}
				}
				// set model into the table object
				jTable1.setModel(dtm);
				jTable1.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
				jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
					public void mouseClicked(java.awt.event.MouseEvent evt) {
						jTable1MouseClicked(evt);
					}
				});
			}
		}
	}// GEN-LAST:event_btnProcessActionPerformed

	/**
	 * Opens the selected file's content
	 * 
	 * @param evt
	 */
	private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {
		try {
			JTable table = (JTable) evt.getSource();
			if (table.getSelectedColumn() == 0) {
				Object Selectedfile = table.getValueAt(table.getSelectedRow(), table.getSelectedColumn());
				if (null != Selectedfile) {
					Gson gson = new Gson();
					FileReader fr = new FileReader(
							IndexPanel.currentDirectory.toString() + "/" + Selectedfile.toString());
					JsonFile fileclass = gson.fromJson(fr, JsonFile.class);
					txtFileDisplayArea.setText(fileclass.getBody());
				}
			}
		} catch (IOException ex) {
			Logger.getLogger(QueryPanel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
			ex.printStackTrace();
		}
	}

	/*
	 * Checks the focus of query input
	 */
	private void txtQueryAreaFieldFocusLost(java.awt.event.FocusEvent evt) throws BadLocationException {// GEN-FIRST:event_txtQueryAreaFieldFocusLost
		markerSetAndRemove();
	}// GEN-LAST:event_txtQueryAreaFieldFocusLost

	/**
	 * Sets the marker for the misspelled words
	 * 
	 * @throws BadLocationException
	 */
	private void markerSetAndRemove() throws BadLocationException {
		highlighter = txtQueryAreaField.getHighlighter();
		HighlightPainter painter = new DefaultHighlighter.DefaultHighlightPainter(Color.orange);
		highlighter.removeAllHighlights();
		String queryString = txtQueryAreaField.getText().trim();
		String[] splitQueryString = queryString.split(" ");
		for (String string : splitQueryString) {
			wrongTerm = PorterStemmer.processWordAndStem(string).trim();
			if (wrongTerm != "" && !wrongTerm.contains("*")) {
				if (mispelledWords.contains(wrongTerm) || (IndexPanel.dIndexP.binarySearchVocabulary(wrongTerm)) < 0) {
					mispelledWords.add(string.trim());
					// kIndex.getSpellingCorrectionKGrams(temp);
					int completeQuery = txtQueryAreaField.getText().indexOf(string);
					int selectedWord = completeQuery + string.length();
					highlighter.addHighlight(completeQuery, selectedWord, painter);

				}
			}
		}
	}

	/**
	 * Opens the suggestion's pop up for misspelled words
	 * 
	 * @param evt
	 */
	private void txtQueryAreaFieldMouseReleased(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_txtQueryAreaFieldMouseReleased
		JMenuItem temp;
		if (evt.isPopupTrigger()) {
			JTextComponent myComponent = (JTextComponent) evt.getComponent();
			ActionListener menuListener = new ActionListener() {
				public void actionPerformed(ActionEvent event) {
					txtQueryAreaField.setText(txtQueryAreaField.getText().replace(myComponent.getSelectedText(),
							event.getActionCommand().trim()));
				}
			};
			jPopupMenu = new JPopupMenu();

			if (myComponent.getSelectedText() != null && mispelledWords.contains(myComponent.getSelectedText())) {
				kIndex.getSpellingCorrectionKGrams(myComponent.getSelectedText().trim());
				System.out.println("topDictionarySuggestions " + KGramIndex.topDictionarySuggestions.size());
				List<String> popupValues = new ArrayList<>();
				// System.out.println("Collections.min(KGramIndex.topDictionarySuggestions.keySet()
				// "+Collections.min(KGramIndex.topDictionarySuggestions.keySet()));
				System.out.println("wait");
				popupValues = (List<String>) KGramIndex.topDictionarySuggestions
						.get(Collections.min(KGramIndex.topDictionarySuggestions.keySet()));
				for (int i = 0; i < popupValues.size(); i++) {
					temp = new JMenuItem();
					temp.setText(popupValues.get(i).replace("-", ""));
					jPopupMenu.add(temp);
					temp.addActionListener(menuListener);

				}
				// JPopupMenu theMenu = myComponent.getComponentPopupMenu();
				jPopupMenu.show(this, evt.getX(), evt.getY());
			}

		}
	}// GEN-LAST:event_txtQueryAreaFieldMouseReleased

	// Variables declaration - do not modify//GEN-BEGIN:variables
	private javax.swing.JButton btnProcess;
	private javax.swing.JPopupMenu jPopupMenu;
	private javax.swing.JScrollPane jScrollPane1;
	private javax.swing.JScrollPane jScrollPane2;
	private javax.swing.JTable jTable1;
	private javax.swing.JTextArea txtFileDisplayArea;
	private javax.swing.JLabel lblOutput;
	private javax.swing.JScrollPane scrollPaneFileDisplay;
	private javax.swing.JTextField txtCountField;
	private javax.swing.JTextArea txtQueryAreaField;
	// End of variables declaration//GEN-END:variables
	private PriorityQueue<RankComparator> displayRankResult = new PriorityQueue<>();
	private List<TokenDetails> displayBooleanResult = new ArrayList<>();
	private String wrongTerm = "";
	private HashSet<String> mispelledWords = new HashSet<>();
	private boolean queryWindowSelection;
	private Highlighter highlighter;
	private KGramIndex kIndex = new KGramIndex();

}
