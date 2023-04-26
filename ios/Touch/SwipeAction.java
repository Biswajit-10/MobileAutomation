package Touch;

import java.util.HashMap;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
public class SwipeAction {
	
	 WebDriver driver;
	 
//   Here Direction is just a Variable which has only one value among all those 
//	 That is the purpose of enum
	 
	 public enum Direction {
	        LEFT,
	        RIGHT,
	        UP,
	        DOWN,
	        VERTICAL,
	        HORIZONTAL,
	        VERTICAL_DOWN_FIRST,
	        HORIZONTAL_RIGHT_FIRST
	    }

	    public enum Zoom {
	        IN,
	        OUT
	    }

	
	/**
	 * Performs screen scroll
	 *
	 * @param dir the direction of scroll
	 * @version java-client: 7.3.0
	 **/
	public void mobileScrollScreenIOS(Direction dir) {
	    System.out.println("mobileScrollScreenIOS(): dir: '" + dir + "'"); // always log your actions

	    // Animation default time:
	    //  - iOS: 200 ms
	    // final value depends on your app and could be greater
	    final int ANIMATION_TIME = 200; // ms
	    final HashMap<String, String> scrollObject = new HashMap<String, String>();

	    switch (dir) {
	        case DOWN: // from down to up (! differs from mobile:swipe)
	            scrollObject.put("direction", "down");
	            break;
	        case UP: // from up to down (! differs from mobile:swipe)
	            scrollObject.put("direction", "up");
	            break;
	        case LEFT: // from left to right (! differs from mobile:swipe)
	            scrollObject.put("direction", "left");
	            break;
	        case RIGHT: // from right to left (! differs from mobile:swipe)
	            scrollObject.put("direction", "right");
	            break;
	        default:
	            throw new IllegalArgumentException("mobileScrollIOS(): dir: '" + dir + "' NOT supported");
	    }
	    try {
	        ((JavascriptExecutor) driver).executeScript("mobile:scroll", scrollObject); // swipe faster then scroll
	        Thread.sleep(ANIMATION_TIME); // always allow swipe action to complete
	    } catch (Exception e) {
	        System.err.println("mobileScrollIOS(): FAILED\n" + e.getMessage());
	        return;
	    }
	}

	/**
	 * Performs screen swipe
	 *
	 * @param dir the direction of swipe
	 * @version java-client: 7.3.0
	 **/
	public void mobileSwipeScreenIOS(Direction dir) {
	    System.out.println("mobileSwipeScreenIOS(): dir: '" + dir + "'"); // always log your actions

	    // Animation default time:
	    //  - iOS: 200 ms
	    // final value depends on your app and could be greater
	    final int ANIMATION_TIME = 200; // ms
	    final HashMap<String, String> scrollObject = new HashMap<String, String>();

	    switch (dir) {
	        case DOWN: // from up to down (! differs from mobile:scroll)
	            scrollObject.put("direction", "down");
	            break;
	        case UP: // from down to up  (! differs from mobile:scroll)
	            scrollObject.put("direction", "up");
	            break;
	        case LEFT: // from right to left  (! differs from mobile:scroll)
	            scrollObject.put("direction", "left");
	            break;
	        case RIGHT: // from left to right  (! differs from mobile:scroll)
	            scrollObject.put("direction", "right");
	            break;
	        default:
	            throw new IllegalArgumentException("mobileSwipeScreenIOS(): dir: '" + dir + "' NOT supported");
	    }
	    try {
	        ((JavascriptExecutor) driver).executeScript("mobile:swipe", scrollObject);
	        Thread.sleep(ANIMATION_TIME); // always allow swipe action to complete
	    } catch (Exception e) {
	        System.err.println("mobileSwipeScreenIOS(): FAILED\n" + e.getMessage());
	        return;
	    }
	}
	/**
	 * Performs screen scroll down direction Until element is visible
	 **/
	public void swipeUntilElementVisible(String locatorStrategy,String locatorValue ) {
	JavascriptExecutor js = (JavascriptExecutor) BaseTest.driver;
	Map<String, Object> params = new HashMap<String, Object>();
	params.put("direction", "down");
	switch (locatorStrategy.toLowerCase()) {
	case "id":
		params.put("id",locatorValue);
		break;

	case "name":
		params.put("name",locatorValue);
		break;

	case "xpath":
		params.put("xpath",locatorValue);
		break;

	default:
		break;
	}

	js.executeScript("mobile: scroll", params);

}
	
}
