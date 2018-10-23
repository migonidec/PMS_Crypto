package hash;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import monoalphabetic.CryptoUtils;

public class HashShaFile {

	public static String calculateSHAfile(String fileName) throws NoSuchAlgorithmException, IOException {

		MessageDigest digest = MessageDigest.getInstance("SHA-256");
		FileInputStream fileInput = new FileInputStream(fileName);
		
		//Data recuperation
		byte[] dataBytes = new byte[1024];
		int numberByteRead = 0; 
		while (numberByteRead >= 0) { //numberByteRead=-1, end of file
			digest.update(dataBytes, 0, numberByteRead);
			numberByteRead = fileInput.read(dataBytes);	//read file and put it in dataBytes
		}
		fileInput.close();

		//Digest
		byte[] digestBytes = digest.digest();
		
		//Convert the byte to hex format
		return CryptoUtils.bytesToHex(digestBytes).toUpperCase();
	}
	
	public static void main (String[] args){
		try {
			System.out.println(calculateSHAfile("test.txt"));	
		} catch (NoSuchAlgorithmException | IOException e) {
			e.printStackTrace();
		}
	}
	
}
