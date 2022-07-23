package csv;

import java.io.FileReader;
import java.util.List;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

public class CsvReader {
	
	public static void main(String[] args) {
		
//		readDataLineByLine("./data/data.csv");
		readAllDataAtOnce("./data/data.csv");
	}
	
	
	
	// Java code to illustrate reading a CSV file line by line
	public static void readDataLineByLine(String file)
	{
	  
	    try {
	  
	        // Create an object of filereader
	        // class with CSV file as a parameter.
	        FileReader filereader = new FileReader(file);
	  
	        // create csvReader object passing
	        // file reader as a parameter
	        CSVReader csvReader = new CSVReader(filereader);
	        String[] nextRecord;
	  
	        // we are going to read data line by line
	        while ((nextRecord = csvReader.readNext()) != null) {
	            for (String cell : nextRecord) {
	                System.out.print(cell + "\t");
	            }
	            System.out.println();
	        }
	    }
	    catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
	// Java code to illustrate reading all data at once
	public static void readAllDataAtOnce(String file)
	{
	    try {
	        // Create an object of file reader
	        // class with CSV file as a parameter.
	        FileReader filereader = new FileReader(file);
	  
	        // create csvReader object and skip first Line
	        CSVReader csvReader = new CSVReaderBuilder(filereader)
	                                  .withSkipLines(1)
	                                  .build();
	        List<String[]> allData = csvReader.readAll();
	  
	        // print Data
	        for (String[] row : allData) {
	            for (String cell : row) {
	                System.out.print(cell + "\t");
	            }
	            System.out.println();
	        }
	    }
	    catch (Exception e) {
	        e.printStackTrace();
	    }
	}
}
