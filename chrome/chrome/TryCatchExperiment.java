package chrome;

public class TryCatchExperiment {
		
	public static void main(String[] args) {
		try {
			System.out.println("first try block");
			return;
		}catch (Exception e) {}
		
		try {
			System.out.println("second try block");
		}catch (Exception e) {
		}
	}
	
}
/*		Output
 * 		------
 *		first try block 
 *
 */

