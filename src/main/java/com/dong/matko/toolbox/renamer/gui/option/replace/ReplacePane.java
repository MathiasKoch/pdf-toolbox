package com.dong.matko.toolbox.renamer.gui.option.replace;


import java.util.EventListener;

import javax.swing.JLabel;
import javax.swing.event.CaretListener;

import com.dong.matko.toolbox.Resources;
import com.dong.matko.toolbox.renamer.gui.option.OptionPane;
import com.dong.matko.toolbox.renamer.gui.option.generic.OptionTextField;
import com.dong.matko.toolbox.renamer.ui.RenameMediator;


public class ReplacePane extends OptionPane {

	private static final long serialVersionUID = 1L;

	public ReplacePane(EventListener el, RenameMediator m) {
		super("title.replace");

		//add(new NonEditableTextArea(Resources.get("option.replace.comment")), 			"growx, span 2, wrap");
		// --- Remove
		add(new JLabel(Resources.get("option.replace.remove")+" :"), 					"");
		add(new OptionTextField((CaretListener) el, m, "Remove", ""), 					"growx, wmin 250, wrap");
		// --- Replace all
		add(new JLabel(Resources.get("option.replace.replace")+" :"), 					"");
		add(new OptionTextField((CaretListener) el, m, "Replace", ""), 					"growx, split");
		add(new JLabel(Resources.get("option.replace.replace.with")+" :"), 				"split");
		add(new OptionTextField((CaretListener) el, m, "ReplaceWith", ""), 	"split, growx, wmax 50");
	}

}
