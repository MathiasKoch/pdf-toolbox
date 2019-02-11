package com.dong.matko.toolbox.renamer.gui.action;



import java.awt.event.ActionListener;

import javax.swing.JButton;

import com.dong.matko.toolbox.Resources;
import com.dong.matko.toolbox.renamer.ui.Command;
import com.dong.matko.toolbox.renamer.ui.RenameMediator;



public class ExitButton extends JButton implements Command {

	private static final long serialVersionUID = 1L;
	private RenameMediator med;

	public ExitButton(ActionListener al, RenameMediator m) {
		super(Resources.get("button.exit"));
		addActionListener(al);
		setOpaque(false);
        med = m;
	}

	public void execute() {
		med.exit();
	}

}
