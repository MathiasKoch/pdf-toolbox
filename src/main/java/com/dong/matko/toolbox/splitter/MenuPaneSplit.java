package com.dong.matko.toolbox.splitter;


import java.awt.Color;
import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.filechooser.FileFilter;
import javax.swing.table.DefaultTableModel;

import net.miginfocom.swing.MigLayout;

import com.dong.matko.toolbox.MainWindow;
import com.dong.matko.toolbox.Resources;
import com.dong.matko.toolbox.unlocker.Unlocker;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.SimpleBookmark;

public class MenuPaneSplit extends JPanel {

	private static final long serialVersionUID = 1L;

	public static JButton pdfButton, runButton, helpButton, restore;
	private static JFileChooser fc;
	public static String pdfFile = null;
	public static DefaultTableModel model = new DefaultTableModel();
	private static int lastpage = 0;
	private static JTable table;
	private static ArrayList<Integer> level = new ArrayList<Integer>();

	public MenuPaneSplit() {
		setLayout(new MigLayout("flowy", "5[]5", "5[]10[]10[]push[][]5"));
		setBackground(Color.WHITE);
		setBorder(BorderFactory.createMatteBorder(0, 1, 0, 0, Color.GRAY));
		model.addColumn(Resources.get("table.splitter.bookmark"));
		model.addColumn(Resources.get("table.splitter.page"));

		final JPanel libelles = new JPanel(new MigLayout("wrap 2, fillx", "5[align left]36", ""));
		libelles.setOpaque(false);
		libelles.setBorder(Resources.createTitledBorder("title.quickhelp"));
		libelles.add(new JLabel(Resources.get("quick.splitter.type")), "wrap");
		libelles.add(new JLabel(Resources.get("quick.splitter.file")), "wrap, gaptop 3px");
		libelles.add(new JLabel(Resources.get("quick.splitter.mark")), "wrap, gaptop 3px");
		libelles.add(new JLabel(Resources.get("quick.splitter.options")), "wrap, gaptop 3px");
		libelles.add(new JLabel(Resources.get("quick.splitter.help")), "wrap, gaptop 3px");

		add(libelles, "alignx");



		fc = new JFileChooser(".");
		FileFilter filter2 = new ExtensionFileFilter("pdf", new String[] { "pdf"});
		fc.setFileFilter(filter2);
		fc.setFileSelectionMode(JFileChooser.FILES_ONLY);

		pdfButton = new JButton(Resources.get("button.splitter.select"), createImageIcon("Open16.gif"));
		pdfButton.addActionListener(new ActionListener() {
			@SuppressWarnings("unchecked")
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (fc.showOpenDialog(libelles) == JFileChooser.APPROVE_OPTION) {
					Runnable r = new Runnable() {
						public void run() {
							MainWindow.showModal("Loading..");
							for(int i = model.getRowCount() - 1; i >= 0; i--){
								model.removeRow(i);
							}
							level.clear();
							File file = fc.getSelectedFile();
							pdfFile = file.getAbsolutePath();
							try {
								PdfReader reader = new PdfReader(pdfFile.toString());
								lastpage = reader.getNumberOfPages();
								List<HashMap<String, Object>> bookmarks = SimpleBookmark.getBookmark(reader);
								if(!(bookmarks == null) && !bookmarks.isEmpty()){
									for(int i = 0; i < bookmarks.size(); i++){
										model.addRow(new Object[]{bookmarks.get(i).get("Title"), bookmarks.get(i).get("Page").toString().substring(0, bookmarks.get(i).get("Page").toString().indexOf(" "))});
										level.add(1);
										if(bookmarks.get(i).containsKey("Kids")){
											List<HashMap<String, Object>> subbookmarks = (List<HashMap<String, Object>>) bookmarks.get(i).get("Kids");
											for(int x = 0; x < subbookmarks.size(); x++){
												model.addRow(new Object[]{"   " + subbookmarks.get(x).get("Title"), subbookmarks.get(x).get("Page").toString().substring(0, subbookmarks.get(x).get("Page").toString().indexOf(" "))});
												level.add(2);
												if(subbookmarks.get(x).containsKey("Kids")){
													List<HashMap<String, Object>> subsubbookmarks = (List<HashMap<String, Object>>) subbookmarks.get(x).get("Kids");
													for(int y = 0; y < subsubbookmarks.size(); y++){
														model.addRow(new Object[]{"      " + subsubbookmarks.get(y).get("Title"), subsubbookmarks.get(y).get("Page").toString().substring(0, subsubbookmarks.get(y).get("Page").toString().indexOf(" "))});
														level.add(3);
														if(subsubbookmarks.get(y).containsKey("Kids")){
															List<HashMap<String, Object>> sub3bookmarks = (List<HashMap<String, Object>>) subsubbookmarks.get(y).get("Kids");
															for(int m = 0; m < sub3bookmarks.size(); m++){
																model.addRow(new Object[]{"         " + sub3bookmarks.get(m).get("Title"), sub3bookmarks.get(m).get("Page").toString().substring(0, sub3bookmarks.get(m).get("Page").toString().indexOf(" "))});
																level.add(4);
																if(sub3bookmarks.get(m).containsKey("Kids")){
																	List<HashMap<String, Object>> sub4bookmarks = (List<HashMap<String, Object>>) sub3bookmarks.get(m).get("Kids");
																	for(int n = 0; n < sub4bookmarks.size(); n++){
																		model.addRow(new Object[]{"            " + sub4bookmarks.get(n).get("Title"), sub4bookmarks.get(n).get("Page").toString().substring(0, sub4bookmarks.get(n).get("Page").toString().indexOf(" "))});
																		level.add(5);
																	}
																}
															}
														}
													}
												}
											}
										}
									}
									runButton.setEnabled(true);
								}else{
									model.addRow(new Object[]{Resources.get("table.splitter.nobookmark"),""});
								}
								reader.close();
							} catch (IOException e1) {
								e1.printStackTrace();
							}
							MainWindow.hideModal();
						}
					};
					new Thread(r).start();
				}
			}
		});
		helpButton = new JButton(Resources.get("button.generic.help"));
		helpButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (Desktop.isDesktopSupported()) {
					InputStream in = getClass().getResourceAsStream("guide.pdf");
					File tmpFile;
					try {
						tmpFile = File.createTempFile("userguide", ".pdf");
						OutputStream out = new FileOutputStream(tmpFile);
						byte[] buffer = new byte[1024];
						int len;
						while ((len = in.read(buffer)) != -1) {
							out.write(buffer, 0, len);
						}
						out.close();
						in.close();
						Desktop.getDesktop().open(tmpFile);
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			}
		});

		runButton = new JButton(Resources.get("button.splitter.run"));
		runButton.setEnabled(false);
		runButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				Runnable r = new Runnable() {
					public void run() {
						MainWindow.showModal("Splitting..");
						if(Unlocker.isLocked(new File(pdfFile)) && TabSplitter.unlock.isSelected()){
							Unlocker.unlock(pdfFile, false);
						}
						ArrayList<String> nameBook = new ArrayList<String>();
						ArrayList<Integer> pageBook = new ArrayList<Integer>();
						ArrayList<Integer> levelBook = new ArrayList<Integer>();

						if(TabSplitter.curPane == 0){
							int[] selectedrows = table.getSelectedRows();
							for(int row : selectedrows){
								int pagestart = Integer.parseInt(table.getValueAt(row, 1).toString());
								int pageend = 0;
								nameBook.clear();
								pageBook.clear();
								levelBook.clear();
								if(row != table.getRowCount() - 1){
									for(int f = row + 1; f <= table.getRowCount(); f++){
										if(row == table.getRowCount() - 1){
											pageend = lastpage;
											break;
										}
										if(level.get(row) == level.get(f)){
											pageend = Integer.parseInt(table.getValueAt(f, 1).toString());
											break;
										}else{
											nameBook.add(table.getValueAt(f, 0).toString().trim());
											levelBook.add(level.get(f));
											pageBook.add(Integer.parseInt(table.getValueAt(f, 1).toString()));
											pageend = lastpage;
										}
									}
								}else{
									pageend = lastpage;
								}
								try {
									String path  = pdfFile.substring(0, pdfFile.lastIndexOf("\\")) + "\\";
									String newpath = path + table.getValueAt(row, 0).toString().replaceAll("\\s\\s+", "").replace("/", " - ").replace("\\", " - ").replace(":", " -") + ".pdf";
									PdfSplitter.splitpdf(pdfFile, new FileOutputStream(newpath), pagestart, pageend - 1, pageBook, nameBook, levelBook);
								} catch (FileNotFoundException e) {
									e.printStackTrace();
								}
							}
						}else if(TabSplitter.curPane == 1){

							if(Math.ceil((double)lastpage/Double.parseDouble(TabSplitter.npage.getText())) > 0){
								for(int i = 0; i < Math.ceil((double)lastpage/Double.parseDouble(TabSplitter.npage.getText())); i++){
									int pagestart = (Integer.parseInt(TabSplitter.npage.getText()) * i) + 1;
									int pageend = (i == (Math.ceil((double)lastpage/Double.parseDouble(TabSplitter.npage.getText())) - 1))? lastpage : pagestart + Integer.parseInt(TabSplitter.npage.getText()) - 1;
									String newpath = pdfFile.replace(".pdf", "_" + (i + 1) + ".pdf");
									try {
										PdfSplitter.splitpdf(pdfFile, new FileOutputStream(newpath), pagestart, pageend, null, null, null);
									} catch (FileNotFoundException e) {
										e.printStackTrace();
									}
								}
							}else{
								// npage is larger than amount of pages in pdf
							}

						}else if(TabSplitter.curPane == 2){
							String[] strVals = TabSplitter.commapage.getText().split(",");
							int i = 0;
							int lastPage = -1;
							for(String strVal : strVals){
								if(!strVal.equals("")){
									int val = Integer.parseInt(strVal);
									if(val <= lastpage){
										int pagestart = lastPage;
										int pageend = val;
										if(i == 0)
											pagestart = 1;
										String newpath = pdfFile.replace(".pdf", "_" + (i+1) + ".pdf");
										try {
											PdfSplitter.splitpdf(pdfFile, new FileOutputStream(newpath), pagestart, pageend, null, null, null);
										} catch (FileNotFoundException e) {
											e.printStackTrace();
										}
										lastPage = pageend;
										i++;
									}
								}
							}
							if(lastPage != -1){
								try {
									PdfSplitter.splitpdf(pdfFile, new FileOutputStream(pdfFile.replace(".pdf", "_" + (i+1) + ".pdf")), lastPage, lastpage, null, null, null);
								} catch (FileNotFoundException e) {
									e.printStackTrace();
								}
							}
						}
						MainWindow.hideModal();
						runButton.setEnabled(true);
					}
				};
				new Thread(r).start();
			}
		});

		restore = new JButton(Resources.get("button.splitter.restore"));
		restore.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				for(int i = model.getRowCount() - 1; i >= 0; i--){
					model.removeRow(i);
				}
				level.clear();
				TabSplitter.unlock.setSelected(false);
				pdfButton.setEnabled(true);
				runButton.setEnabled(false);
				TabSplitter.bookmarkButton.setSelected(true);
				TabSplitter.drop.remove(TabSplitter.midPanel);
				TabSplitter.drop.remove(TabSplitter.midPanel2);
				TabSplitter.drop.add(TabSplitter.pane, "grow");
				TabSplitter.drop.setBorder(Resources.createTitledSeparator("title.splitter.bookmark"));
				TabSplitter.curPane = 0;
				TabSplitter.drop.revalidate();
				TabSplitter.drop.repaint();
			}
		});

		add(pdfButton, 	"alignx center, growx, sg 1");
		add(runButton, 	"alignx center, growx, sg 1");
		add(restore, 	"alignx center, growx, sg 1");
		add(helpButton, "alignx center, growx, sg 1");

	}

	protected static ImageIcon createImageIcon(String path) {
		java.net.URL imgURL = TabSplitter.class.getClassLoader().getResource(path);
		if (imgURL != null) {
			return new ImageIcon(imgURL);
		} else {
			System.err.println("Couldn't find file: " + path);
			return null;
		}
	}
}
class ExtensionFileFilter extends FileFilter {
	String description;
	String extensions[];
	public ExtensionFileFilter(String description, String extension) {
		this(description, new String[] { extension });
	}

	public ExtensionFileFilter(String description, String extensions[]) {
		if (description == null) {
			this.description = extensions[0];
		} else {
			this.description = description;
		}
		this.extensions = (String[]) extensions.clone();
		toLower(this.extensions);
	}

	private void toLower(String array[]) {
		for (int i = 0, n = array.length; i < n; i++) {
			array[i] = array[i].toLowerCase();
		}
	}

	public String getDescription() {
		return description;
	}

	public boolean accept(File file) {
		if (file.isDirectory()) {
			return true;
		} else {
			String path = file.getAbsolutePath().toLowerCase();
			for (int i = 0, n = extensions.length; i < n; i++) {
				String extension = extensions[i];
				if ((path.endsWith(extension) && (path.charAt(path.length() - extension.length() - 1)) == '.')) {
					return true;
				}
			}
		}
		return false;
	}
}
