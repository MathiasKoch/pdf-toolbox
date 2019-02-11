package com.dong.matko.toolbox.renamer.gui.option.output;


import javax.swing.JTextField;
import javax.swing.event.CaretListener;

import com.dong.matko.toolbox.Resources;
import com.dong.matko.toolbox.renamer.bean.Option;
import com.dong.matko.toolbox.renamer.ui.Command;
import com.dong.matko.toolbox.renamer.ui.RenameMediator;



public class OutputString extends JTextField implements Command, Option {

	private static final long serialVersionUID = 1L;
	private RenameMediator med;
	private String defaultValue;

	public OutputString(CaretListener cl, RenameMediator m) {
		super(Resources.get("format.filename")+"."+Resources.get("format.extension"));
		defaultValue = getText();
		addCaretListener(cl);
        med = m;
        med.registerOption(this);
        execute();
	}

	public void execute() {
		med.setResultString(getText());
	}

	public void restoreDefault() {
		setText(defaultValue);
		execute();
	}

}
