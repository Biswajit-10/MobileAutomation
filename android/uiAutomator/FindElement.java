package uiAutomator;

import io.appium.java_client.MobileDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;

public class FindElement {
	
	public void uiAutomator() {
		MobileDriver<MobileElement> driver = null;
		
		/*Wrong Way*/
//		(AndroidDriver<MobileElement>)driver.findElementByAndroidUIAutomator(null);
		
		/*Right Way*/
		((AndroidDriver<MobileElement>)driver).findElementByAndroidUIAutomator(null);
		
		
	}
}
