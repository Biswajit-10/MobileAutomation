package Touch;

import java.time.Duration;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;

import io.appium.java_client.MobileDriver;
import io.appium.java_client.MultiTouchAction;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;

public class Zoom_Or_MultiTouch {
	
	 default public void zoom(int startx1, int starty1, int endx1, int endy1, int startx2, int starty2, int endx2, int endy2, int duration) {
	        UTILS_LOGGER.debug(String.format(
	                "Zoom action will be performed with parameters : startX1 : %s ;  startY1: %s ; endX1: %s ; endY1: %s; startX2 : %s ;  startY2: %s ; endX2: %s ; endY2: %s",
	                startx1, starty1, endx1, endy1, startx2, starty2, endx2, endy2));
	        MobileDriver<?> driver = (MobileDriver<?>) castDriver();
	        try {
	            MultiTouchAction multiTouch = new MultiTouchAction(driver);
	            @SuppressWarnings("rawtypes")
	            TouchAction<?> tAction0 = new TouchAction(driver);
	            @SuppressWarnings("rawtypes")
	            TouchAction<?> tAction1 = new TouchAction(driver);

	            PointOption<?> startPoint1 = PointOption.point(startx1, starty1);
	            PointOption<?> endPoint1 = PointOption.point(endx1, endy1);
	            PointOption<?> startPoint2 = PointOption.point(startx2, starty2);
	            PointOption<?> endPoint2 = PointOption.point(endx2, endy2);
	            WaitOptions waitOptions = WaitOptions.waitOptions(Duration.ofMillis(duration));

	            tAction0.press(startPoint1).waitAction(waitOptions).moveTo(endPoint1).release();
	            tAction1.press(startPoint2).waitAction(waitOptions).moveTo(endPoint2).release();
	            multiTouch.add(tAction0).add(tAction1);
	            multiTouch.perform();
	            UTILS_LOGGER.info("Zoom has been performed");
	        } catch (Exception e) {
	            UTILS_LOGGER.error("Error during zooming", e);
	        }
	    }
	 
	 
	    default public void zoom(Zoom_Or_MultiTouch type) {
	        UTILS_LOGGER.info("Zoom will be performed :" + type);
	        MobileDriver<?> driver = (MobileDriver<?>) castDriver();
	        Dimension scrSize = driver.manage().window().getSize();
	        int height = scrSize.getHeight();
	        int width = scrSize.getWidth();
	        UTILS_LOGGER.debug("Screen height : " + height);
	        UTILS_LOGGER.debug("Screen width : " + width);
	        Point point1 = new Point(width / 2, height / 2 - 30);
	        Point point2 = new Point(width / 2, height / 10 * 3);
	        Point point3 = new Point(width / 2, height / 2 + 30);
	        Point point4 = new Point(width / 2, (7 * height) / 10);
	        switch (type) {
	        case OUT:
	            zoom(point1.getX(), point1.getY(), point2.getX(), point2.getY(), point3.getX(), point3.getY(), point4.getX(), point4.getY(),
	                    DEFAULT_TOUCH_ACTION_DURATION);
	            break;
	        case IN:
	            zoom(point2.getX(), point2.getY(), point1.getX(), point1.getY(), point4.getX(), point4.getY(), point3.getX(), point3.getY(),
	                    DEFAULT_TOUCH_ACTION_DURATION);
	            break;
	        }
	    }


	 
	
	
}
