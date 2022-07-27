package csv.debug;

import java.io.FileReader;

import com.opencsv.CSVReader;

public class Debug {

	public static void main(String[] args) throws Throwable {
		readRow("./data/OneRow1.csv");
	}

	@SuppressWarnings("resource")
	public static void readRow(String filePath) throws Throwable {

		FileReader file = new FileReader(filePath);
		CSVReader csvReader = new CSVReader(file);
		String[] nextRecord;

		nextRecord = csvReader.readNext();

		for (int j = 0; j < nextRecord.length; j++) {
			if (j != 4)
				System.out.println(nextRecord[j]);
			else {
				file = new FileReader(filePath);
				csvReader = new CSVReader(file);
				nextRecord = csvReader.readNext();
				System.out.println(nextRecord[j]);
			}
		}
	}
}
