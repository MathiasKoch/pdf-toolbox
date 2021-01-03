package com.dong.matko.toolbox.writer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.io.FilenameUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ReadExcel {
	public static ArrayList<WriterFile> fillTableExcel(String path, String dir)
			throws Exception, FileNotFoundException, IOException {
		ArrayList<WriterFile> arr = new ArrayList<WriterFile>();
		Workbook workbook = WorkbookFactory.create(new FileInputStream(path));
		Sheet sheet = workbook.getSheetAt(0);
		if (dir != null) {
			File[] files = new File(dir).listFiles();
			int rowStart = sheet.getFirstRowNum() + 14;
			int rowEnd = sheet.getLastRowNum();
			for (int rowNum = rowStart; rowNum <= rowEnd; rowNum++) {
				for (File file : files) {
					if (!file.getAbsolutePath().toLowerCase().equals(path.toLowerCase())) {

						String relPath = helpers.getRelativeFile(new File(file.getCanonicalPath()), new File(path)).toString();
						Cell cell = sheet.getRow(rowNum).getCell(2, Row.RETURN_BLANK_AS_NULL);
						String fileName = getCellString(cell);

						// Make sure it works in all operating systems
						fileName = fileName.replace("\\", File.separator);
						fileName = fileName.replace("/", File.separator);
						if (!fileName.startsWith("..")) {
							relPath = relPath.replace(".." + File.separator, "");
						}
						if (fileName.toLowerCase().contains(relPath.replace("%20", " ").toLowerCase())) {
							File actualFile = new File(file.getAbsolutePath() + fileName.replace(relPath, ""));
							if (actualFile.exists()) {
								WriterFile f = new WriterFile();
								f.setFile(actualFile);
								f.setLocation(getCellString(sheet.getRow(6).getCell(2)));
								f.setSystem(getCellString(sheet.getRow(2).getCell(6)));
								f.setBlock(getCellString(sheet.getRow(rowNum).getCell(3, Row.RETURN_BLANK_AS_NULL)));
								f.setDrawingText(getCellString(sheet.getRow(rowNum).getCell(11, Row.RETURN_BLANK_AS_NULL)), 1);
								f.setDrawingText(getCellString(sheet.getRow(rowNum).getCell(12, Row.RETURN_BLANK_AS_NULL)), 2);
								f.setDrawingText(getCellString(sheet.getRow(rowNum).getCell(13, Row.RETURN_BLANK_AS_NULL)), 3);
								f.setF0(getCellString(sheet.getRow(rowNum).getCell(4, Row.RETURN_BLANK_AS_NULL)));
								f.setRDSPP(getCellString(sheet.getRow(rowNum).getCell(5, Row.RETURN_BLANK_AS_NULL)));
								f.setRev(getCellString(sheet.getRow(rowNum).getCell(6, Row.RETURN_BLANK_AS_NULL)));
								f.setSheet(getCellString(sheet.getRow(rowNum).getCell(9, Row.RETURN_BLANK_AS_NULL)));
								arr.add(f);
							}
						}
					}
				}
			}
		}
		return arr;
	}

	public static ArrayList<WriterFile> getExcelDrawingTexts(String path)
			throws Exception, FileNotFoundException, IOException {
		ArrayList<WriterFile> arr = new ArrayList<WriterFile>();
		Workbook workbook = WorkbookFactory.create(new FileInputStream(path));
		Sheet sheet = workbook.getSheetAt(0);
		int rowStart = sheet.getFirstRowNum() + 14;
		int rowEnd = sheet.getLastRowNum();
		for (int rowNum = rowStart; rowNum <= rowEnd; rowNum++) {
			Cell cell = sheet.getRow(rowNum).getCell(2, Row.RETURN_BLANK_AS_NULL);
			String fileName = getCellString(cell);
			WriterFile f = new WriterFile();
			f.setFileName(FilenameUtils.getName(fileName));
			f.setLocation(getCellString(sheet.getRow(6).getCell(2)));
			f.setSystem(getCellString(sheet.getRow(2).getCell(6)));
			f.setBlock(getCellString(sheet.getRow(rowNum).getCell(3, Row.RETURN_BLANK_AS_NULL)));
			f.setDrawingText(getCellString(sheet.getRow(rowNum).getCell(11, Row.RETURN_BLANK_AS_NULL)), 1);
			f.setDrawingText(getCellString(sheet.getRow(rowNum).getCell(12, Row.RETURN_BLANK_AS_NULL)), 2);
			f.setDrawingText(getCellString(sheet.getRow(rowNum).getCell(13, Row.RETURN_BLANK_AS_NULL)), 3);
			f.setF0(getCellString(sheet.getRow(rowNum).getCell(4, Row.RETURN_BLANK_AS_NULL)));
			f.setRDSPP(getCellString(sheet.getRow(rowNum).getCell(5, Row.RETURN_BLANK_AS_NULL)));
			f.setRev(getCellString(sheet.getRow(rowNum).getCell(6, Row.RETURN_BLANK_AS_NULL)));
			f.setSheet(getCellString(sheet.getRow(rowNum).getCell(9, Row.RETURN_BLANK_AS_NULL)));
			arr.add(f);
			// arr.add(new RenameFileExcel(FilenameUtils.getName(fileName),
			// getCellString(sheet.getRow(rowNum).getCell(13, Row.RETURN_BLANK_AS_NULL))));
		}
		return arr;
	}

	private static String getCellString(Cell c) {
		if (c != null) {
			if (c.getCellType() == Cell.CELL_TYPE_STRING) {
				return c.getStringCellValue();
			} else if (c.getCellType() == Cell.CELL_TYPE_NUMERIC) {
				return String.valueOf((int) c.getNumericCellValue());
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	public static String readxlsOnce(String path) throws Exception, FileNotFoundException, IOException {
		Workbook workbook = WorkbookFactory.create(new FileInputStream(path));
		Sheet sheet = workbook.getSheetAt(0);
		Cell cell = sheet.getRow(2).getCell(6);
		if (cell != null) {
			switch (cell.getCellType()) {
			case Cell.CELL_TYPE_STRING:
				return cell.getStringCellValue();
			case Cell.CELL_TYPE_BLANK:
				break;
			case Cell.CELL_TYPE_ERROR:
				System.out.println(cell.getErrorCellValue());
				break;
			default:
				break;
			}
		}
		return null;
	}
}
