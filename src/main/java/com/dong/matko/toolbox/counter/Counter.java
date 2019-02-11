package com.dong.matko.toolbox.counter;

import java.io.File;

public class Counter {
	private static int count = 0;

	public static int CountFiles(File[] files, String ending, String search, boolean recursive){
		count = 0;
		if(!ending.startsWith("."))
			ending = "." + ending;
		showFiles(files, ending, search, recursive);
		return count;
	}

	public static void showFiles(File[] files, String ending, String search, boolean recursive) {
		if(files != null){
			for (File file : files) {
				if (file.isDirectory()) {
					if(recursive)
						showFiles(file.listFiles(), ending, search, recursive);
				} else {
					if(!search.isEmpty()){
						if(file.getParent().toLowerCase().contains(search)){
							if(file.getName().toLowerCase().endsWith(ending)){
								count++;
							}
						}
					}else{
						if(file.getName().toLowerCase().endsWith(ending)){
							count++;
						}
					}
				}       
			}
		}
	}
}