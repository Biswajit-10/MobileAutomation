package screenshotExperiment;

import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
 
import javax.imageio.ImageIO;

/*
 	
 	Robot.createScreenCapture() and ImageIO.write() 
 
 */

public class ScreenCapture {
	public static void main(String[] args) {
		 try {
	            Robot robot = new Robot();
	            String format = "jpg";
	            String fileName = "FullScreenshot." + format;
	             
	            Rectangle screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
	            BufferedImage screenFullImage = robot.createScreenCapture(screenRect);
	            ImageIO.write(screenFullImage, format, new File(fileName));
	             
	            System.out.println("A full screenshot saved!");
	        } catch (AWTException | IOException ex) {
	            System.err.println(ex);
	        }
	}
	
	private static byte[] pngBytesToJpgBytes(byte[] pngBytes) throws IOException {
		//create InputStream for ImageIO using png byte[]
		ByteArrayInputStream bais = new ByteArrayInputStream(pngBytes);
		//read png bytes as an image
		BufferedImage bufferedImage = ImageIO.read(bais);

		BufferedImage newBufferedImage = new BufferedImage(bufferedImage.getWidth(),
			bufferedImage.getHeight(),
			BufferedImage.TYPE_INT_RGB);
		newBufferedImage.createGraphics().drawImage(bufferedImage, 0, 0, Color.WHITE, null);

		//create OutputStream to write prepaired jpg bytes
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		//write image as jpg bytes
		ImageIO.write(newBufferedImage, "JPG", baos);

		//convert OutputStream to a byte[]
        return baos.toByteArray();
    }
	
}
