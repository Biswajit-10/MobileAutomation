package logs;

import org.openqa.selenium.By;
import org.openqa.selenium.logging.LogEntries;

import desiredCapability.DesiredCapabilties;
import io.appium.java_client.MobileDriver;

public class RecordLogs {
	
	public static void main(String[] args) {
		MobileDriver driver = DesiredCapabilties.desiredCap("com.miui.calculator", ".cal.CalculatorActivity", "192.168.1.101:5555");
		driver.findElement(By.id("com.miui.calculator:id/btn_6_s")).click();
		driver.findElement(By.id("com.miui.calculator:id/btn_mul_s")).click();
		driver.findElement(By.id("com.miui.calculator:id/btn_5_s")).click();
		driver.findElement(By.id("com.miui.calculator:id/btn_equal_s")).click();
		
//		Logs log = driver.manage().logs();
//		System.out.println(log);
		
		LogEntries logEntries = driver.manage().logs().get("driver");
		System.out.println(logEntries);
	}
	
}
