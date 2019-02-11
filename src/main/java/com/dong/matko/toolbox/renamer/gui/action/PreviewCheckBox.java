package com.dong.matko.toolbox.renamer.gui.action;



import java.awt.event.ActionListener;

import javax.swing.JCheckBox;

import com.dong.matko.toolbox.Resources;
import com.dong.matko.toolbox.renamer.bean.Option;
import com.dong.matko.toolbox.renamer.ui.Command;
import com.dong.matko.toolbox.renamer.ui.RenameMediator;



public class PreviewCheckBox extends JCheckBox implements Command, Option {

	private static final long serialVersionUID = 1L;
	private RenameMediator med;

	public PreviewCheckBox(ActionListener al, RenameMediator m) {
		super(Resources.get("selection.live.preview"));
		setOpaque(false);
		setSelected(true);
		addActionListener(al);
        med = m;
        med.registerOption(this);
        // --- Enregistrement de l'option par defaut
        execute();
	}

	public void execute() {
		med.setLivePreview(this.isSelected());
	}

	public void restoreDefault() {
		setSelected(true);
		execute();
	}

}
