package com.dong.matko.toolbox.renamer.gui.option.regexp;

import java.awt.Color;

import javax.swing.JTextField;
import javax.swing.event.CaretListener;

import com.dong.matko.toolbox.renamer.ui.Command;
import com.dong.matko.toolbox.renamer.ui.RenameMediator;



public class RegexpString extends JTextField implements Command {

	private static final long serialVersionUID = 1L;
	private RenameMediator med;
	private static final String defaultValue = "(.*)";

	public RegexpString(CaretListener cl, RenameMediator m) {
		super(defaultValue);
		addCaretListener(cl);
        med = m;
        med.registerRegexpString(this);
        execute();
	}
	
	public void execute() {
		med.setRegexpString(getText());
	}
	
	public void paintStringOK(boolean ok) {
		if (ok) {
			this.setBackground(Color.WHITE);
			this.setForeground(Color.BLACK);
		}
		else {
			this.setBackground(Color.RED);
			this.setForeground(Color.WHITE);
		}
	}

	public String getDefault() {
		return defaultValue;
	}

}
