import java.awt.Color;
import java.awt.Desktop;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import javax.imageio.ImageIO;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;


public  final class Utils {
	
	public static final String filePath = Paths.get(".").toAbsolutePath()
												.normalize().toString();
	
	public static final int size = 125;
	public static final String fileType = "png";
    public static String charset = "UTF-8"; // or "ISO-8859-1"

	private static void createQRImage(File qrFile, String qrCodeText, int size, 
										String fileType) throws WriterException, IOException {
		// Create the ByteMatrix for the QR-Code that encodes the given String
		try{
			Hashtable hintMap = new Hashtable();
			hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
			QRCodeWriter qrCodeWriter = new QRCodeWriter();
			BitMatrix byteMatrix = qrCodeWriter.encode(qrCodeText,
					BarcodeFormat.QR_CODE, size, size, hintMap);
			// Make the BufferedImage that are to hold the QRCode
			int matrixWidth = byteMatrix.getWidth();
			BufferedImage image = new BufferedImage(matrixWidth, matrixWidth,
					BufferedImage.TYPE_INT_RGB);
			image.createGraphics();

			Graphics2D graphics = (Graphics2D) image.getGraphics();
			graphics.setColor(Color.WHITE);
			graphics.fillRect(0, 0, matrixWidth, matrixWidth);
			// Paint and save the image using the ByteMatrix
			graphics.setColor(Color.BLACK);

			for (int i = 0; i < matrixWidth; i++) {
				for (int j = 0; j < matrixWidth; j++) {
					if (byteMatrix.get(i, j)) {
						graphics.fillRect(i, j, 1, 1);
					}
				}
			}
			ImageIO.write(image, fileType, qrFile);
		}catch(WriterException | IOException e){
			e.printStackTrace();
			
		}
		
	}
	// accepts String as input
	// creates an image file with the QR code.
	public static void createQrCode(String qrText, String fileName){
		String path = Paths.get(filePath, fileName).toString() + "." + fileType;
		File file = new File(path);
		try {
			createQRImage(file, qrText, size, fileType);
		    Desktop dt = Desktop.getDesktop();
		    dt.open(file);
		} catch (WriterException | IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	}
	// Takes a file object(image) and returns back the decoded contents as string
	
	public static String readQRCode(String filePath)
		      throws FileNotFoundException, IOException, NotFoundException {
		Map hintMap = new HashMap();
		hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
		    BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(
		        new BufferedImageLuminanceSource(
		            ImageIO.read(new FileInputStream(filePath)))));
		    Result qrCodeResult = new MultiFormatReader().decode(binaryBitmap,
		        hintMap);
		    return qrCodeResult.getText();
		  }
	public static String extractQrCodeFromFile(String path){
		//Set up OCR engine
//		Ocr.setUp();
//		Ocr ocr = new Ocr();
//		ocr.startEngine("eng", Ocr.SPEED_FASTEST);
//		String extractedText = ocr.recognize(new File[] {qrImage},
//				   				Ocr.RECOGNIZE_TYPE_BARCODE, Ocr.OUTPUT_FORMAT_PLAINTEXT);
//		ocr.stopEngine();

		String extractedText = null;
		try {
			extractedText = "Contents of the QR Code: " + readQRCode(path);
		} catch (NotFoundException | IOException e) {
			e.printStackTrace();
		    System.out.println("Image does not contain a recognizable QR Code");
		}
		return extractedText;
	}

}
