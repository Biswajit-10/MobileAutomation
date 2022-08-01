package csv.csvExp;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.util.ArrayList;

public class CsvParser1 {

	public static void main(String[] args) throws Exception {
		String fName = "./data/data.csv";
		String thisLine;
		FileInputStream fis = new FileInputStream(fName);
		DataInputStream myInput = new DataInputStream(fis);
		ArrayList<String[]> strar = new ArrayList<String[]>();

		while ((thisLine = myInput.readLine()) != null) {
		    strar.add(thisLine.split(","));
		}
		System.out.println(strar.get(0)[0]);
		System.out.println(strar.get(0)[1]);
		System.out.println(strar.get(0)[2]);
		System.out.println(strar.get(0)[3]);
		
		System.out.println(strar.get(1)[0]);
		System.out.println(strar.get(1)[1]);
		System.out.println(strar.get(1)[2]);
		System.out.println(strar.get(1)[3]);
		
	}
}
