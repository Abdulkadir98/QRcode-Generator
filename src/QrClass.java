import java.awt.Color;
import java.awt.Desktop;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Hashtable;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

public class QrClass {
	static int n;
	//static Ocr ocr;
	static String qrText;
	static StringBuffer sb;
	public QrClass(){
		JFrame f = new JFrame("Barcode Scanner");
		JLabel label = new JLabel();
		JTextField tf = new JTextField();
		JTextArea ta = new JTextArea("extracted text:");
		JButton button = new JButton("Enter details");
		tf.setEditable(true);
		label.setText("Enter the number of inputs:");
		label.setBounds(50, 50, 175, 20);
		tf.setBounds(215, 50, 100, 20);
		button.setBounds(150, 150, 150, 20);
		ta.setBounds(20,250,500,200);
		button.addActionListener(new ActionListener(){
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String input = tf.getText();
				int number = 0;
				
					try{
						number = Integer.parseInt(input);
						qrText = enterInput(number);
					}
					catch(NumberFormatException ex){
						ex.printStackTrace();
						JOptionPane.showMessageDialog(button, "Please enter a Number");
						return;
					}
					
				//Utils.createQrCode(qrText);
				//String s = performOcr(fileName+".png");
				
//				int size = 125;
////				String filePath = "C:\\Users\\admin\\workspace\\QRCodeGenerator\\barcode.png";
////				String filePath2 = "D:\\barcode.png";
//				String fileType = "png";
//				//File file = new File(filePath);
//				String temp = Paths.get(".").toAbsolutePath().normalize().toString() + "\\barcode.png" ;
//				String fpath = temp + "\\barcode.png";
//				File file = new File(fpath);
//				try {
//					createQRImage(file, qrText, size, fileType);
//				    Desktop dt = Desktop.getDesktop();
//				    dt.open(file);
//				} catch (WriterException | IOException e1) {
//					// TODO Auto-generated catch block
//					e1.printStackTrace();
//				}
//				Ocr.setUp();
//				Ocr ocr = new Ocr();
//				ocr.startEngine("eng", Ocr.SPEED_FASTEST);
//				String s = ocr.recognize(new File[] {file},
//						   Ocr.RECOGNIZE_TYPE_BARCODE, Ocr.OUTPUT_FORMAT_PLAINTEXT);
//				
//	            ta.append(s);
//	            ocr.stopEngine();
//				
			}
		});
		f.add(label);
		f.add(tf);
		f.add(button);
		f.add(ta);
		f.setSize(500, 500);
		f.setLayout(null);
	    f.setVisible(true);
	}
	public static void main(String args[]) throws IOException, WriterException{
		
		
	//Set up OCR engine
//		Ocr.setUp();
//		Ocr ocr = new Ocr();
//		ocr.startEngine("eng", Ocr.SPEED_FASTEST);
		//qrText = "www.google.com\nName: Abdul Kadir\n Age: 19\n Aadhar: 32434324\n Pan card:34823947";
		//qrText = enterInput();
//		int size = 125;
//		String filePath = "C:\\Users\\admin\\workspace\\QRCodeGenerator\\barcode.png";
//		String fileType = "png";
//		File file = new File(filePath);
//		createQRImage(file, qrText, size, fileType);
//		String s = ocr.recognize(new File[] {new File("barcode.png")},
//				   Ocr.RECOGNIZE_TYPE_BARCODE, Ocr.OUTPUT_FORMAT_PLAINTEXT);
		//new QrClass();
		//qrText = enterInput(3);
		System.out.println("Do you wish to:\n1. Create a QR code\n2. Extract from QR code\n3. Check if ");
		Scanner sc = new Scanner(System.in);
		int ch = sc.nextInt();
		if(ch==1){
			System.out.println("Enter the number of attributes:");
			StringBuffer sb = new StringBuffer();
			int n = sc.nextInt();
			for(int i = 0; i<n; i++){
				System.out.println("\nEnter attribute number: "+ (i+1));
				String text = sc.next();
				sb.append(text);
			}
			qrText = sb.toString();
			System.out.println("Enter file name(without format):");
			String fileName = sc.next();
			Utils.createQrCode(qrText,fileName);
		}
		else if(ch==2){
			System.out.println("Enter file name:");
			String fileName = sc.next();
			String filePath = Paths.get(Utils.filePath, fileName).toString() + "." + Utils.fileType;
			String text = Utils.extractQrCodeFromFile(filePath);
			System.out.println(text);
			System.out.println("DONE");
		}
		else if (ch == 3) {
			System.out.println("Enter file name:");
			String fileName = sc.next();
			String filePath = Paths.get(Utils.filePath, fileName).toString() + "." + Utils.fileType;
			Boolean response = Utils.checkIfQRCode(filePath);
			System.out.println(response);
		}
		else System.out.println("\nInvalid choice");

//		
		
//		
	}
	private static void createQRImage(File qrFile, String qrCodeText, int size,
			String fileType) throws WriterException, IOException {
		// Create the ByteMatrix for the QR-Code that encodes the given String
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
	}
private static String enterInput(int n){
	//n = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter number of inputs"));
	sb = new StringBuffer();
	for(int i=0; i<n ; i++){
		String text = (JOptionPane.showInputDialog(null, "Enter attribute: "+ (i+1)));
		if(text == null){
			JOptionPane.showMessageDialog(null, "Please enter something!");
		}
		else{
			sb.append(text);
			}
		}
	return sb.toString();
	
}
private static void setUpQRCodeDisplay(){
	
}

}
