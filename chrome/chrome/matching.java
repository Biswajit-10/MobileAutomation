package chrome;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

//find() method is like hasNext() so it is mandatory to use it

public class matching {

	public static void main(String[] args) {
		String otp=null;
		String str="123 456 789 0";
//		final Pattern p =Pattern.compile("[0-9]{3}");//or [0-9]==/d
		Pattern p =Pattern.compile("[/d]{3}");//or [0-9]==/d
		Matcher m= p.matcher(str);
		
//		if(m.find()){
//			otp=m.group();
//		}
//		System.out.println(otp);
//		if(m.find()){
//			otp=m.group();
//		}
//		System.out.println(otp);
//		if(m.find()){
//			otp=m.group();
//		}
//		System.out.println(otp);
		
		while(m.find()) {
			otp=m.group();
			System.out.println(otp);
		}
		
	}

}
class matching2_starts_with {
	
	public static void main(String[] args) {
		String otp=null;
		String str="your emailid is samalbiswajit20@gmail.com";
		
		Pattern p =Pattern.compile("[e][a-z]*");	//starts with e then a to z 1 or more times
		Matcher m= p.matcher(str);
		if(m.find()){
			otp=m.group();
			System.out.println(otp);
		}
		p =Pattern.compile("[e][a-z]{4}");	
		m= p.matcher(str);
		if(m.find()){
			otp=m.group();
			System.out.println(otp);
		}
	}
	
}
 class matching3 {
	
	public static void main(String[] args) {
		String otp=null;
		String str="your email id is samalbiswajit20@gmail.com";
		
		final Pattern p =Pattern.compile("[a-z A-Z]*[0-9]{3}");//a to z then space then A - Z
		final Matcher m= p.matcher(str);
		if(m.find()){
			otp=m.group();
		}
		System.out.println(otp);
	}
	
}
class MobileNumberMatching{
	public static void main(String[] args) {
		String phno=null;
		String s="My phone number is 7077276850";
		Pattern p = Pattern.compile("[70][0-9]{8}");//starts with 7 or 0
		Matcher m = p.matcher(s);
		if(m.find()){
			phno=m.group();
			System.out.println(m.group());
		}
		System.out.println(phno);		
		 p = Pattern.compile("[7][0][0-9]{8}");//starts with 7 then 0
		 m = p.matcher(s);
		if(m.find()){
			System.out.println(m.group());
		}
		
	}
}


class Pin{
	public static void gettingPin() {
		
		String pin=null;
		String s="My pin  is 567432";
		Pattern p = Pattern.compile("[0-9]{6}");//starts with 7 or 0
		Matcher m = p.matcher(s);
		if(m.find()){
			pin=m.group();
			System.out.println(m.group());
		}
		System.out.println(pin);
		
	}
	public static void main(String[] args) {
		
		gettingPin();
	}
}









