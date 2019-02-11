package com.dong.matko.toolbox.renamer.gui.option.format;


import java.awt.event.ActionListener;
import java.util.EventListener;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JRadioButton;

import net.miginfocom.swing.MigLayout;

import com.dong.matko.toolbox.renamer.bean.Option;
import com.dong.matko.toolbox.renamer.gui.option.OptionPane;
import com.dong.matko.toolbox.renamer.gui.option.generic.OptionCheckBox;
import com.dong.matko.toolbox.renamer.gui.option.generic.OptionRadioButton;
import com.dong.matko.toolbox.renamer.ui.RenameMediator;

public class FormatPane extends OptionPane implements Option {

	private static final long serialVersionUID = 1L;
	private static final JRadioButton defaultRadioCase = new JRadioButton();
	private ButtonGroup group;

	public FormatPane(EventListener el, RenameMediator m) {
		super(new MigLayout("", "[]50[]50[]", "[]0[]0[]0[]0[]"), "title.format");

		m.registerOption(this);

		group = new ButtonGroup();
		AbstractButton lowercase = new OptionRadioButton((ActionListener) el, m, "option.format.lowercase", "Lowercase", false);
		AbstractButton uppercase = new OptionRadioButton((ActionListener) el, m, "option.format.uppercase", "Uppercase", false);
		AbstractButton swapcase = new OptionRadioButton((ActionListener) el, m, "option.format.swapcase", "SwapCase", false);
		AbstractButton capitalize = new OptionRadioButton((ActionListener) el, m, "option.format.capitalize", "Capitalize", false);
		AbstractButton uncapitalize = new OptionRadioButton((ActionListener) el, m, "option.format.uncapitalize", "Uncapitalize", false);
		group.add(lowercase);
		group.add(uppercase);
		group.add(swapcase);
		group.add(capitalize);
		group.add(uncapitalize);
		group.add(defaultRadioCase);
		setOpaque(false);

		// --- Layout
		add(lowercase, "cell 0 0");
		add(uppercase, "cell 0 1");
		add(swapcase, "cell 0 2");
		add(capitalize, "cell 0 3");
		add(uncapitalize, "cell 0 4");
		add(new OptionCheckBox((ActionListener) el, m, "option.format.trim", "Trim", true), "cell 1 0");
		add(new OptionCheckBox((ActionListener) el, m, "option.format.remove.blank", "SpacesAllowed", true), "cell 1 1");
		add(new OptionCheckBox((ActionListener) el, m, "option.format.remove.double.spaces", "DoubleSpaces", true), "cell 1 2");
		add(new OptionCheckBox((ActionListener) el, m, "option.format.parenthesis", "Parenthesis", false), "cell 2 0");
		add(new OptionCheckBox((ActionListener) el, m, "option.format.extra.periods", "Periods", false), "cell 2 1");

	}

	public void restoreDefault() {
		group.setSelected(defaultRadioCase.getModel(), true);
	}

}
