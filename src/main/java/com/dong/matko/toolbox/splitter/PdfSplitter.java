package com.dong.matko.toolbox.splitter;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfDestination;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfOutline;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.RandomAccessFileOrArray;


public class PdfSplitter {
	public static void splitpdf(String pdfFile, OutputStream outputStream, int fromPage, int toPage, ArrayList<Integer> pageBook, ArrayList<String> nameBook, ArrayList<Integer> levelBook) {
		Document document = new Document();
		try {
			@SuppressWarnings("deprecation")
			PdfReader inputPDF = new PdfReader(new RandomAccessFileOrArray(pdfFile),null);
			int totalPages = inputPDF.getNumberOfPages();
			if(fromPage > toPage ) {
				fromPage = toPage;
			}
			if(toPage > totalPages) {
				toPage = totalPages;
			}
			PdfWriter writer = PdfWriter.getInstance(document, outputStream);
			document.open();
			PdfOutline root = writer.getRootOutline();
			PdfOutline mark, mark2, mark3, mark4, mark5;
			mark = mark2 = mark3 = mark4 = mark5 = null;
			PdfContentByte cb = writer.getDirectContent();
			PdfImportedPage page;
			int rootlevel = 0;
			while(fromPage <= toPage) {
				if(fromPage > 0){
					document.newPage();
					page = writer.getImportedPage(inputPDF, fromPage);
					if(pageBook!=null){
						for(int i = 0; i < pageBook.size(); i++){
							if(pageBook.get(i) == fromPage){
								if(i == 0){
									mark = new PdfOutline(root, new PdfDestination(fromPage), nameBook.get(i));
									rootlevel = levelBook.get(i);
								}else{
									if(levelBook.get(i) == rootlevel){
										mark = new PdfOutline(root, new PdfDestination(fromPage), nameBook.get(i));
									}else if(levelBook.get(i) == rootlevel + 1){
										mark2 = new PdfOutline(mark, new PdfDestination(fromPage), nameBook.get(i));
									}else if(levelBook.get(i) == rootlevel + 2){
										mark3 = new PdfOutline(mark2, new PdfDestination(fromPage), nameBook.get(i));
									}else if(levelBook.get(i) == rootlevel + 3){
										mark4 = new PdfOutline(mark3, new PdfDestination(fromPage), nameBook.get(i));
									}else if(levelBook.get(i) == rootlevel + 4){
										mark5 = new PdfOutline(mark4, new PdfDestination(fromPage), nameBook.get(i));
									}else if(levelBook.get(i) == rootlevel + 5){
										new PdfOutline(mark5, new PdfDestination(fromPage), nameBook.get(i));
									}else{
										new PdfOutline(root, new PdfDestination(fromPage), nameBook.get(i));
									}
								}
							}
						}
					}
					cb.addTemplate(page, 0, 0);
					fromPage++;
				}
			}
			outputStream.flush();
			document.close();
			outputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

			try {
				if (outputStream != null)
					outputStream.close();
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
		}
	}
}