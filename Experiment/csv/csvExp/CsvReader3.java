package csv.csvExp;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class CsvReader3 {
	public static void main(String[] args) throws Exception {
		
		csvReaderSuccess();
		
	}
		
	public static String[][] csvReaderSuccess() throws Exception {
	String fileName = "data copy.csv";
	List<List<String>> list = new ArrayList<List<String>>();
	BufferedReader br = new BufferedReader(new FileReader(fileName));
	String line = "";
	while ((line = br.readLine()) != null) {
		String[] headers = line.split(",",-1);
		List<String> subList = new ArrayList<String>();
//			for (String header : headers) {
			for (int i = 0; i < headers.length; i++) {
//				subList.add(header.trim());
			subList.add(headers[i].trim());
		}
		list.add(subList);
	}

	System.out.println("List size is :" + list.size());

	br.close();

	int rows = list.size();
	int cols = list.get(0).size();

	String[][] array2D = new String[rows][cols];
	for (int row = 0; row < rows; row++) {
		for (int col = 0; col < cols; col++) {
			try {
				String string = list.get(row).get(col);
				System.out.println("String is :"+string);
				array2D[row][col] = list.get(row).get(col);
			} catch (Exception e) {
				System.out.println("row no and column no: "+row+","+col);
			}
		}
	}
	return array2D;
	}
	
	public static String[][] csvReader(String csvFilePath) throws Exception {
		
		List<List<String>> list = new ArrayList<List<String>>();
		BufferedReader br = new BufferedReader(new FileReader(csvFilePath));
		String line = br.readLine();
		String[] headers = line.split(",");
		for(String header: headers) {
			List<String> subList = new ArrayList<String>();
			subList.add(header);
			list.add(subList);
		}
		while((line = br.readLine()) != null) {
			String[] elems = line.split(",");
			for(int i = 0; i < elems.length; i++) {
				list.get(i).add(elems[i]);
			}
		}
		br.close();
		int rows = list.size();
		int cols = list.get(0).size();
		String[][] array2D = new String[rows][cols];
		for(int row = 0; row < rows; row++) {
			for(int col = 0; col < cols; col++) {
				array2D[row][col] = list.get(row).get(col);
			}
		}
		return array2D;
	}
	
	
	public static void csvReaderSuccess1() throws Exception {

		
		String fileName = "./data/data.csv";
		List<List<String>> list = new ArrayList<List<String>>();
		BufferedReader br = new BufferedReader(new FileReader(fileName));
		String line = br.readLine();
		String[] headers = line.split(",");
		for(String header: headers) {
		    List<String> subList = new ArrayList<String>();
		    subList.add(header);
		    list.add(subList);
		}
		while((line = br.readLine()) != null) {
		    String[] elems = line.split(",");
		    for(int i = 0; i < elems.length; i++) {
		        list.get(i).add(elems[i]);
		    }
		}
		br.close();
		int rows = list.size();
		int cols = list.get(0).size();
		String[][] array2D = new String[rows][cols];
		for(int row = 0; row < rows; row++) {
		    for(int col = 0; col < cols; col++) {
		        array2D[row][col] = list.get(row).get(col);
		    }
		}
		
		System.out.println(array2D[0][0]);
		System.out.println(array2D[1][0]);
		System.out.println(array2D[2][0]);
		System.out.println(array2D[3][0]);
		System.out.println(array2D[0][1]);
		System.out.println(array2D[1][1]);
		System.out.println(array2D[2][1]);
		System.out.println(array2D[3][1]);
	}
	
}
