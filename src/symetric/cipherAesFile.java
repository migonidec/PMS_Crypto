package symetric;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;

public class cipherAesFile {
	
	public static void encryptFileAES(String fileName) throws Exception {
		Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
		cipher.init(Cipher.ENCRYPT_MODE, cipherAES.generateKeyAES(false));
		FileOutputStream fileOutput = new FileOutputStream(fileName);
		CipherOutputStream cipherOutput = new CipherOutputStream(fileOutput, cipher);

		try {
			cipherOutput.write("[Hello:Okay]\nOkay".getBytes());
			//cipherOutput.write(fileInput.read());
			cipherOutput.flush();
			cipherOutput.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void decryptFileAES(String fileName) throws Exception{
		Cipher decipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
		decipher.init(Cipher.DECRYPT_MODE, cipherAES.generateKeyAES(false));
		FileInputStream fileInput = new FileInputStream(fileName);
		CipherInputStream cipherInput = new CipherInputStream(fileInput,decipher);
		
		ByteArrayOutputStream byteArrayOutput = new ByteArrayOutputStream();
		byte[] byteArray = new byte[1024];
		int numberOfBytedRead = 0;
		while(numberOfBytedRead >= 0) {
			byteArrayOutput.write(byteArray, 0, numberOfBytedRead);
			numberOfBytedRead = cipherInput.read(byteArray);
		}
		System.out.println(new String(byteArrayOutput.toByteArray()));
	}
	
	
}
