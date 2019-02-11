// package com.dong.matko.toolbox.writer;

// import java.io.FileInputStream;
// import java.io.InputStream;
// import java.util.List;

// import org.apache.poi.openxml4j.opc.OPCPackage;
// import org.apache.poi.xwpf.usermodel.XWPFDocument;
// import org.apache.poi.xwpf.usermodel.XWPFHeader;
// import org.apache.poi.xwpf.usermodel.XWPFParagraph;

// public class WriteWord {

// 	public static void write(){
// 		InputStream fs = null;
// 		XWPFHeader head = null;

// 		try {
// 			fs = new FileInputStream("C:\\Users\\matko\\Downloads\\Quality Management System project Idea.docm");
// 			XWPFDocument hdoc=new XWPFDocument(OPCPackage.open(fs));
// 			head = hdoc.getHeaderFooterPolicy().getDefaultHeader();
// 			String s = head.getText();
// 			String start = s.substring(0, s.indexOf("\t"));
// 			String center =  s.substring(s.indexOf("\t") + 1, s.lastIndexOf("\t"));
// 			String end = s.substring(s.lastIndexOf("\t") + 1);
// 			if(head.getAllPictures().size() > 0){
// 				System.out.println("Got Pictures, minor problem!");
// 			}
// 			List<XWPFParagraph> listParagraph = head.getListParagraph();
// 			for (XWPFParagraph p : listParagraph) {
// 				if(end.isEmpty()){
// 					if(p.getCTP().sizeOfRArray() > 4){
// 						if(p.getCTP().getRArray(3).sizeOfTArray() > 0 &&
// 								p.getCTP().getRArray(3).getTArray(0) != null &&
// 								p.getCTP().getRArray(3).getTArray(0).getStringValue().isEmpty()){
// 							if(p.getCTP().getRArray(4).sizeOfTArray() > 0 &&
// 									p.getCTP().getRArray(4).getTArray(0) != null &&
// 									p.getCTP().getRArray(4).getTArray(0).getStringValue().isEmpty()){
// 								//Write End
// 								System.out.println("End");
// 							}else{
// 								// Array-part 4 not there or not empty
// 							}
// 						}else{
// 							// Array-part 3 not there or not empty
// 						}
// 					}else{
// 						// Something is Wrong!
// 					}
// 				}else if(start.isEmpty()){
// 					if(p.getCTP().sizeOfRArray() > 1){
// 						if(p.getCTP().getRArray(0).sizeOfTArray() == 0 ){
// 							System.out.println(p.getCTP().sizeOfRArray());
// 							if(p.getCTP().getRArray(1).sizeOfTArray() == 0){
// 								//Write Start
// 							}
// 						}
// 					}else{
// 						// Something is wrong!
// 					}
// 				}else if(center.isEmpty()){
// 					if(p.getCTP().sizeOfRArray() > 2){
// 						if(p.getCTP().getRArray(2).sizeOfTArray() > 0 &&
// 								p.getCTP().getRArray(2).getTArray(0) != null &&
// 								p.getCTP().getRArray(2).getTArray(0).getStringValue().isEmpty()){
// 							//Write Center
// 							System.out.println("Center");
// 						}else{
// 							// Array-part 2 not there or not empty
// 						}
// 					}else{
// 						// Something is wrong!
// 					}
// 				}else{
// 					// All filled
// 					System.out.println("Nope");
// 				}
// 			}

// 		} catch(Exception ex) {
// 			ex.printStackTrace();
// 		}
// 	}

// }
