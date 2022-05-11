package desiredCapability;

import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.remote.DesiredCapabilities;

import io.appium.java_client.MobileDriver;
import io.appium.java_client.android.AndroidDriver;

public class DesiredCapabilties {
	
	public static MobileDriver driver;
	
	public static MobileDriver desiredCap(String appPackage,String appActivity) {

		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability("platformName", "Android");

		capabilities.setCapability("platformVersion", "9");
		capabilities.setCapability("automationName", "uiautomator2");

		capabilities.setCapability("appPackage", appPackage);
		capabilities.setCapability("appActivity", appActivity);
		capabilities.setCapability("noReset", true);
		
		try {
			driver = new AndroidDriver(new URL("http://0.0.0.0:4723/wd/hub"), capabilities);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return driver;
	}
}
