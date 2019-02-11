package com.dong.matko.toolbox.renamer.gui.option.output;


import java.util.EventListener;

import javax.swing.event.CaretListener;

import net.miginfocom.swing.MigLayout;

import com.dong.matko.toolbox.renamer.gui.option.OptionPane;
import com.dong.matko.toolbox.renamer.ui.RenameMediator;

public class OutputPane extends OptionPane {

	private static final long serialVersionUID = 1L;

	public OutputPane(EventListener el, RenameMediator m) {
		super(new MigLayout("fillx", "0[]0", "[]"), "title.output");
		OutputString output = new OutputString((CaretListener) el, m);
		add(output, "growx");
	}
		
	
}
