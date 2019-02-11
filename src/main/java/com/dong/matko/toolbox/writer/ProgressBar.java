package com.dong.matko.toolbox.writer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JTextArea;

public class ProgressBar {
	static String regex = "(\\[[=>]*\\s*?]\\s*?.*%\\s*?Dir:\\s*?\\d*?/\\d*?\\s*?File:\\s*?\\d*?/\\d*)";
	static String text, text2 = "";
	static public void print(int percent, int curFolder, int nrFolder, int curFile, int nrFile, JTextArea log){
		text = log.getText();
		StringBuilder bar = new StringBuilder("[");
		for(int i = 0; i < 50; i++){
			if( i < (percent/2)){
				bar.append("=");
			}else if( i == (percent/2)){
				bar.append(">");
			}else{
				bar.append("  ");
			}
		}
		bar.append("]   " + percent + "%      Dir: " + curFolder + "/" + nrFolder + "   File: " + curFile + "/" + nrFile);
		Matcher m = Pattern.compile(regex).matcher(text);
		if(m.find()){
			text2 = text.replace(m.group(1), bar.toString());
			log.setText(text2);
			log.setCaretPosition(log.getDocument().getLength());
		}else{
			log.append(bar.toString());
		}
		
	}
}