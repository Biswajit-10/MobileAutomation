package excel;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * make on csv file, saving and reading again we are facing issue
 * @author Biswajit
 *
 */

public class Read {

	public WebDriver driver;
	public Properties prop;

	public static Workbook book;
	public static Sheet sheet;

	public Base base;
	public WebElement element;

	public static final String SCENARIO_SHEET_PATH = "./scenarios.xlsx";
	
	public static void main(String[] args) {
		startExecution("tc_01");
	}
	
	public static void startExecution(String sheetName) {

//		FileInputStream file = null;
//		try {
//			file = new FileInputStream(SCENARIO_SHEET_PATH);
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		}
//
//		try {
//			book = WorkbookFactory.create(file);
//		}  catch (IOException e) {
//			e.printStackTrace();
//		}
//
//		sheet = book.getSheet(sheetName);
		
		work(sheetName);
		
		int k = 0;
		
		for (int i = 0; i < sheet.getLastRowNum(); i++) {
			try {
//		stepno	action	data	runStatus

				String stepno = sheet.getRow(i + 1).getCell(k ).toString().trim();
				String action = sheet.getRow(i + 1).getCell(k + 1).toString().trim();
				String data = sheet.getRow(i + 1).getCell(k + 2).toString().trim();
				String runStatus = sheet.getRow(i + 1).getCell(k + 3).toString().trim();	//[yes no]
				String value=null;
				
				switch (runStatus) {
				case "yes":
					System.out.println(data);
					
					break;
				case "no":
					Thread.sleep(20000);
					work(sheetName);
					System.out.println(data);
					
					break;
			
				default:
					break;
				}

			}catch (Exception e) {
				// TODO: handle exception
			}
		}
	}

	public static void work(String sheetName) {
		FileInputStream file = null;
		try {
			file = new FileInputStream(SCENARIO_SHEET_PATH);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		try {
			book = WorkbookFactory.create(file);
		}  catch (IOException e) {
			e.printStackTrace();
		}

		sheet = book.getSheet(sheetName);

	}

}