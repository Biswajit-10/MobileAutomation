package Touch;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;

import io.appium.java_client.MobileDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.LongPressOptions;
import io.appium.java_client.touch.offset.ElementOption;

public class LongPres {
	
    /**
     * tap with TouchActions slowly to imitate log tap on element
     *
     * @param elem ExtendedWebElement
     *            element
     */
    default public void longTap(ExtendedWebElement elem) {
        Dimension size = elem.getSize();

        int width = size.getWidth();
        int height = size.getHeight();

        int x = elem.getLocation().getX() + width / 2;
        int y = elem.getLocation().getY() + height / 2;
        try {
            swipe(x, y, x, y, 2500);
        } catch (Exception e) {
            UTILS_LOGGER.error("Exception: " + e);
        }
    }

    /**
     * Tap and Hold (LongPress) on element
     *
     * @param element ExtendedWebElement
     * @return boolean
     */
    default public boolean longPress(ExtendedWebElement element) {
        // TODO: SZ migrate to FluentWaits
        try {
            WebDriver driver = castDriver();
            @SuppressWarnings("rawtypes")
            TouchAction<?> action = new TouchAction((MobileDriver<?>) driver);
            LongPressOptions options = LongPressOptions.longPressOptions().withElement(ElementOption.element(element.getElement()));
            action.longPress(options).release().perform();
            return true;
        } catch (Exception e) {
            UTILS_LOGGER.info("Error occurs during longPress: " + e, e);
        }
        return false;
    }


	
}
