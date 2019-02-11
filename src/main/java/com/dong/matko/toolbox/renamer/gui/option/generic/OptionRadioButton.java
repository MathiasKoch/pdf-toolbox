package com.dong.matko.toolbox.renamer.gui.option.generic;



import java.awt.event.ActionListener;

import javax.swing.JRadioButton;

import com.dong.matko.toolbox.Resources;
import com.dong.matko.toolbox.renamer.ui.Command;
import com.dong.matko.toolbox.renamer.ui.RenameMediator;



public class OptionRadioButton extends JRadioButton implements Command {

	private static final long serialVersionUID = 1L;
	private RenameMediator med;
	private String option;

	public OptionRadioButton(ActionListener al, RenameMediator m, String title, String opt, boolean selected) {
		super(Resources.get(title));
		setOpaque(false);
		option = opt;
		setSelected(selected);
		addActionListener(al);
        med = m;
        // --- Enregistrement de l'option par defaut
        execute();
	}
	
	public void execute() {
		med.setOption(this.isSelected(), option, boolean.class);
	}

}
