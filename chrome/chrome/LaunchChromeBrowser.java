package chrome;

/**
 * Appium has inbuilt chrome driver
 * if chrome driver compatibility issue came up you should delete the current chromedriver.exe in 
 *  No Chromedrivers were found in 'C:\Program Files\Appium Server GUI\resources\app\node_modules\appium\node_modules\appium-chromedriver\chromedriver\win'
 *  
 *  check Appium logs
 */
import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.remote.DesiredCapabilities;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;

public class LaunchChromeBrowser {
	
	public static void main(String[] args) throws MalformedURLException, InterruptedException {
		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability("platformName", "Android");

		capabilities.setCapability("platformVersion", "9");
		capabilities.setCapability("automationName", "uiautomator2");

		capabilities.setCapability(MobileCapabilityType.BROWSER_NAME, "chrome");
//		capabilities.setCapability("chromedriverExecutable","C:\\Users\\Biswajit\\Appium-workspace\\MobileAutomation\\chromedriver.exe");
		capabilities.setCapability("deviceId", "192.168.1.101:5555");
		capabilities.setCapability("deviceId", "4f905e2c");
		capabilities.setCapability("noReset", true);

		AndroidDriver<MobileElement> driver = new AndroidDriver<>(new URL("http://0.0.0.0:4723/wd/hub"), capabilities);

//		Thread.sleep(5000);
		driver.get("http://appium.io/docs/en/writing-running-appium/tutorial/swipe/ios-mobile-screen/");
	}
	
}
