package csv;

import java.io.FileReader;

import com.opencsv.CSVReader;

public class CsvMilu {

	@SuppressWarnings("resource")
	public static void readRow(String filePath) throws Throwable {

		FileReader file = new FileReader(filePath);
		CSVReader csvReader = new CSVReader(file);
		String[] nextRecord;
		
		nextRecord = csvReader.readNext();
		System.out.println(nextRecord.length);
		System.out.println(nextRecord[0]);
	}

	public static void main(String[] args) throws Throwable {
		readRow("./data/OneRow.csv");
		
/*		 1
		 1	2	3	4	5	6	7	8	9	10		 */
		readRow("./data/OneRow1.csv");
		
/*		   10
		   1      */
	}

}
/*
1.Read entire file and store it in array

2.How to make a csv file 
	ans:- write on a workbook and save as -> .csv file then it automatically add comma

	don't- if you rename a excel sheet there will be no comma and it treated as one String 

3.2D arrayList
		ArrayList[][] table = new ArrayList[10][10];
		table[0][0] = new ArrayList(); // add another ArrayList object to [0,0]
		table[0][0].add(); // add object to that ArrayList

4.
		

*/