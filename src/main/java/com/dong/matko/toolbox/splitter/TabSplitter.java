package com.dong.matko.toolbox.splitter;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.JTableHeader;

import net.miginfocom.swing.MigLayout;

import com.dong.matko.toolbox.Resources;
import com.dong.matko.toolbox.renamer.gui.list.model.ZebraJTable;
import com.dong.matko.toolbox.renamer.gui.option.OptionPane;

public class TabSplitter  {
	private static JTable table;
	public static JRadioButton bookmarkButton, nrpagesButton, npageButton;
	public static JCheckBox unlock;
	public static int curPane = -1;
	public static OptionPane drop;
	public static JPanel midPanel, midPanel2;
	public static JScrollPane pane;
	public static JTextField npage, commapage;

	public static JPanel initUI() {
		JPanel jpl = new JPanel(new MigLayout("", "10[][grow]10[grow]", "10[]10[grow]5"));

		MenuPaneSplit menu = new MenuPaneSplit();
		
		
		table = new ZebraJTable(MenuPaneSplit.model);
		table.setIntercellSpacing(new Dimension(0, 1));
		table.setColumnSelectionAllowed(false);
		table.setShowVerticalLines(false);
		table.setShowHorizontalLines(false); 
		  
		npage = new JTextField();
		npage.setPreferredSize(new Dimension(40,25));
		npage.addKeyListener(new KeyAdapter() 
		{
			public void keyTyped(KeyEvent ke) 
			{
				if(!Character.isDigit(ke.getKeyChar()))
				{
					ke.consume();
				}
			} 
			public void keyReleased(KeyEvent e){
				MenuPaneSplit.runButton.setEnabled(MenuPaneSplit.pdfFile != null && !MenuPaneSplit.pdfFile.isEmpty() && !npage.getText().isEmpty());
			} 
			public void keyPressed(KeyEvent e){} 
		});

		
		commapage = new JTextField();
		commapage.setPreferredSize(new Dimension(40,25));
		commapage.addKeyListener(new KeyAdapter() 
		{ 
			public void keyTyped(KeyEvent ke) 
			{ 
				char c = ke.getKeyChar(); 
				if(!Character.isDigit(c) && Character.compare(c, (",").charAt(0)) != 0)
				{ 
					ke.consume(); 
				}
			} 
			public void keyReleased(KeyEvent e){
				MenuPaneSplit.runButton.setEnabled(MenuPaneSplit.pdfFile != null && !MenuPaneSplit.pdfFile.isEmpty() && !commapage.getText().isEmpty());
			} 
			public void keyPressed(KeyEvent e){} 
		});

		unlock = new JCheckBox(Resources.get("button.splitter.unlock"));
		midPanel = new JPanel();
		midPanel2 = new JPanel();
		OptionPane type = new OptionPane(new MigLayout("", ""), "title.splitter.type");
		OptionPane opt = new OptionPane(new MigLayout("", ""), "title.options");
		drop = new OptionPane(new MigLayout("", "[grow]"), "title.unlock");
		
		JTableHeader header = table.getTableHeader();
		header.setReorderingAllowed(false);
		header.setResizingAllowed(true);

		midPanel.add(npage);
		midPanel2.add(commapage);
		
		pane = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		pane.setWheelScrollingEnabled(true);
		
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
	
		curPane = 0;

		bookmarkButton = new JRadioButton(Resources.get("button.splitter.bookmark"));
		bookmarkButton.setSelected(true);
		npageButton = new JRadioButton(Resources.get("button.splitter.npage"));
		nrpagesButton = new JRadioButton(Resources.get("button.splitter.nrpage"));

		ButtonGroup group = new ButtonGroup();
		group.add(bookmarkButton);
		group.add(npageButton);
		group.add(nrpagesButton);

		bookmarkButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				drop.remove(midPanel);
				drop.remove(midPanel2);
				drop.add(pane, "grow");
				drop.setBorder(Resources.createTitledSeparator("title.splitter.bookmark"));
				curPane = 0;
				drop.revalidate();
				drop.repaint();
			}
		});
		nrpagesButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				drop.remove(pane);
				drop.remove(midPanel);
				drop.add(midPanel2);
				drop.setBorder(Resources.createTitledSeparator("title.splitter.nrpage"));
				curPane = 2;
				drop.revalidate();
				drop.repaint();
			}
		});
		npageButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				drop.remove(pane);
				drop.remove(midPanel2);
				drop.add(midPanel);
				drop.setBorder(Resources.createTitledSeparator("title.splitter.npage"));
				curPane = 1;
				drop.revalidate();
				drop.repaint();
			}
		});
		
		type.add(bookmarkButton, "wrap");
		type.add(npageButton, "wrap");
		type.add(nrpagesButton, "wrap");
		
		opt.add(unlock);
		
		drop.add(pane, "grow");
		
		jpl.add(type, "grow, push");
		jpl.add(opt, "wrap, grow");
		jpl.add(drop, "span 2, grow");
		jpl.add(menu, "east");
		
		return jpl;
	}
}
