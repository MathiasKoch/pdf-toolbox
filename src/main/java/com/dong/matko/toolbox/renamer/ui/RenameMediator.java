package com.dong.matko.toolbox.renamer.ui;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import javax.swing.filechooser.FileFilter;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.WordUtils;

import com.dong.matko.toolbox.Resources;
import com.dong.matko.toolbox.renamer.bean.FileRenamer;
import com.dong.matko.toolbox.renamer.bean.Option;
import com.dong.matko.toolbox.renamer.bean.RenamerOption;
import com.dong.matko.toolbox.renamer.gui.action.PreviewButton;
import com.dong.matko.toolbox.renamer.gui.action.RenameButton;
import com.dong.matko.toolbox.renamer.gui.action.UndoButton;
import com.dong.matko.toolbox.renamer.gui.list.RenamerFileList;
import com.dong.matko.toolbox.renamer.gui.option.regexp.RegexpString;
import com.dong.matko.toolbox.renamer.gui.option.excel.ExcelUtilities;
import com.dong.matko.toolbox.writer.ReadExcel;
import com.dong.matko.toolbox.writer.WriterFile;


/**
 * files
 * files rename
 */
public class RenameMediator {

	private RenameButton rename;
	private RenamerFileList list;

	private ArrayList<FileRenamer> renameFiles;
	private RenamerOption options;
	private UndoButton undo;
	private RegexpString regexp;
	private JLabel groupLabel;
	private JLabel excelLabel;
	// --- Restore default settings
	private ArrayList<Option> optionList;
	private boolean blocking;
	// --- Formats
	private String formatDay;
	private String formatMonth;
	private String formatYear;
	private String formatIncrement;
	private String formatFilename;
	private String formatExtension;
	private String formatGroup;
	private String formatTecdocDrawingOne;
	private String formatTecdocDrawingTwo;
	private String formatTecdocDrawingThree;
	private String formatTecdocLocation;
	private String formatTecdocBlock;
	private String formatTecdocF0;
	private String formatTecdocRDSPP;
	// --- Main Frame
	private JFrame main;
	// --- File chooser
	private JFileChooser chooser;
	private PreviewButton preview;
	// --- Regexp flag
	private boolean newRegexpFlag;
	// --- TECDOC Excel file
	private String excelFilePath;
	private ArrayList<WriterFile> tecdocArray;

	public RenameMediator(JFrame mainFrame) {
		main=mainFrame;
		newRegexpFlag=false;
		chooser = new JFileChooser();
		chooser.setMultiSelectionEnabled(true);
		chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		options = new RenamerOption();
		renameFiles = new ArrayList<FileRenamer>();
		optionList = new ArrayList<Option>();
		blocking = false;
		formatDay=Resources.get("format.date.day");
		formatMonth=Resources.get("format.date.month");
		formatYear=Resources.get("format.date.year");
		formatIncrement=Resources.get("format.increment");
		formatFilename=Resources.get("format.filename");
		formatExtension=Resources.get("format.extension");
		formatGroup=Resources.get("format.group");
		formatTecdocDrawingOne=Resources.get("format.tecdoc.drawing.one");
		formatTecdocDrawingTwo=Resources.get("format.tecdoc.drawing.two");
		formatTecdocDrawingThree=Resources.get("format.tecdoc.drawing.three");
		formatTecdocLocation=Resources.get("format.tecdoc.location");
		formatTecdocBlock=Resources.get("format.tecdoc.block");
		formatTecdocF0=Resources.get("format.tecdoc.F0");
		formatTecdocRDSPP=Resources.get("format.tecdoc.RDSPP");
	}

	// --- Click on exit button
	public void exit() {
		System.exit(0);
	}

	public void rename() {
		doRename(false, false, true);
	}

	// --- Undo operation
	public void undo() {
		boolean isUndo = false;
		for (FileRenamer fr : renameFiles) {
			isUndo = isUndo || fr.undoRename()>0;
		}
		list.setData(renameFiles);
		undo.setEnabled(isUndo);
	}

	public void setFileRenamer(ArrayList<File> files) {
		renameFiles = new ArrayList<FileRenamer>();
		for (File file : files) {
			renameFiles.add(new FileRenamer(file.getAbsolutePath()));
		}

	}

	// --- Setting generique options
	@SuppressWarnings("rawtypes")
	public void setOption(Object value, String setter, Class param) {
		try {
			Method method = options.getClass().getDeclaredMethod("set"+setter, new Class[] {param});
			method.invoke(options, new Object[] {value});
		} catch (NoSuchMethodException nsme) {
			nsme.printStackTrace();
		} catch (InvocationTargetException ite) {
			ite.printStackTrace();
		} catch (IllegalAccessException iae) {
			iae.printStackTrace();
		}
		doRename(true, false, false);
	}

	public void restoreDefault() {
		blocking=true;
		options.setCapitalize(false);
		options.setLowercase(false);
		options.setUppercase(false);
		options.setUncapitalize(false);
		options.setSwapCase(false);
		for (Option opt : optionList) {
			opt.restoreDefault();
		}
		regexp.setText(regexp.getDefault());
		blocking=false;
		setRegexpString(regexp.getDefault());
	}

	public void setRegexpString(String text) {
		try {
			Pattern pattern = Pattern.compile(text);
			regexp.paintStringOK(true);
			// remplacement
			if (pattern.equals(options.getRegexp())) {
				return;
			}
			options.setRegexp(pattern);
			// compte le nombre de groups
			int n = StringUtils.countMatches(text, "(");
			groupLabel.setText(String.valueOf(n)+" "+Resources.get("option.regexp.count"));
			// --- Tooltip
//			String tooltip = Resources.get("tooltip.group.no");
//			if (n>0) {
//				if (n==1) {
//					tooltip = formatGroup+String.valueOf(0);
//				} else if (n==2) {
//					tooltip = formatGroup+String.valueOf(0)+" & "+formatGroup+String.valueOf(1);
//				} else {
//					tooltip = formatGroup+String.valueOf(0)+" ... "+formatGroup+String.valueOf(n);
//				}
//				tooltip = Resources.get("tooltip.group")+" "+tooltip;
//			}
//			regexp.setToolTipText(tooltip);
			// mise a jour de l'affichage
			doRename(true, true, false);
			//rename.setEnabled(renameFiles.size()>0);
			blocking=false;
		} catch (PatternSyntaxException pse) {
			regexp.paintStringOK(false);
			rename.setEnabled(false);
			blocking=true;
		}
	}

	public void setLivePreview(boolean selected) {
		preview.setEnabled(!selected);
		options.setLivePreview(selected);
		if (selected) {
			doRename(true, newRegexpFlag, false);
		}
	}

	public void preview() {
		doRename(true, newRegexpFlag, true);
	}

	private void doRename(boolean preview, boolean newRegexp, boolean forced) {
		newRegexpFlag = newRegexp||newRegexpFlag;
		if (blocking || (!options.isLivePreview()&&!forced)) {
			return;
		}
		newRegexpFlag = false;
		boolean isUndo = false;
		options.setDate();
		Matcher m;
		int increment = options.getIncrStart();
		String format = "0";
		for (int i=1; i<options.getIncrDigits(); i++) {
			format += "0";
		}
		DecimalFormat df = new DecimalFormat(format);
		for (FileRenamer fr : renameFiles) {
			if (newRegexp) {
				fr.setMatching(fr.getName().matches(options.getRegexp().pattern()));
			}
			String name = fr.getName();
			if (fr.isMatching()) {
				name = new String(options.getOutput());

				// --- Filename replacement
				name = name.replaceAll(formatFilename, FilenameUtils.getBaseName(fr.getName()));
				name = name.replaceAll(formatExtension, FilenameUtils.getExtension(fr.getName()));

				// --- Date replacement
				name = name.replaceAll(formatDay, options.getDay());
				name = name.replaceAll(formatMonth, options.getMonth());
				name = name.replaceAll(formatYear, options.getYear());

				// --- Increment replacement
				if (name.contains(formatIncrement)) {
					name = name.replaceAll(formatIncrement, df.format(increment));
					increment += options.getIncrStep();
				}

				// --- Group replacement
				m = options.getRegexp().matcher(fr.getName());
				if (m.matches()) {
					for (int i=1; i<m.groupCount()+1; i++) {
						name = name.replaceAll(formatGroup+(i-1), m.group(i));
					}
				}

				// --- TECDOC replacement
				name = name.replaceAll(formatTecdocDrawingOne, ExcelUtilities.getDrawingText(tecdocArray, fr.getName(),1));
				name = name.replaceAll(formatTecdocDrawingTwo, ExcelUtilities.getDrawingText(tecdocArray, fr.getName(),2));
				name = name.replaceAll(formatTecdocDrawingThree, ExcelUtilities.getDrawingText(tecdocArray, fr.getName(),3));
				name = name.replaceAll(formatTecdocLocation, ExcelUtilities.getLocation(tecdocArray, fr.getName()));
				name = name.replaceAll(formatTecdocBlock, ExcelUtilities.getBlock(tecdocArray, fr.getName()));
				name = name.replaceAll(formatTecdocF0, ExcelUtilities.getF0(tecdocArray, fr.getName()));
				name = name.replaceAll(formatTecdocRDSPP, ExcelUtilities.getRDSPP(tecdocArray, fr.getName()));

				// --- Case
				if (options.isUppercase()) {
					name = name.toUpperCase();
				} else if (options.isLowercase()) {
					name = name.toLowerCase();
				} else if (options.isSwapCase()) {
					name = StringUtils.swapCase(name);
				} else if (options.isCapitalize()) {
					name = WordUtils.capitalizeFully(name);
				}

				// --- Spaces
				if (options.isDoubleSpaces()) {
					name = name.replaceAll(" {2,}", " ");
				}
				if (!options.isSpacesAllowed()) {
					name = name.replaceAll(" +", "");
				}
				if (options.isTrim()) {
					name = name.trim();
				}

				// --- Removing
				if (options.isParenthesis()) {
					// add a replace character
					name = name.replaceAll("\\([^\\(\\)]*\\)", "");
				}
				if (options.isPeriods()) {
					// add a replace character
					int i=name.indexOf(".");
					while (i>=0 && i!=name.lastIndexOf(".")) {
						name = name.replaceFirst("\\.", "");
						i=name.indexOf(".");
					}
				}

				// --- Removing & replacing
				if (options.getRemove()!=null && options.getRemove().length()>0) {
					name = StringUtils.replaceChars(name, options.getRemove(), "");
				}
				if (options.getReplace()!=null && options.getReplace().length()>0) {
					String replacement = "";
					if (options.getReplaceWith()!=null) {
						replacement = options.getReplaceWith();
					}
					name = name.replace(options.getReplace(), replacement);
				}

				// --- Renaming
				if (!preview) {
					fr.renameTo(name);
				}

			}
			isUndo = isUndo || fr.undoCount()>0;
			fr.setPreview(name);

		}
		if (!preview) {
			undo.setEnabled(isUndo);
			doRename(true, false, true);
		} else {
			list.setData(renameFiles);
		}
	}

	public void setResultString(String text) {
		if (!text.equals(options.getOutput())) {
			options.setOutput(text);
			doRename(true, false, false);
		}
	}

	public void openFolder() {
		int returnVal = chooser.showOpenDialog(main);
	    if (returnVal == JFileChooser.APPROVE_OPTION) {
	    	drop(chooser.getSelectedFiles());
	    }
	}

	public void clearExcelFile() {
		excelFilePath = null;
		excelLabel.setText("none");
	}

	public void selectExcelFile(FileFilter filter) {
		chooser.setFileFilter(filter);
		chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		if (chooser.showOpenDialog(main) == JFileChooser.APPROVE_OPTION) {
			File file = chooser.getSelectedFile();
			excelFilePath = file.getAbsolutePath();
			excelLabel.setText(file.getName());
			if(excelFilePath != null){
				try {
					tecdocArray = ReadExcel.getExcelDrawingTexts(excelFilePath);
				} catch (Exception e) {
					//TODO: handle exception
				}
			}
		}
		chooser.setFileFilter(null);
		chooser.setMultiSelectionEnabled(true);
		chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
	}

	public void drop(File[] files) {
		renameFiles = new ArrayList<FileRenamer>();
		for (File file : files) {
			if (file.isDirectory()) {
				listFiles(file.listFiles());
			} else {
				renameFiles.add(new FileRenamer(file.getAbsolutePath(), options));
			}
		}
		doRename(true, false, true);
		rename.setEnabled(renameFiles.size()>0);
		undo.setEnabled(false);
	}

	private void listFiles(File[] files) {
		for (File file : files) {
			if (file.isFile()) {
				renameFiles.add(new FileRenamer(file.getAbsolutePath(), options));
			} else if (options.isRecursive()) {
				listFiles(file.listFiles());
			}
		}
	}

	/**********************************
	 * REGISTERING DISPLAY ELEMENTS *
	 **********************************/

	public void registerRename(RenameButton renameButton) {
		this.rename=renameButton;
	}

	public void registerFileList(RenamerFileList fileList) {
		this.list=fileList;
	}

	public void registerUndo(UndoButton undoButton) {
		this.undo=undoButton;
	}

	public void registerRegexpString(RegexpString regexpString) {
		this.regexp=regexpString;
	}

	public void registerGroupLabel(JLabel groupLabel) {
		this.groupLabel=groupLabel;
	}

	public void registerExcelLabel(JLabel excelLabel) {
		this.excelLabel=excelLabel;
	}

	public void registerOption(Option opt) {
		optionList.add(opt);
	}

	public void registerPreview(PreviewButton previewButton) {
		this.preview=previewButton;
	}

}
