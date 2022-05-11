package desiredCapability;

import java.io.IOException;
import java.net.ServerSocket;

import io.appium.java_client.MobileDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.service.local.AppiumDriverLocalService;

public class ServerUtils {
	
	public static AppiumDriverLocalService service;
	  public static MobileDriver<MobileElement>  driver;

	public static MobileDriver<MobileElement> desiredCap() {

		return null;

	}

	public AppiumDriverLocalService startAppiumServer() {
		service = AppiumDriverLocalService.buildDefaultService();
		service.start();
		return service;
	}
	
	public AppiumDriverLocalService stopAppiumServer() {
		service.stop();
		return service;
	}

	public boolean serverState() {
		boolean isServerRunning = false;
		ServerSocket serverSocket;
		try {
			serverSocket = new ServerSocket(4723);
			serverSocket.close();
		} catch (IOException e) {
			// If control comes here, then it means that the port is in use
			isServerRunning = true;
		} finally {
			serverSocket = null;
		}
		return isServerRunning;
	}

}
