package csv.controllingFlow;

import java.util.Scanner;

public class InputTesting {
	
	public static void main(String[] args) {
		try {
		System.out.println(args[0]);
		}catch (Exception e) {
			args=new String[1];
			args[0]="N";
		}
		System.out.println(args[0]);
//		decision();
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
