package csv;

import java.io.FileReader;

import com.opencsv.CSVReader;

public class CsvMilu_2D_Array {
	
	public static void readRow(String filePath) throws Throwable {

		FileReader file = new FileReader(filePath);
		CSVReader csvReader = new CSVReader(file);
		String[][] nextRecord;
		
		nextRecord = csvReader.readAll();
	}

	public static void main(String[] args) throws Throwable {
		
	}
	
}
