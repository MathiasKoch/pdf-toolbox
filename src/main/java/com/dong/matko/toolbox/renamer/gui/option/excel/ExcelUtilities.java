package com.dong.matko.toolbox.renamer.gui.option.excel;

import java.util.ArrayList;

import com.dong.matko.toolbox.writer.WriterFile;


public class ExcelUtilities {

	public static String getDrawingText(ArrayList<WriterFile> tecdocArray, String name, Integer textNumber){
    for(WriterFile f : tecdocArray) {
      if (f.getFileName().toLowerCase().equals(name.toLowerCase())) {
        return f.getDrawingText(textNumber);
      }
    }
    return "";
  }

  public static String getBlock(ArrayList<WriterFile> tecdocArray, String name){
    for(WriterFile f : tecdocArray) {
      if (f.getFileName().toLowerCase().equals(name.toLowerCase())) {
        return f.getBlock();
      }
    }
    return "";
  }

  public static String getLocation(ArrayList<WriterFile> tecdocArray, String name){
    for(WriterFile f : tecdocArray) {
      if (f.getFileName().toLowerCase().equals(name.toLowerCase())) {
        return f.getLocation();
      }
    }
    return "";
  }

  public static String getF0(ArrayList<WriterFile> tecdocArray, String name){
    for(WriterFile f : tecdocArray) {
      if (f.getFileName().toLowerCase().equals(name.toLowerCase())) {
        return f.getF0();
      }
    }
    return "";
  }

  public static String getRDSPP(ArrayList<WriterFile> tecdocArray, String name){
    for(WriterFile f : tecdocArray) {
      if (f.getFileName().toLowerCase().equals(name.toLowerCase())) {
        return f.getRDSPP(false, false, false);
      }
    }
    return "";
  }
}
