
package set.gui;

import com.google.gson.Gson;
import com.homework5.DiskInvertedIndex;
import com.homework5.IndexWriter;

import set.beans.JsonFile;
import set.docprocess.BiWordIndexing;
import set.docprocess.PorterStemmer;
import set.docprocess.PositionalInvertedIndex;
import set.docprocess.SimpleTokenStream;
import set.queryprocessing.KGramIndex;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 * @author Durvijay Sharma
 * @author Mangesh Adalinge
 * @author Surabhi Dixit
 */
public class IndexPanel extends javax.swing.JPanel {

	public static Path currentDirectory;
	private HashMap<Integer, File> fileNameLists = new HashMap<>();
	private PositionalInvertedIndex pInvertedInd;
	private BiWordIndexing bIndex;
	private JTextField txtTotalFiles;
	private JTextField txtTotalTime;
	// private PorterStemmer pStemmer=new PorterStemmer();
	private KGramIndex kIndex = new KGramIndex();
	private IndexWriter indexWriter;
	private List<String> tokenList = new ArrayList<>();
	private String[] diskName = { "PdocWeights.bin", "Ppostings.bin", "Pvocab.bin", "PvocabTable.bin",
			 "Bpostings.bin", "Bvocab.bin", "BvocabTable.bin","FileList","KGram" };
	private static SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
	public static boolean diskExsits=false;

	public PositionalInvertedIndex getpInvertedIndex() {
		return pInvertedInd;
	}

	public KGramIndex getkIndex() {
		return kIndex;
	}

	public void setkIndex(KGramIndex kIndex) {
		this.kIndex = kIndex;
	}

	public void setpInvertedIndex(PositionalInvertedIndex pInvertedIndex) {
		this.pInvertedInd = pInvertedIndex;
	}

	public BiWordIndexing getbIndexing() {
		return bIndex;
	}

	public void setbIndexing(BiWordIndexing bIndexing) {
		this.bIndex = bIndexing;
	}

	/**
	 * constructor to set indexlist and pass the totalfile list and total time
	 * 
	 * @param btnLabel
	 * @param txtTotalFiles
	 * @param txtTotalTime
	 * @param bIndexing
	 * @param pInvertedIndex
	 */
	public IndexPanel(String btnLabel, JTextField txtTotalFiles, JTextField txtTotalTime,
			PositionalInvertedIndex pInvertedIndex, BiWordIndexing bIndexing) {
		initComponents();
		btnIndex.setText(btnLabel);
		this.txtTotalFiles = txtTotalFiles;
		this.txtTotalTime = txtTotalTime;
		this.pInvertedInd = pInvertedIndex;
		this.bIndex = bIndexing;
	}

	// getter for file name list
	public HashMap<Integer, File> getFileNameLists() {
		return fileNameLists;
	}

	// setter for file name list
	public void setFileNameLists(HashMap<Integer, File> fileNameLists) {
		this.fileNameLists = fileNameLists;
	}

	// Code">//GEN-BEGIN:initComponents
	/**
	 * Index Frame U.I design components ex: buttons,left pane, right pane etc.
	 * Button action event listeners
	 */
	private void initComponents() {

		btnBrowse = new javax.swing.JButton();
		btnIndex = new javax.swing.JButton();
		txtFolderSelect = new javax.swing.JTextField();

		btnBrowse.setText("Browse");
		btnBrowse.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnBrowseActionPerformed(evt);
			}
		});

		btnIndex.setText("Index");
		btnIndex.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				try {
					btnIndexActionPerformed(evt);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

		txtFolderSelect.setEditable(false);

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
		this.setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup().addContainerGap()
						.addComponent(txtFolderSelect, javax.swing.GroupLayout.PREFERRED_SIZE, 359,
								javax.swing.GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(btnBrowse, javax.swing.GroupLayout.PREFERRED_SIZE, 90,
								javax.swing.GroupLayout.PREFERRED_SIZE)
						.addGap(28, 28, 28).addComponent(btnIndex, javax.swing.GroupLayout.PREFERRED_SIZE, 92,
								javax.swing.GroupLayout.PREFERRED_SIZE)
						.addContainerGap(149, Short.MAX_VALUE)));
		layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(layout
				.createSequentialGroup().addContainerGap()
				.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
						.addComponent(btnIndex, javax.swing.GroupLayout.Alignment.TRAILING,
								javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
								Short.MAX_VALUE)
						.addComponent(txtFolderSelect, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
						.addComponent(btnBrowse, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE))
				.addContainerGap(22, Short.MAX_VALUE)));
	}// </editor-fold>//GEN-END:initComponents

	private void btnBrowseActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnBrowseActionPerformed
		JFileChooser fileChooser = new JFileChooser();

		// For Directory
		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

		// For File
		// fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

		fileChooser.setAcceptAllFileFilterUsed(false);
		int rVal = fileChooser.showOpenDialog(null);
		if (rVal == JFileChooser.APPROVE_OPTION) {
			txtFolderSelect.setText(fileChooser.getSelectedFile().toString());
		} else {
			System.out.println(".actionPerformed()");
		}
	}// GEN-LAST:event_btnBrowseActionPerformed

	/**
	 * start indexing button event listener also used to reset and set total
	 * file list and total time required for indexing
	 * 
	 * @param evt
	 * @throws NullPointerException,Exception 
	 */
	private void btnIndexActionPerformed(java.awt.event.ActionEvent evt) throws NullPointerException,Exception {// GEN-FIRST:event_btnIndexActionPerformed
		try {

			if (!txtFolderSelect.getText().trim().equalsIgnoreCase("")) {
				currentDirectory = Paths.get(txtFolderSelect.getText());
				int dialogueDiskReadbox = -1, dialogueDiskCreatebox = -1,
						dialogueDiskReadButton = JOptionPane.YES_NO_OPTION;
				String date = "";
				for (int i = 0; i < diskName.length; i++) {
					//System.out.println(txtFolderSelect.getText().trim() + "/" + diskName[i]);
					File file = new File(txtFolderSelect.getText().trim() + "/" + diskName[i]);
					if (file.exists()) {
						diskExsits=true;
						date = dateFormat.format(file.lastModified());
					} else {
						diskExsits=false;
						break;						
					}
				}
				if (!diskExsits) {
					dialogueDiskReadbox = JOptionPane.showConfirmDialog(null,
							"Disk File Not present, You might Consider indexing", "Warning",
							dialogueDiskReadButton);
				}
				if (dialogueDiskReadbox != 0) {
					diskExsits=true;
					dialogueDiskCreatebox = JOptionPane.showConfirmDialog(null,
							"The Disk was last modified " + date + ". Do you want to Re-Index", "Warning",
							dialogueDiskReadButton);

				}
				if (dialogueDiskReadbox == 0 || dialogueDiskCreatebox == 0) {
					

					txtTotalFiles.setText("");
					txtTotalTime.setText("");
					indexWriter = new IndexWriter(txtFolderSelect.getText().trim());
					Gson gson = new Gson();
					long startTime = System.nanoTime();
					fileNameLists = new HashMap<>();
					
					pInvertedInd = new PositionalInvertedIndex();
					bIndex = new BiWordIndexing();
					System.out.println(pInvertedInd.getTermCount() + "before size");
					Files.walkFileTree(currentDirectory, new SimpleFileVisitor<Path>() {
						int l = 0, k = 0, z = 0;

						public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
							// make sure we only process the current working

							if (currentDirectory != null) {
								return FileVisitResult.CONTINUE;
							}
							return FileVisitResult.SKIP_SUBTREE;
						}

						public FileVisitResult visitFile(Path file, BasicFileAttributes attrs)
								throws FileNotFoundException {
							// only process .json files
							if (file.toString().endsWith(".json")) {
								fileNameLists.put(l, file.toFile());
								JsonFile fileclass = gson.fromJson(new FileReader(file.toFile().getAbsolutePath()),
										JsonFile.class);
								if (z == k) {
									System.out.println("completed file: " + k);
									z += 5000;
								}
								retrieveToken(fileclass.getBody(), pInvertedInd, bIndex, l);

								l++;
								k++;
							}
							return FileVisitResult.CONTINUE;
						}

						// don't throw exceptions if files are locked/other
						// errors
						// occur
						public FileVisitResult visitFileFailed(Path file, IOException e) {

							return FileVisitResult.CONTINUE;
						}
					});
					long endTime = System.nanoTime();
					long totalTime = endTime - startTime;
					System.out.println("Total Indexing Time" + TimeUnit.NANOSECONDS.toMinutes(totalTime));
					String[] pDictionary = pInvertedInd.getDictionary();
					String[] bDictionary = bIndex.getDictionary();
					// an array of positions in the vocabulary file
					long[] pVocabPositions = new long[pDictionary.length];
					long[] bVocabPositions = new long[bDictionary.length];

					indexWriter.buildVocabFile(currentDirectory.toString(), pDictionary, pVocabPositions, "P");
					indexWriter.buildPostingsFile(currentDirectory.toString(), pInvertedInd, pDictionary,
							pVocabPositions, bIndex, "P");
					
					indexWriter.buildVocabFile(currentDirectory.toString(), bDictionary, bVocabPositions, "B");
					indexWriter.buildPostingsFile(currentDirectory.toString(), pInvertedInd, bDictionary,bVocabPositions, bIndex, "B");
					IndexWriter.builDocWeightFile(currentDirectory.toString(), "P");
				//	IndexWriter.builDocWeightFile(currentDirectory.toString(), "B");
					IndexWriter.buildFilename(currentDirectory.toString(),fileNameLists);
		//			System.out.println("created tol file "+DiskInvertedIndex.fileNameRead().size());
					IndexWriter.buildKGramFile(currentDirectory.toString(),KGramIndex.kgrams);

					endTime = System.nanoTime();
					totalTime = endTime - startTime;
					System.out.println("Complete file & indexing Time" + TimeUnit.NANOSECONDS.toMinutes(totalTime));
					if (fileNameLists.size() > 0) {
						System.out.println(pInvertedInd.getTermCount() + "vaala");
						txtTotalFiles.setText(Integer.toString(fileNameLists.size()));// setting
																						// total
																						// file
																						// size
						txtTotalTime.setText(Long.toString(TimeUnit.NANOSECONDS.toSeconds(totalTime)));// setting
																										// total
																										// indexing
						diskExsits=true;																				// time
						JOptionPane.showMessageDialog(null, "Indexing Complete for " + fileNameLists.size() + " files");
					}
				}else{
					KGramIndex.readKGramsFromFile(currentDirectory.toString());
					fileNameLists=DiskInvertedIndex.fileNameRead(currentDirectory.toString());
					System.out.println("filenameLists"+fileNameLists.size());
					txtTotalFiles.setText(Integer.toString(fileNameLists.size()));
					txtTotalTime.setText("0");
				}
			} else {
				JOptionPane.showMessageDialog(null, "Please Select Folder To Index");
			}
		}catch (IOException ex) {
			ex.getMessage();
			ex.printStackTrace();
			Logger.getLogger(IndexPanel.class.getName()).log(Level.SEVERE, null, ex);
		}catch (Exception e) {
			e.getMessage();
			e.printStackTrace();
			Logger.getLogger(IndexPanel.class.getName()).log(Level.SEVERE, null, e);
		}
	}// GEN-LAST:event_btnIndexActionPerformed

	// Variables declaration - do not modify//GEN-BEGIN:variables
	private javax.swing.JButton btnBrowse;
	private javax.swing.JButton btnIndex;
	private javax.swing.JTextField txtFolderSelect;
	// End of variables declaration//GEN-END:variables

	/**
	 * retrieve individual token from the body and process, also passes the
	 * token for Position Inverted indexing and biword index
	 * 
	 * @param body
	 * @param index
	 * @param docID
	 */
	private void retrieveToken(String body, PositionalInvertedIndex pindex, BiWordIndexing bindex, int docID) {
		try {

			int i = 1;
			SimpleTokenStream st = new SimpleTokenStream(body);
			String token1 = "";
			String token2 = "";
			if (st.hasNextToken()) {
				token1 = st.nextToken();
				if (PositionalInvertedIndex.indexMap.containsKey(token1.replaceAll("^[^\\p{L}\\p{Nd}]+|[^\\p{L}\\p{Nd}]+$", "").toLowerCase())) {
					//kIndex.generateKgram(token1.replaceAll("^[^\\p{L}\\p{Nd}]+|[^\\p{L}\\p{Nd}]+$", "").toLowerCase());
				}
				// PI INDEX
				invertedIndexTerm(token1, docID, 0, pindex);

			}
			while (st.hasNextToken()) {
				token2 = st.nextToken();

				if (PositionalInvertedIndex.indexMap.containsKey(token2.replaceAll("^[^\\p{L}\\p{Nd}]+|[^\\p{L}\\p{Nd}]+$", "").toLowerCase())) {
				//	kIndex.generateKgram(token2.replaceAll("^[^\\p{L}\\p{Nd}]+|[^\\p{L}\\p{Nd}]+$", "").toLowerCase());
				}
				// BIWORD INDEX
				bindex.addTerm(PorterStemmer.processWord(token1), PorterStemmer.processWord(token2), docID);
				// PI INDEX
				invertedIndexTerm(token2, docID, i, pindex);

				i++;
				token1 = token2;
			}
		} catch (Exception e) {
			Logger.getLogger(IndexPanel.class.getName()).log(java.util.logging.Level.SEVERE, null, e);
			e.printStackTrace();
		}
	}

	public String getTxtFolderSelect() {
		return txtFolderSelect.getText();
	}

	public void setTxtFolderSelect(javax.swing.JTextField txtFolderSelect) {
		this.txtFolderSelect = txtFolderSelect;
	}

	// passing token to Index file for PI Index
	private void invertedIndexTerm(String token1, Integer docid, Integer i, PositionalInvertedIndex pindex) {

		if (token1.contains("-")) {
			for (String splitTok : PorterStemmer.processWordHypen(token1)) {
				pindex.addTerm(PorterStemmer.processWord(splitTok), docid, i);

			}
			pindex.addTerm(PorterStemmer.processWord(token1.replaceAll("-", "")), docid, i);
		} else {
			pindex.addTerm(PorterStemmer.processWord(token1), docid, i);
		}
	}

}
