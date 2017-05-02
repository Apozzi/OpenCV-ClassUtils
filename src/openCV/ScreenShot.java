package openCV;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import org.opencv.core.*;
import org.opencv.videoio.VideoCapture;

public class ScreenShot {
	
	static {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	}
	
	/**
	 * Caso a webcam estiver disponivel bate uma foto e salva em um buffer de imagens.
	 * @return buffer de imagens.
	 */
	public static BufferedImage GetCurrentScreenImage() throws InterruptedException, IOException {
		 VideoCapture camera = new VideoCapture(0);
		 Thread.sleep(1000);
		 camera.open(0);
		 if(!camera.isOpened()){
			 throw new IOException("Camera nao disponivel");
		 }
		 Mat frame = new Mat();
		 camera.grab();
		 camera.retrieve(frame);
		 camera.read(frame);
		 return createBufferedImage(frame);
	}
	
	/**
	 * Caso a webcam estiver disponivel bate uma foto e cria um arquivo com a imagem com path especificado.
	 * @param filePath - path do arquivo
	 * @return arquivo em que esta contido a imagem.
	 */
	public static File GetCurrentImageAndWriteInFile(String filePath) throws IOException {
		BufferedImage screenPixelData;
		try {
			screenPixelData = GetCurrentScreenImage();
		} catch (InterruptedException e) {
			throw new IOException("Nao foi possivel ler a imagem");
		}
		File file = new File(filePath);
		if (!file.exists()) {
			file.createNewFile();
		}
		ImageIO.write(screenPixelData, "jpg", file);
		return file;
	}
	
	private static BufferedImage createBufferedImage(Mat mat) {
	    BufferedImage image = new BufferedImage(mat.width(), mat.height(), BufferedImage.TYPE_3BYTE_BGR);
	    WritableRaster raster = image.getRaster();
	    DataBufferByte dataBuffer = (DataBufferByte) raster.getDataBuffer();
	    byte[] data = dataBuffer.getData();
	    mat.get(0, 0, data);
	    return image;
	}
	
}
