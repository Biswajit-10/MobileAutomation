package excel.excelReadSpeedCheck;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class Speed {
	static Workbook book;
	static Sheet sheet ;
	static short cellNum;
	FileInputStream file;
	
	public static void load() throws Throwable {
		FileInputStream file = null;
		try {
			file = new FileInputStream("./data/abcd.xlsx");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		book = WorkbookFactory.create(file);
		sheet = book.getSheetAt(0);
		short cellNum = sheet.getRow(1).getLastCellNum();
	}
	
	public static void read() throws Throwable {
		load();
		String[][] array = null;
		
		for (int i = 1; i < sheet.getLastRowNum(); i++) {
			for (int j = 1; j < cellNum; j++) {
				
				if(i!=6)
				System.out.println(sheet.getRow(i).getCell(j).getStringCellValue().toString());
				else {
					load();
					System.out.println(sheet.getRow(i).getCell(j).getStringCellValue().toString());
				}
			}
		}
	}

	public static void main(String[] args) throws Throwable {
		read();
	}

}
