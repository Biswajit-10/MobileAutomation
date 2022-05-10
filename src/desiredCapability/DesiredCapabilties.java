package desiredCapability;

import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.remote.DesiredCapabilities;

import io.appium.java_client.android.AndroidDriver;

public class DesiredCapabilties {

	public void desiredCap() {

		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability("platformName", "Android");

		capabilities.setCapability("platformVersion", "11");
		capabilities.setCapability("automationName", "Appium");

		capabilities.setCapability("appPackage", "com.YONOUKMobileApp");
		capabilities.setCapability("appActivity", "com.YONOUKMobileApp.FinacleMobileApp");
		capabilities.setCapability("noReset", true);

		try {
			AndroidDriver driver = new AndroidDriver(new URL("http://0.0.0.0:4723/wd/hub"), capabilities);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}
}
