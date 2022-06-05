package Touch;

import java.time.Duration;

import org.openqa.selenium.Dimension;

import desiredCapability.DesiredCapabilties;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;

public class Swipe {
	
	protected void swipeDown(int swipeCount) {

		Dimension dimension =DesiredCapabilties.driver.manage().window().getSize();
		int startX = dimension.width / 2;
		int endX = startX;
		int startY = (int) (dimension.height * 0.8);
		int endY = (int) (dimension.height * 0.2);

		for (int i = 0; i < swipeCount; i++) {
			TouchAction touchAction = new TouchAction(DesiredCapabilties.driver);
			touchAction.
			/*
			 * Using the PointOption is worst practice : Varies with device-to-device
			 * (Screen resolution)
			 */
			// press(PointOption.point(477, 1891)).
					press(PointOption.point(startX, startY)).
					/* It'll take 2 seconds to complete swipe operation */
					waitAction(WaitOptions.waitOptions(Duration.ofMillis(2000))).
					// moveTo(PointOption.point(494, 386))
					moveTo(PointOption.point(endX, endY)).release().perform();
		}
	}
	
}
