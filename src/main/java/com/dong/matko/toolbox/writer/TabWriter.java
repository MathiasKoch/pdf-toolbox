package com.dong.matko.toolbox.writer;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

import net.miginfocom.swing.MigLayout;

import com.dong.matko.toolbox.MainWindow;
import com.dong.matko.toolbox.Resources;
import com.dong.matko.toolbox.renamer.gui.list.model.ZebraJTable;
import com.dong.matko.toolbox.renamer.gui.option.OptionPane;

public class TabWriter {
	public static JTextField textField, customText;
	private static JButton xmlButton, pdfButton, pdfPosButton, wordPosButton, excelPosButton;
	private static JFileChooser fc, fc2;
	public static String dir, RDSPP = null, dirIndex;
	public static JCheckBox redButton, revButton, unlockButton, sheetButton, pdfCheck, wordCheck, excelCheck;
	private static JPanel jplPanel;
	public static ArrayList<WriterFile> arr;
	public static ArrayList<Position> PDF = new ArrayList<Position>();
	public static ArrayList<Position> WORD = new ArrayList<Position>();
	public static ArrayList<Position> EXCEL = new ArrayList<Position>();
	public static DefaultTableModel model = new DefaultTableModel(){

		private static final long serialVersionUID = 1L;

		@Override
		public boolean isCellEditable(int row, int column) {
			//all cells false
			return false;
		}
	};
	public static TableRowSorter<TableModel> sorter;
	public static JTable table;
	public static JLabel currLabel, cLabel, nrSysLabel, nLabel;
	public static enum H {
		LEFT, RIGHT, CENTER;
	}
	public static enum V {
		TOP, BOTTOM;
	}

	public static int errorCount, nrFolders, nrFiles, currentFolder, currentFile = 0;

	public static JPanel setupUI(){
		DocumentFilter filter = new UppercaseDocumentFilter();

		JPanel jpl = new JPanel(new MigLayout("", "10[][grow][][]10[grow]", "10[][]10[grow]10[]5"));

		OptionPane open = new OptionPane(new MigLayout("", ""), "title.writer.open");
		OptionPane location = new OptionPane(new MigLayout("", ""), "title.writer.location");
		OptionPane customSys = new OptionPane(new MigLayout("", ""), "title.writer.custom");
		OptionPane position = new OptionPane(new MigLayout("", ""), "title.writer.position");
		OptionPane fileFilter = new OptionPane(new MigLayout("", ""), "title.writer.filefilter");
		OptionPane opt = new OptionPane(new MigLayout("", ""), "title.options");
		OptionPane drop = new OptionPane(new MigLayout("", "[]5[]15[]5[45]"), "title.writer.pdflist");
		MenuPaneWriter menu = new MenuPaneWriter();

		fc = new JFileChooser();
		FileFilter filter1 = new ExtensionFileFilter("Excel file (xls, xlsx, xlsm)", new String[] { "xlsm", "xls", "xlsx"});
		fc.setFileFilter(filter1);
		fc2 = new JFileChooser();
		fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
		fc2.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		currLabel = new JLabel(Resources.get("title.writer.currlabel"));
		nrSysLabel = new JLabel(Resources.get("title.writer.nrSyslabel"));
		nLabel = new JLabel("");
		cLabel = new JLabel("");
		nLabel.setFont(new Font(nLabel.getFont().getName(),Font.BOLD,nLabel.getFont().getSize()));
		cLabel.setFont(new Font(cLabel.getFont().getName(),Font.BOLD,cLabel.getFont().getSize()));

		xmlButton = new JButton(Resources.get("button.writer.excel"),
				createImageIcon("Open16.gif"));
		xmlButton.setPreferredSize(new Dimension(120, 20));
		xmlButton.setHorizontalAlignment(SwingConstants.LEFT);
		xmlButton.setMargin(new Insets(2,5,2,2));
		xmlButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (fc.showOpenDialog(jplPanel) == JFileChooser.APPROVE_OPTION) {
					File file = fc.getSelectedFile();
					RDSPP = file.getAbsolutePath();
					cLabel.setText(file.getName());
					fc2.setCurrentDirectory(file);
					if(dir != null){
						fillTable();
					}
				}
			}
		});


		pdfButton = new JButton(Resources.get("button.writer.pdf"),
				createImageIcon("Open16.gif"));
		pdfButton.setPreferredSize(new Dimension(120, 20));
		pdfButton.setHorizontalAlignment(SwingConstants.LEFT);
		pdfButton.setMargin(new Insets(2,5,2,2));
		pdfButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (fc2.showOpenDialog(jplPanel) == JFileChooser.APPROVE_OPTION) {
					File file = fc2.getSelectedFile();
					dir = file.getAbsolutePath();
					dirIndex = dir.substring(dir.lastIndexOf(File.separator) + 1);
					if(RDSPP != null){
						fillTable();
					}
				}
			}
		});

		pdfPosButton = new JButton(Resources.get("button.writer.pdfPos"));
		pdfPosButton.setPreferredSize(new Dimension(150, 20));
		pdfPosButton.setHorizontalAlignment(SwingConstants.LEFT);
		pdfPosButton.setMargin(new Insets(2,5,2,2));
		pdfPosButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				MainWindow.showPositionEditor("pdf");
			}
		});

		wordPosButton = new JButton(Resources.get("button.writer.wordPos"));
		wordPosButton.setPreferredSize(new Dimension(150, 20));
		wordPosButton.setHorizontalAlignment(SwingConstants.LEFT);
		wordPosButton.setMargin(new Insets(2,5,2,2));
		wordPosButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				MainWindow.showPositionEditor("word");
			}
		});

		excelPosButton = new JButton(Resources.get("button.writer.excelPos"));
		excelPosButton.setPreferredSize(new Dimension(150, 20));
		excelPosButton.setHorizontalAlignment(SwingConstants.LEFT);
		excelPosButton.setMargin(new Insets(2,5,2,2));
		excelPosButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				MainWindow.showPositionEditor("excel");
			}
		});
		customText = new JTextField(12);
		customText.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent arg0) {

			}

			@Override
			public void keyReleased(KeyEvent arg0) {
				if(arr != null)
					for(int i = 0; i < arr.size(); i++){
						arr.get(i).setCustomSystem(customText.getText());
						model.setValueAt(arr.get(i).getRDSPP(true, revButton.isSelected(), sheetButton.isSelected()), i, 1);
					}
				if(customText.getText().length() > 0)
					nLabel.setText("Custom");
				else
					if(arr != null && arr.size() > 0)
						nLabel.setText(arr.get(0).getSystem());
					else
						nLabel.setText("");
			}

			@Override
			public void keyPressed(KeyEvent arg0) {

			}
		});
		customText.setUI(new HintTextFieldUI(Resources.get("button.writer.customHint"), true, Color.GRAY));
		customText.setMargin(new Insets(2, 2, 2, 2));

		textField = new JTextField(12);
		((AbstractDocument) textField.getDocument()).setDocumentFilter(filter);
		textField.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent arg0) {
				if(textField.getText().length() == 5){
					arg0.consume();
				}else if(textField.getText().length() >= 3){
					if(!Character.isDigit(arg0.getKeyChar())){
						arg0.consume();
					}
				}else{
					if(!Character.isAlphabetic(arg0.getKeyChar())){
						arg0.consume();
					}
				}

			}

			@Override
			public void keyReleased(KeyEvent arg0) {
				if(RDSPP != null && dir != null && arr != null){
					for(int i = 0; i < arr.size(); i++){
						arr.get(i).setLocation(textField.getText());
						model.setValueAt(arr.get(i).getRDSPP(true, revButton.isSelected(), sheetButton.isSelected()), i, 1);
					}
					model.fireTableDataChanged();
				}
			}

			@Override
			public void keyPressed(KeyEvent arg0) {

			}
		});
		textField.setUI(new HintTextFieldUI(Resources.get("button.writer.hint"), true, Color.GRAY));
		textField.setMargin(new Insets(2, 2, 2, 2));
		redButton = new JCheckBox(Resources.get("button.writer.red"));
		revButton = new JCheckBox(Resources.get("button.writer.rev"));
		sheetButton = new JCheckBox(Resources.get("button.writer.sheet"));
		unlockButton = new JCheckBox(Resources.get("button.writer.unlock"));
		pdfCheck = new JCheckBox(Resources.get("button.writer.pdfcheck"));
		wordCheck = new JCheckBox(Resources.get("button.writer.wordcheck"));
		excelCheck = new JCheckBox(Resources.get("button.writer.excelcheck"));

		table = new ZebraJTable(model);
		table.setIntercellSpacing(new Dimension(0, 1));
		table.setColumnSelectionAllowed(false);
		table.setShowVerticalLines(false);
		table.setShowHorizontalLines(false);
		model.addColumn("Document");
		model.addColumn("Number");
		model.addColumn("Position");

		sorter = new TableRowSorter<TableModel>(model);
		table.setRowSorter(sorter);

		JTableHeader header = table.getTableHeader();
		header.setReorderingAllowed(false);
		header.setResizingAllowed(true);

		revButton.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent arg0) {
				if(arr != null){
					for(int i = 0; i < arr.size(); i++){
						model.setValueAt(
								arr.get(i).getRDSPP(true, revButton.isSelected(), sheetButton.isSelected()), i, 1);
					}
					model.fireTableDataChanged();
				}
			}
		});

		sheetButton.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent arg0) {
				if(arr != null){
					for(int i = 0; i < arr.size(); i++){
						model.setValueAt(
								arr.get(i).getRDSPP(true, revButton.isSelected(), sheetButton.isSelected()), i, 1);
					}
					model.fireTableDataChanged();
				}
			}
		});

		pdfCheck.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent arg0) {
				pdfPosButton.setEnabled(pdfCheck.isSelected());
				updateFilters();
			}
		});

		wordCheck.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent arg0) {
				wordPosButton.setEnabled(wordCheck.isSelected());
				updateFilters();
			}
		});

		excelCheck.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent arg0) {
				excelPosButton.setEnabled(excelCheck.isSelected());
				updateFilters();
			}
		});


		JScrollPane pane = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		pane.setWheelScrollingEnabled(true);

		opt.add(redButton);
		opt.add(revButton);
		opt.add(sheetButton);
		opt.add(unlockButton);

		position.add(pdfPosButton, "wrap, gaptop 1px");
		position.add(wordPosButton, "wrap, gaptop 2px");
		position.add(excelPosButton, "gaptop 3px");
		location.add(textField);

		fileFilter.add(pdfCheck, "wrap");
		fileFilter.add(wordCheck, "wrap");
		fileFilter.add(excelCheck);

		customSys.add(customText);

		open.add(xmlButton, "wrap");
		open.add(pdfButton);

		drop.add(currLabel, "");
		drop.add(cLabel, "push, w 200!");
		drop.add(nrSysLabel, "");
		drop.add(nLabel, "wrap, w 55!");
		drop.add(pane, "grow, gaptop 4px, span 4");
		jpl.add(open, "grow, spany 2");
		jpl.add(location, "grow");
		jpl.add(fileFilter, "grow, spany 2");
		jpl.add(position, "wrap, grow, spany 2");
		jpl.add(customSys, "grow, wrap");
		jpl.add(drop, "wrap, span 4, grow");
		jpl.add(opt, "span 4, grow");
		jpl.add(menu, "east");

		resetDefault();
		return jpl;
	}

	private static void fillTable(){
		if(model.getRowCount() > 0){
			model.setRowCount(0);
			table = new ZebraJTable(model);
		}
		if(dir != null && RDSPP != null){
			Runnable r = new Runnable() {
				public void run() {
					try {
						MainWindow.showModal("Loading...");
						arr = ReadExcel.fillTableExcel(RDSPP, dir);
						for(WriterFile f : arr){
							if(f.getType().equals("xls") || f.getType().equals("xlsx") || f.getType().equals("xlsm")){

							}else if(f.getType().equals("doc") || f.getType().equals("docx") || f.getType().equals("docm")){

							}else if(f.getType().equals("pdf")){
								WritePdf.checkPositions(f, PDF);
							}
							model.addRow(new Object[]{f.getName(), f.getRDSPP(true, revButton.isSelected(), sheetButton.isSelected()), f.getWritePosString()});
						}
						model.fireTableDataChanged();
						updateFilters();
						if(arr.size() > 0){
							textField.setText(arr.get(0).getLocation());
							if(customText.getText().length() > 0)
								nLabel.setText("Custom");
							else
								nLabel.setText(arr.get(0).getSystem());
						}
						MenuPaneWriter.runButton.setEnabled(true);
						MainWindow.hideModal();
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
			};
			new Thread(r).start();
		}
	}

	public static void resetDefault(){
		pdfCheck.setSelected(true);
		wordCheck.setSelected(false);
		excelCheck.setSelected(false);
		revButton.setSelected(false);
		redButton.setSelected(false);
		sheetButton.setSelected(false);
		customText.setText("");
		if(arr != null)
			for(int i = 0; i < arr.size(); i++){
				arr.get(i).setCustomSystem(customText.getText());
				model.setValueAt(arr.get(i).getRDSPP(true, revButton.isSelected(), sheetButton.isSelected()), i, 1);
			}
		if(customText.getText().length() > 0)
			nLabel.setText("Custom");
		else
			if(arr != null && arr.size() > 0)
				nLabel.setText(arr.get(0).getSystem());
			else
				nLabel.setText("");
		unlockButton.setSelected(true);
		PDF.clear();
		WORD.clear();
		EXCEL.clear();
		PDF.add(new Position(V.TOP, H.LEFT));
		PDF.add(new Position(V.TOP, H.CENTER));
		PDF.add(new Position(V.TOP, H.RIGHT));
		PDF.add(new Position(V.BOTTOM, H.LEFT));
		PDF.add(new Position(V.BOTTOM, H.CENTER));
		PDF.add(new Position(V.BOTTOM, H.RIGHT));
		WORD.add(new Position(V.TOP, H.LEFT));
		WORD.add(new Position(V.TOP, H.CENTER));
		WORD.add(new Position(V.TOP, H.RIGHT));
		WORD.add(new Position(V.BOTTOM, H.LEFT));
		WORD.add(new Position(V.BOTTOM, H.CENTER));
		WORD.add(new Position(V.BOTTOM, H.RIGHT));
		EXCEL.add(new Position(V.TOP, H.LEFT));
		EXCEL.add(new Position(V.TOP, H.CENTER));
		EXCEL.add(new Position(V.TOP, H.RIGHT));
		EXCEL.add(new Position(V.BOTTOM, H.LEFT));
		EXCEL.add(new Position(V.BOTTOM, H.CENTER));
		EXCEL.add(new Position(V.BOTTOM, H.RIGHT));
		updateFilters();
	}

	protected static ImageIcon createImageIcon(String path) {
		java.net.URL imgURL = TabWriter.class.getResource(path);
		if (imgURL != null) {
			return new ImageIcon(imgURL);
		} else {
			System.err.println("Couldn't find file: " + path);
			return null;
		}
	}

	public static void updateFilters() {
		RowFilter<Object, Object> pdfFilter = RowFilter.regexFilter(".*?\\.(pdf|PDF)");
		RowFilter<Object, Object> wordFilter = RowFilter.regexFilter(".*?\\.(doc|DOC|docx|DOCX)");
		RowFilter<Object, Object> excelFilter = RowFilter.regexFilter(".*?\\.(xls|XLS|XLSX|xlsx)");
		if(pdfCheck.isSelected() && wordCheck.isSelected() && excelCheck.isSelected()){
			// all checkboxes active
			sorter.setRowFilter(RowFilter.orFilter(Arrays.asList(pdfFilter, wordFilter, excelFilter)));
		}else{
			if(pdfCheck.isSelected() && wordCheck.isSelected()){
				sorter.setRowFilter(RowFilter.orFilter(Arrays.asList(pdfFilter, wordFilter)));
			}else if(pdfCheck.isSelected() && excelCheck.isSelected()){
				sorter.setRowFilter(RowFilter.orFilter(Arrays.asList(pdfFilter, excelFilter)));
			}else if(wordCheck.isSelected() && excelCheck.isSelected()){
				sorter.setRowFilter(RowFilter.orFilter(Arrays.asList(excelFilter, wordFilter)));
			}else{
				if(pdfCheck.isSelected()) {
					sorter.setRowFilter(pdfFilter);
				}else if(wordCheck.isSelected()) {
					sorter.setRowFilter(wordFilter);
				}else if(excelCheck.isSelected()) {
					sorter.setRowFilter(excelFilter);
				}else{
					if(!pdfCheck.isSelected() && !wordCheck.isSelected() && !excelCheck.isSelected()){
						sorter.setRowFilter(RowFilter.regexFilter("%%%%%%%%%%%%"));
					}
				}
			}
		}
	}
}

class UppercaseDocumentFilter extends DocumentFilter {
	public void insertString(DocumentFilter.FilterBypass fb, int offset, String text,
			AttributeSet attr) throws BadLocationException {

		fb.insertString(offset, text.toUpperCase(), attr);
	}

	public void replace(DocumentFilter.FilterBypass fb, int offset, int length, String text,
			AttributeSet attrs) throws BadLocationException {

		fb.replace(offset, length, text.toUpperCase(), attrs);
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
