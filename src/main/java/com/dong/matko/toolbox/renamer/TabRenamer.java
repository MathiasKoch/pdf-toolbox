package com.dong.matko.toolbox.renamer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import net.miginfocom.swing.MigLayout;

import com.dong.matko.toolbox.TabHelper;
import com.dong.matko.toolbox.renamer.gui.MenuPane;
import com.dong.matko.toolbox.renamer.gui.list.RenamerFileList;
import com.dong.matko.toolbox.renamer.gui.option.format.FormatPane;
import com.dong.matko.toolbox.renamer.gui.option.increment.IncrementPane;
import com.dong.matko.toolbox.renamer.gui.option.output.OutputPane;
import com.dong.matko.toolbox.renamer.gui.option.regexp.RegexpPane;
import com.dong.matko.toolbox.renamer.gui.option.replace.ReplacePane;
import com.dong.matko.toolbox.renamer.ui.Command;
import com.dong.matko.toolbox.renamer.ui.RenameMediator;

public class TabRenamer extends JPanel implements ActionListener, CaretListener, ChangeListener{
	private static final long serialVersionUID = 1L;
	
	public TabRenamer(JTabbedPane frameT, JFrame frame){
		
		RenameMediator mediator = new RenameMediator(frame);

		MenuPane menu = new MenuPane(this, mediator);
		
		// --- File list
		RenamerFileList list = new RenamerFileList(this, mediator);

		// --- Options
		FormatPane format = new FormatPane(this, mediator);
		RegexpPane regex = new RegexpPane(this, mediator);
		IncrementPane increment = new IncrementPane(this, mediator);
		ReplacePane replace = new ReplacePane(this, mediator);
		OutputPane output = new OutputPane(this, mediator);
		
		// --- Layout
		JPanel jpl = new JPanel(new MigLayout("", "[]10[grow]"));
		JPanel center = new JPanel(new MigLayout("", "[]", "10[]0[]0[][]5"));
		center.add(format, 		"growx, wrap");
		center.add(increment, 	"growx, split");
		center.add(replace, 	"growx, aligny top, wrap");
		center.add(output, 		"growx, split, sg 1");
		center.add(regex, 		"growx, wrap, sg 1");
		center.add(list, 		"growx, wrap");
		jpl.add(center, 	"");
		jpl.add(menu, 	"east");
		TabHelper.addTab(frameT, "RENAMER", jpl);
	}

	public void actionPerformed(ActionEvent ae) {
		launchCommand((Command) ae.getSource());
    }

	public void caretUpdate(CaretEvent ce) {
		launchCommand((Command) ce.getSource());
	}

	public void stateChanged(ChangeEvent ce) {
		launchCommand((Command) ce.getSource());
	}
	
	private void launchCommand(Command c) {
		c.execute();
	}
}
