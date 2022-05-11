package uiAutomator;

import desiredCapability.DesiredCapabilties;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;

/**
 * 
 * @author Biswajit
 * {@link}@https://appium.io/docs/en/writing-running-appium/tutorial/swipe/android-simple/
 */
public class ScrollToElement {
	
	AndroidDriver<MobileElement> driver=(AndroidDriver<MobileElement>) DesiredCapabilties.driver;
	
	public void scroll(String attributeName,String attributeValue) {
		
		((AndroidDriver<MobileElement>)driver).
		findElementByAndroidUIAutomator("new UIScrollable(new UiSelector())."
				+ "scrollIntoView("+attributeName+"(\""+attributeValue+"\"))");
		
	}
	public void scroll() {
		
		((AndroidDriver<MobileElement>)driver).
		findElementByAndroidUIAutomator("new UIScrollable(new UiSelector())."
				+ "scrollIntoView(AttributeName(\"AttributeValue\"))");
		
	}
	
	
	
	
	/*Search by text*/
	@AndroidFindBy(uiAutomator = "new UiScrollable(new UiSelector().scrollable(true))" +
			".scrollIntoView(new UiSelector().text(\"exact_text\"))")
	MobileElement element1;
	
	@AndroidFindBy(uiAutomator = "new UiScrollable(new UiSelector().scrollable(true))" +
	        ".scrollIntoView(new UiSelector().textContains(\"part_text\"))")
	MobileElement element2;

	MobileElement element3 = (MobileElement) driver.findElement(MobileBy.AndroidUIAutomator(
	        "new UiScrollable(new UiSelector().scrollable(true))" +
	         ".scrollIntoView(new UiSelector().text(\"exact_text\"))"));

	MobileElement element4 = (MobileElement) driver.findElement(MobileBy.AndroidUIAutomator(
	        "new UiScrollable(new UiSelector().scrollable(true))" +
	         ".scrollIntoView(new UiSelector().textContains(\"part_text\"))"));
	
	
	
	/*Search by id*/
	@AndroidFindBy(uiAutomator = "new UiScrollable(new UiSelector().scrollable(true))" +
	        ".scrollIntoView(new UiSelector().resourceIdMatches(\".*part_id.*\"))")
	MobileElement element5;

	// FindElement
	MobileElement element6 = (MobileElement) driver.findElement(MobileBy.AndroidUIAutomator(
	        "new UiScrollable(new UiSelector().scrollable(true))" +
	         ".scrollIntoView(new UiSelector().resourceIdMatches(\".*part_id.*\"))"));

	
	
	
	/*Search by id and text*/
	// Page object
	@AndroidFindBy(uiAutomator = "new UiScrollable(new UiSelector().scrollable(true))" +
	        ".scrollIntoView(new UiSelector().resourceIdMatches(\".*part_id.*\").text(\"exact_text\"))")
	MobileElement element7;

	// FindElement
	MobileElement element8 = (MobileElement) driver.findElement(MobileBy.AndroidUIAutomator(
	        "new UiScrollable(new UiSelector().scrollable(true))" +
	         ".scrollIntoView(new UiSelector().resourceIdMatches(\".*part_id.*\").text(\"exact_text\"))"));
	
	
/*	Long view issue
	For some longer views it is necessary to increase "setMaxSearchSwipes". 
	This value allows to set the maximum count of swipe retries made until the search is stopped.
*/
	// set max swipes to 10
	// FindElement
	MobileElement element = (MobileElement) driver.findElement(MobileBy.AndroidUIAutomator(
	        "new UiScrollable(new UiSelector().scrollable(true)).setMaxSearchSwipes(10)" +
	         ".scrollIntoView(new UiSelector().text(\"exact_text\"))"));

}
