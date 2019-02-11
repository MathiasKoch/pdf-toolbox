package com.dong.matko.toolbox.unlocker;

import java.awt.Dimension;

import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import net.miginfocom.swing.MigLayout;

import com.dong.matko.toolbox.MainWindow;
import com.dong.matko.toolbox.renamer.gui.drop.FileDrop;
import com.dong.matko.toolbox.renamer.gui.list.model.ZebraJTable;
import com.dong.matko.toolbox.renamer.gui.option.OptionPane;




public class TabUnlocker  {
	public static JCheckBox deleteButton;
	public static DefaultTableModel model = new DefaultTableModel(); 
	public static JTable table;

	public static JPanel initUI() {

		JPanel jpl = new JPanel(new MigLayout("", "10[grow]10[grow]", "10[]10[grow]5"));

		OptionPane opt = new OptionPane(new MigLayout("", ""), "title.options");
		OptionPane drop = new OptionPane(new MigLayout("", "[grow]"), "title.unlock");
		MenuPaneUnlock menu = new MenuPaneUnlock();

		table = new ZebraJTable(model);
		table.setIntercellSpacing(new Dimension(0, 1));
		table.setColumnSelectionAllowed(false);
		table.setShowVerticalLines(false);
		table.setShowHorizontalLines(false); 
		model.addColumn("Document name"); 
		model.addColumn("Protection status"); 
		
		new FileDrop(TabUnlocker.table, false, new FileDrop.Listener() {
			public void filesDropped(java.io.File[] files) {
				MainWindow.showModal("Loading..");
				for(int i = TabUnlocker.model.getRowCount() - 1; i >= 0; i--){
					TabUnlocker.model.removeRow(i);
				}
				MenuPaneUnlock.filesList.clear();
				MenuPaneUnlock.showFiles(files);
				MainWindow.hideModal();
				MenuPaneUnlock.runButton.setEnabled(true);
			}
		});

		JTableHeader header = table.getTableHeader();
		header.setReorderingAllowed(false);
		header.setResizingAllowed(true);

		deleteButton = new JCheckBox("Delete old, locked files");
		Border empty3 = new EmptyBorder(0, 0, 0, 25);
		deleteButton.setBorder(empty3);

		JScrollPane pane = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		pane.setWheelScrollingEnabled(true);
		
		opt.add(deleteButton);
		drop.add(pane, "grow");
		jpl.add(opt, "wrap, grow");
		jpl.add(drop, "gaptop 10px, grow");
		jpl.add(menu, "east");

		return jpl;
	}
	
}