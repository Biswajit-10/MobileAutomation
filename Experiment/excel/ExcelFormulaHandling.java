package excel;

import java.io.File;
import java.io.FileInputStream;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ExcelFormulaHandling {
	public static void main(String[] args) throws Exception {
		excel();
	}
	private static void excel() throws Exception {
        // Open the Excel file
        FileInputStream inputStream = new FileInputStream(new File("Test Data.xlsx"));

        // Create a Workbook object from the Excel file
        Workbook workbook = WorkbookFactory.create(inputStream);

        // Get the first sheet from the Workbook
        Sheet sheet = workbook.getSheetAt(1);

        DataFormatter dataFormatter = new DataFormatter();
        // Create a FormulaEvaluator to handle all the formulas in the sheet
        FormulaEvaluator formulaEvaluator = workbook.getCreationHelper().createFormulaEvaluator();

        // Loop through all the rows in the sheet
        for (Row row : sheet) {
            // Loop through all the cells in the row
            for (Cell cell : row) {
                // Get the value of the cell
                switch (cell.getCellType()) {
                    case STRING:
                        System.out.print(cell.getStringCellValue() + "\t");
                        break;
                    case NUMERIC:
                    	System.out.print(dataFormatter.formatCellValue(cell) + "\t");
//                        System.out.print(cell.getNumericCellValue() + "\t");
                        break;
                    case BOOLEAN:
                        System.out.print(cell.getBooleanCellValue() + "\t");
                        break;
                    case FORMULA:
                        // Evaluate the formula and print the result
 /*     
                    	formulaEvaluator.evaluateFormulaCell(cell);
                        switch (cell.getCachedFormulaResultType()) {
                            case STRING:
                                System.out.print(cell.getStringCellValue() + "\t");
                                break;
                            case NUMERIC:
                            	System.out.print(dataFormatter.formatCellValue(cell) + "\t");
                                System.out.print(cell.getNumericCellValue() + "\t");
                                break;
                            case BOOLEAN:
                                System.out.print(cell.getBooleanCellValue() + "\t");
                                break;
  */                            
                    	CellType cellType;
                    	formulaEvaluator.evaluateInCell(cell);
                    	 cellType = cell.getCellType();
                    	 if (cellType == CellType.NUMERIC) {
                    		 System.out.print(dataFormatter.formatCellValue(cell) + "\t");
                         } else {
                        	 String Value = dataFormatter.formatCellValue(cell);
 	                        System.out.print(Value + "\t");
                         }
                        break;
                    default:
                        System.out.print("\t");
                }
            }
            System.out.println();
        }

        // Close the Workbook and input stream
        workbook.close();
        inputStream.close();
    
	}
}
