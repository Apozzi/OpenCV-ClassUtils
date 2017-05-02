package openCV;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.CvType;
import org.opencv.core.Scalar;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.videoio.VideoCapture;
import org.opencv.core.Point;
import org.opencv.core.Rect;

public class main {
	static {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	}
	
	DetectFace cp;
	
	public static void main(String[] args) throws InterruptedException {
		try {
			String filepath = "filepath.jpg";
			BufferedImage image = ScreenShot.GetCurrentScreenImage();
			BufferedImage imageDetected = DetectFace.detectAndCreateSquare(image);
			File file = new File(filepath);
			if (!file.exists()) {
				file.createNewFile();
			}
			ImageIO.write(imageDetected, "jpg", file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
