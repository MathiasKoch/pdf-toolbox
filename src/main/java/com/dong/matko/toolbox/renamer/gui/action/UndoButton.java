package com.dong.matko.toolbox.renamer.gui.action;



import java.awt.event.ActionListener;

import javax.swing.JButton;

import com.dong.matko.toolbox.Resources;
import com.dong.matko.toolbox.renamer.ui.Command;
import com.dong.matko.toolbox.renamer.ui.RenameMediator;



public class UndoButton extends JButton implements Command {

	private static final long serialVersionUID = 1L;
	private RenameMediator med;

	public UndoButton(ActionListener al, RenameMediator m) {
		super(Resources.get("button.undo"));
		setEnabled(false);
		setOpaque(false);
		addActionListener(al);
        med = m;
        med.registerUndo(this);
	}

	public void execute() {
		med.undo();
	}

}
