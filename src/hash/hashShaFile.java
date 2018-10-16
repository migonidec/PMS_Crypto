package hash;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class hashShaFile {

	public static String calculateSHAfile(String fileName) throws NoSuchAlgorithmException, IOException {

		MessageDigest digest = MessageDigest.getInstance("SHA-256");
		FileInputStream fileInput = new FileInputStream(fileName);
		//Data recuperation
		byte[] dataBytes = new byte[1024];
		int numberByteRead = 0; 
		while (numberByteRead >= 0) {
			digest.update(dataBytes, 0, numberByteRead);
			numberByteRead = fileInput.read(dataBytes);
		}
		fileInput.close();

		//Digest
		byte[] digestBytes = digest.digest();
		
		//convert the byte to hex format method
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < digestBytes.length; i++) {
			buffer.append(Integer.toString((digestBytes[i] & 0xff) + 0x100, 16).substring(1));
		}
 
		System.out.println("Hex format Hash Value of " + fileName + " is: "+ buffer.toString());
		return buffer.toString();
	}
	
	public static void main (String[] args){
		try {
			calculateSHAfile("test.txt");	
		} catch (NoSuchAlgorithmException | IOException e) {
			e.printStackTrace();
		}
	}
	
}
