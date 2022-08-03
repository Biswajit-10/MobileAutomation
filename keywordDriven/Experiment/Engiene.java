package Experiment;

import java.util.Scanner;
import org.testng.Assert;


public class Engiene {
	
	public static void main(String[] args) {
		try {
			System.out.println("argument in main is "+args[0]);
			}catch (Exception e) {
				args=new String[1];
				args[0]="N";
			}
		System.out.println("argument in main is "+args[0]);

		for (int i = 0; i < 10; i++) {

			try {

				String RunStatus = "Yes";
		/*		if Run Status in is Yes then Run else not Run*/		
				if (RunStatus.equalsIgnoreCase("Yes")) {
					
					System.out.println(1/0);
					// do switch case
					// All the functions
					// All the actions
					
				}else {
					
					/*do nothing*/
					
				}
			} 
			catch (Exception e) {

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
					String resume = decision();

					if (resume.equalsIgnoreCase("Y")) {
						i--; // we will try that step once more if input is yes
					}

				} else {
					Assert.fail();
				}
				
			}

		}

	}

	/* To Do */
	/* When you are ready after editing just type y and click enter */
	/* make it such a way that it can only take input as y or n */
	public static String input() {
		Scanner scanner = new Scanner(System.in);
		System.out.println("type Y or N");
		String input = scanner.next();

		return input;
	}

	public static String decision() {
		Scanner scanner = new Scanner(System.in);
		String decision=null;
		boolean yn = true;
		while (yn) {

			System.out.println("please enter Y or N");
			decision = scanner.next();

			System.out.println("you entered the decision: " + decision+"\n");

			switch (decision) {
			case "Y":
				yn = false;
				break;
			case "y":
				yn = false;
				break;
			case "N":
				yn = false;
				break;
			case "n":
				yn = false;
				break;
			default:
				System.out.println("please enter a valid Input ");
			}
		}
		return decision;
	}

	
} 
