package csv;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class CsvRead {
	public static void main(String[] args) {

	}
}

class ReadCSVExample1 {

	public static void main(String[] args) throws Exception {

//		parsing a CSV file into Scanner class constructor  
		Scanner sc = new Scanner(new File("./data/data.csv"));
		sc.useDelimiter(","); // sets the delimiter pattern
		while (sc.hasNext()) // returns a boolean value
		{
			System.out.print(sc.next()); // find and returns the next complete token from this scanner
		}
		sc.close(); // closes the scanner
	}
}

class ReadCSVExample2 {
	
	public static void main(String[] args) {
		String line = "";
		String splitBy = ",";
		try {
//	parsing a CSV file into BufferedReader class constructor  
			BufferedReader br = new BufferedReader(new FileReader("./data/data.csv"));
			while ((line = br.readLine()) != null) // returns a Boolean value
			{
				String[] employee = line.split(splitBy); // use comma as separator
				System.out.println("Employee [First Name=" + employee[0] );
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}