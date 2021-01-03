package com.dong.matko.toolbox.renamer.gui.option.excel;



import java.awt.event.ActionListener;

import javax.swing.JButton;

import com.dong.matko.toolbox.Resources;
import com.dong.matko.toolbox.renamer.ui.Command;
import com.dong.matko.toolbox.renamer.ui.RenameMediator;

import com.dong.matko.toolbox.writer.ExtensionFileFilter;

import javax.swing.filechooser.FileFilter;


public class OpenButton extends JButton implements Command {

	private static final long serialVersionUID = 1L;
	private RenameMediator med;

	public OpenButton(ActionListener al, RenameMediator m) {
		super(Resources.get("button.writer.excel"));
		setOpaque(false);
		addActionListener(al);
        med = m;
	}

	public void execute() {
		FileFilter filter = new ExtensionFileFilter("Excel file (xls, xlsx, xlsm)", new String[] { "xlsm", "xls", "xlsx"});
		med.selectExcelFile(filter);
	}

}
