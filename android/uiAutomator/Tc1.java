package uiAutomator;

import desiredCapability.DesiredCapabilties;
import io.appium.java_client.MobileDriver;

public class Tc1 {
	
	public static void main(String[] args) {
		
		MobileDriver driver = DesiredCapabilties.desiredCap("com.wt.apkinfo", ".activities.StartActivity");
		ScrollToElement scroll=new ScrollToElement();
		scroll.scroll("text","Lite");
	}
	
}
