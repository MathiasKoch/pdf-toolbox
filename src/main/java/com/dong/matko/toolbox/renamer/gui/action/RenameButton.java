package com.dong.matko.toolbox.renamer.gui.action;



import java.awt.event.ActionListener;

import javax.swing.JButton;

import com.dong.matko.toolbox.Resources;
import com.dong.matko.toolbox.renamer.ui.Command;
import com.dong.matko.toolbox.renamer.ui.RenameMediator;



public class RenameButton extends JButton implements Command {

	private static final long serialVersionUID = 1L;
	private RenameMediator med;

	public RenameButton(ActionListener al, RenameMediator m) {
		super(Resources.get("button.rename"));
		setEnabled(false);
		setOpaque(false);
		addActionListener(al);
        med = m;
        med.registerRename(this);
	}
	
	public void execute() {
		med.rename();
	}

}
