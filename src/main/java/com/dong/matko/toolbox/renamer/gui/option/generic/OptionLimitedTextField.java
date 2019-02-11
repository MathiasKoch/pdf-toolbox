package com.dong.matko.toolbox.renamer.gui.option.generic;


import javax.swing.event.CaretListener;

import com.dong.matko.toolbox.renamer.bean.Option;
import com.dong.matko.toolbox.renamer.gui.model.LimitedTextField;
import com.dong.matko.toolbox.renamer.ui.Command;
import com.dong.matko.toolbox.renamer.ui.RenameMediator;



public class OptionLimitedTextField extends LimitedTextField implements Command, Option {

	private static final long serialVersionUID = 1L;
	private RenameMediator med;
	private String option;
	private String defaultValue;

	public OptionLimitedTextField(CaretListener cl, RenameMediator m, String opt, String val, int limit) {
		super(val, limit);
		option = opt;
		defaultValue=val;
		addCaretListener(cl);
        med = m;
        med.registerOption(this);
        execute();
	}
	
	public void execute() {
		med.setOption(getText(), option, String.class);
	}

	public void restoreDefault() {
		setText(defaultValue);
		execute();
	}

}
