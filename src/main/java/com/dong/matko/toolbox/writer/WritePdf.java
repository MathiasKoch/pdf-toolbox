package com.dong.matko.toolbox.writer;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.PdfWriter;

public class WritePdf {

	static public void checkPositions(WriterFile f, ArrayList<Position> p){
		f.setWritePosList(p);
		f.setReadyState(true);
		f.setWritePos(p.get(0));
	}

	@SuppressWarnings("unused")
	static public boolean write(WriterFile f, String dirIndex, Boolean red){

		float pagePadding = 10;
		float textSize = 10;
		double factor = 1.33;

		PdfReader reader;
		File newOnes = new File(f.getFile().getParent().replace(dirIndex, dirIndex + "_new"));
		File filename = new File(f.getFile().getName());
		newOnes.mkdirs();
		try {
			reader = new PdfReader(f.getFile().toString());
			PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(newOnes + File.separator + filename));
			BaseFont bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.WINANSI, BaseFont.NOT_EMBEDDED);
			Font font;
			if(red)
				font = new Font(bf, textSize, Font.NORMAL, new BaseColor(255, 0, 0));
			else
				font = new Font(bf, textSize, Font.NORMAL);

			Rectangle pageSize = reader.getPageSizeWithRotation(1);
			String rotation = "Portrait";
			if(pageSize.getWidth() > pageSize.getHeight())
				rotation = "Landscape";
			float width = bf.getWidthPoint(f.getRDSPP(true, TabWriter.revButton.isSelected(), TabWriter.sheetButton.isSelected()), 10);
			PdfPTable table = new PdfPTable(1);
			PdfPCell cell = new PdfPCell(new Paragraph(f.getRDSPP(true, TabWriter.revButton.isSelected(), TabWriter.sheetButton.isSelected()), font));
			cell.setPaddingRight(3);
			cell.setPaddingLeft(5);
			cell.setPaddingBottom((float)2.6);
			cell.setBorder(0);

			//cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			table.addCell(cell);
			String isoAsize = null;
			PdfContentByte over = stamper.getOverContent(1);
			ColumnText ct = new ColumnText(over);
			float pagePX = pageSize.getHeight();
			if(rotation.equals("Landscape"))
				pagePX = pageSize.getWidth();

			if(700 > pagePX)// smaller than A4
				isoAsize = "Smaller than A4 paper - Orientation: " + rotation;
			else if(900 > pagePX && pagePX >= 700) // A4
				isoAsize = "A4 paper - Orientation: " + rotation;
			else if(1300 > pagePX && pagePX >= 900) // A3
				isoAsize = "A3 paper - Orientation: " + rotation;
			else if(1800 > pagePX && pagePX >= 1300) // A2
				isoAsize = "A2 paper - Orientation: " + rotation;
			else if(2600 > pagePX && pagePX >= 1800) // A1
				isoAsize = "A1 paper - Orientation: " + rotation;
			else if(3500 > pagePX && pagePX >= 2600) // A0
				isoAsize = "A0 paper - Orientation: " + rotation;
			else // Larger then A0
				isoAsize = "Larger than A0 paper - Orientation: " + rotation;

			if(f.getWritePos().getVertical().equals(TabWriter.V.TOP)){
				if(f.getWritePos().getHorizontal().equals(TabWriter.H.RIGHT))			// TOP RIGHT
					ct.setSimpleColumn((float)pageSize.getWidth() - pagePadding - (float)Math.round(width*factor), (float)pageSize.getHeight() - (pagePadding + 5 + textSize), (float)pageSize.getWidth() - pagePadding, (float)pageSize.getHeight() - pagePadding, 0, Element.ALIGN_RIGHT);
				else if(f.getWritePos().getHorizontal().equals(TabWriter.H.LEFT))		// TOP LEFT
					ct.setSimpleColumn(pagePadding, (float)pageSize.getHeight() - (pagePadding + 5 + textSize), (float)Math.round(width*factor) + pagePadding, (float)pageSize.getHeight() - pagePadding, 0, Element.ALIGN_LEFT);
				else if(f.getWritePos().getHorizontal().equals(TabWriter.H.CENTER))		// TOP CENTER
					ct.setSimpleColumn((float)pageSize.getWidth() / 2 - (float)Math.round(width*factor) / 2, (float)pageSize.getHeight() - (pagePadding + 5 + textSize), (float)pageSize.getWidth() / 2 + (float)Math.round(width*factor) / 2, (float)pageSize.getHeight() - pagePadding, 0, Element.ALIGN_CENTER);
			}else if(f.getWritePos().getVertical().equals(TabWriter.V.BOTTOM)){
				if(f.getWritePos().getHorizontal().equals(TabWriter.H.RIGHT))			// BOTTOM RIGHT
					ct.setSimpleColumn((float)pageSize.getWidth() - pagePadding - (float)Math.round(width*factor), pagePadding, (float)pageSize.getWidth() - pagePadding, (pagePadding + 5 + textSize), 0, Element.ALIGN_RIGHT);
				else if(f.getWritePos().getHorizontal().equals(TabWriter.H.CENTER))		// BOTTOM CENTER
					ct.setSimpleColumn((float)pageSize.getWidth() / 2 - (float)Math.round(width*factor) / 2, pagePadding, (float)pageSize.getWidth() / 2 + (float)Math.round(width*factor) / 2, (pagePadding + 5 + textSize), 0, Element.ALIGN_CENTER);
				else if(f.getWritePos().getHorizontal().equals(TabWriter.H.LEFT))		// BOTTOM LEFT
					ct.setSimpleColumn(pagePadding, pagePadding, (float)Math.round(width*factor) + pagePadding, (pagePadding + 5 + textSize), 0, Element.ALIGN_LEFT);
			}
			ct.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);
			ct.addElement(table);
			ct.go();

			stamper.close();
			reader.close();
			return true;
		} catch (IOException | DocumentException e) {
			e.printStackTrace();
			return false;
		}
	}
}
