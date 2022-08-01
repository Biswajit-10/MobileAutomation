package csv.csvExp;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class CsvParser2 {

	public static void main(String[] args) throws Exception, IOException {
		String[][] a = readCSV("./data/data.csv");
		System.out.println(a[0][0]);
		System.out.println(a[0][1]);
		System.out.println(a[0][2]);
		System.out.println(a[0][3]);
		System.out.println(a[1][0]);
		System.out.println(a[1][1]);
		System.out.println(a[1][2]);
		System.out.println(a[1][3]);
	}
	public static String[][] readCSV(String path) throws FileNotFoundException, IOException {
	    try (FileReader fr = new FileReader(path);
	            BufferedReader br = new BufferedReader(fr)) {
	        Collection<String[]> lines = new ArrayList<>();
	        for (String line = br.readLine(); line != null; line = br.readLine()) {
	            lines.add(line.split(","));
	        }
	        return lines.toArray(new String[lines.size()][]);
	    }
	}
	
}
