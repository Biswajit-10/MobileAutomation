package Experiment;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;

import csv.csvExp.CsvReader3;
import io.github.bonigarcia.wdm.WebDriverManager;


public class KeyWordEngiene {
	
	static WebDriver driver;
	
	public static void main(String[] args) throws Exception {
		
		try {
			System.out.println("argument in main is "+args[0]);
			}catch (Exception e) {
				args=new String[1];
				args[0]="N";
			}
		
		String[][] array2D=CsvReader3.csvReader("./data/testCase.csv");
		

		for (int i = 1; i < 10; i++) {
			
			String stepNo = array2D[0][i];
			String testStep = array2D[1][i] ;
			String locatorType = array2D[2][i];
			String locatorValue = array2D[3][i];
			String keyword = array2D[4][i];
			String data = array2D[5][i];

			try {

				String RunStatus = "Yes";
				
				/* if Run Status in is Yes then Run else not Run */
				if (RunStatus.equalsIgnoreCase("Yes")) {

					switch (keyword) {
					case "LAUNCH BROWSER":
						WebDriverManager.chromiumdriver().setup();
						driver=new ChromeDriver();
						break;
					case "OPEN URL":
						driver.get(data);
						break;
					case "CLICK":
						driver.findElement(By.xpath(locatorValue)).click();
						break;
					case "SEND KEYS":
						driver.findElement(By.xpath(locatorValue)).sendKeys(data);
						break;
				
					case "CLOSE BROWSER":
						driver.close();
						break;

					default:
						break;
					}
					

				} else {

					/* do nothing */

				}
			} catch (Exception e) {

				/* printing error message */
				System.err.println(e.getMessage());
				System.out.println("\n\n\n");
				System.err.println("We are getting error at line no: " + i);

				/*
				 * if we don't want to make porgram stop pass it in main argument Y else fail
				 * the script
				 */

				if (args[0].equalsIgnoreCase("Y")) {
					System.out.println("waiting for your response");
					String resume = Engiene.decision();

					if (resume.equalsIgnoreCase("Y")) {
						i--; // we will try that step once more if input is yes
					}

				} else {
					Assert.fail();
				}

			}

		}

	}

}
