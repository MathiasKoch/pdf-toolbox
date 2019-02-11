package com.dong.matko.toolbox.renamer.gui.action;



import java.awt.event.ActionListener;

import javax.swing.JButton;

import com.dong.matko.toolbox.Resources;
import com.dong.matko.toolbox.renamer.ui.Command;
import com.dong.matko.toolbox.renamer.ui.RenameMediator;



public class OpenButton extends JButton implements Command {

	private static final long serialVersionUID = 1L;
	private RenameMediator med;

	public OpenButton(ActionListener al, RenameMediator m) {
		super(Resources.get("button.open"));
		setOpaque(false);
		addActionListener(al);
        med = m;
	}

	public void execute() {
		med.openFolder();
	}

}
