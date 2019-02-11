package com.dong.matko.toolbox;
import java.awt.Component;
import java.awt.Insets;

import javax.swing.JTabbedPane;
import javax.swing.UIManager;


public class TabHelper {

	public static void addTab(JTabbedPane tabPane, String text, Component comp){
	    int tabPlacement = tabPane.getTabPlacement();
	    switch(tabPlacement){
	        case JTabbedPane.LEFT:
	        case JTabbedPane.RIGHT:
	            tabPane.addTab(null, new VerticalTextIcon(text, tabPlacement==JTabbedPane.RIGHT), comp);
	            return;
	        default:
	            tabPane.addTab(text, null, comp);
	    }
	}

	public static JTabbedPane createTabbedPane(int tabPlacement){
	    switch(tabPlacement){
	        case JTabbedPane.LEFT:
	        case JTabbedPane.RIGHT:
	            Object textIconGap = UIManager.get("TabbedPane.textIconGap");
	            Insets tabInsets = UIManager.getInsets("TabbedPane.tabInsets");
	            UIManager.put("TabbedPane.textIconGap", 1);
	            UIManager.put("TabbedPane.tabInsets", new Insets(tabInsets.left, tabInsets.top, tabInsets.right, tabInsets.bottom));
	            JTabbedPane tabPane = new JTabbedPane(tabPlacement);
	            UIManager.put("TabbedPane.textIconGap", textIconGap);
	            UIManager.put("TabbedPane.tabInsets", tabInsets);
	            return tabPane;
	        default:
	            return new JTabbedPane(tabPlacement);
	    }
	}
}
