package videoRecordings;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.commons.codec.binary.Base64;
import org.openqa.selenium.By;

import desiredCapability.DesiredCapabilties;
import io.appium.java_client.MobileDriver;
import io.appium.java_client.screenrecording.CanRecordScreen;


public class Recording1 {
	public static void main(String[] args) {
		
		MobileDriver driver = DesiredCapabilties.desiredCap("com.miui.calculator", ".cal.CalculatorActivity", "192.168.1.102:5555");
		String media = ((CanRecordScreen)driver).startRecordingScreen();
		
		driver.findElement(By.id("com.miui.calculator:id/btn_6_s")).click();
		driver.findElement(By.id("com.miui.calculator:id/btn_mul_s")).click();
		driver.findElement(By.id("com.miui.calculator:id/btn_5_s")).click();
		driver.findElement(By.id("com.miui.calculator:id/btn_equal_s")).click();
		
		((CanRecordScreen)driver).stopRecordingScreen();
		
		File videoDir = new File("./videoRecording");
		if (!videoDir.exists()) {
			videoDir.mkdirs();
		}
		FileOutputStream stream = null;
		try {
			stream = new FileOutputStream(videoDir + File.separator +"record.mp4");
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		try {
			stream.write(Base64.decodeBase64(media));
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			stream.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
}

