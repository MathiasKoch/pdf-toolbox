package com.dong.matko.toolbox.renamer.gui.option.excel;

import java.awt.event.ActionListener;

import javax.swing.JLabel;

import net.miginfocom.swing.MigLayout;

import com.dong.matko.toolbox.Resources;
import com.dong.matko.toolbox.renamer.gui.option.OptionPane;
import com.dong.matko.toolbox.renamer.ui.RenameMediator;

public class ExcelPane extends OptionPane {

	private static final long serialVersionUID = 1L;

	public ExcelPane(ActionListener al, RenameMediator m) {
		super(new MigLayout("fillx", "0[]0", "[]"), "title.excel");

		JLabel fileLabel = new JLabel(Resources.get("renamer.file.label"), JLabel.LEFT);

		JLabel excelLabel = new JLabel();
		excelLabel.setFont(Resources.NORMAL);
		excelLabel.setText("none");
		m.registerExcelLabel(excelLabel);

		add(new OpenButton(al, m), "split, sg 1");
		add(fileLabel, "wrap");
		add(new ClearButton(al, m), "split, sg 1");
		add(excelLabel, "growx");
	}


}
