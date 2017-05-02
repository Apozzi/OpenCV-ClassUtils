package openCV;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Stroke;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileSystem;

import javax.naming.Context;

import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Rect;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.videoio.VideoCapture;

public class DetectFace {
	private static BufferedImage image;
	private static VideoCapture capture;
	private static CascadeClassifier faceDetector;
	private static MatOfRect faceDetections;
	
	public static BufferedImage detectAndCreateSquare(BufferedImage image) throws IOException {
		Rect[] rectangles = detect(image);
		BufferedImage imageDetected = copyImage(image);
		Graphics2D graphics = imageDetected.createGraphics();
		for (Rect rect : rectangles) {
			graphics.setColor(Color.red);
			float thickness = 2;
			Stroke oldStroke = graphics.getStroke();
			graphics.setStroke(new BasicStroke(thickness));
			graphics.drawRect(rect.x, rect.y, rect.width, rect.height);
			graphics.setStroke(oldStroke);
			graphics.dispose();
			
		}
		return imageDetected;
	}
	
	public static Rect[] detect(BufferedImage image) throws IOException {
		Mat matImage = fromBufferedImage(image);
		String filePath = new File("src/haarcascade_frontalface_alt.xml").getAbsolutePath();
		System.out.println(filePath);
		faceDetector = new CascadeClassifier(filePath);
		faceDetections = new MatOfRect();
		faceDetector.detectMultiScale(matImage, faceDetections);
		return faceDetections.toArray();
		
	}
	
	private static BufferedImage copyImage(BufferedImage source){
	    BufferedImage b = new BufferedImage(source.getWidth(), source.getHeight(), source.getType());
	    Graphics g = b.getGraphics();
	    g.drawImage(source, 0, 0, null);
	    g.dispose();
	    return b;
	}

	private static Mat fromBufferedImage(BufferedImage img) {
	    byte[] pixels = ((DataBufferByte) img.getRaster().getDataBuffer()).getData();
	    Mat mat = new Mat(img.getHeight(), img.getWidth(), CvType.CV_8UC3);
	    mat.put(0, 0, pixels);
	    return mat;
	}
}
