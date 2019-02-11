package com.dong.matko.toolbox.renamer.gui.option.regexp;


import java.util.EventListener;

import javax.swing.JLabel;
import javax.swing.event.CaretListener;

import net.miginfocom.swing.MigLayout;

import com.dong.matko.toolbox.Resources;
import com.dong.matko.toolbox.renamer.gui.option.OptionPane;
import com.dong.matko.toolbox.renamer.ui.RenameMediator;

public class RegexpPane extends OptionPane {

	private static final long serialVersionUID = 1L;

	public RegexpPane(EventListener el, RenameMediator m) {
		super(new MigLayout("fillx", "0[][]0", "[]"), "title.regexp");
		JLabel groupLabel = new JLabel();
		groupLabel.setFont(Resources.BOLD);
		m.registerGroupLabel(groupLabel);
		// --- Layout
		add(new RegexpString((CaretListener) el, m), 							"growx");
		add(groupLabel, 														"");
	}

}
