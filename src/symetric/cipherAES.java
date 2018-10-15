package symetric;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class cipherAES {
	
	//https://www.programcreek.com/java-api-examples/index.php?source_dir=BigSecret-master/Cipher/src/edu/utdallas/bigsecret/cipher/AesCtr.java#
	
	static byte[] keyBytes = new byte[] {0x2F, 0x11, 0x1D, 0x00, 0x14, 0x0E, 0x14, 0x07,0x18, 0x19, 0x0A, 0x0F, 0x1C, 0x11, 0x3A, 0x1F};
	
	static byte[] plainText1 = new byte[] {0x30, 0x01, 0x05, 0x3A, 0x00, 0x28, 0x45, 0x2F, 0x1C, 0x6A, 0x4B, 0x05, 0x30, 0x20, 0x19, 0x0A};
	static byte[] plainText2 = new byte[] {0x30, 0x01, 0x05, 0x3A, 0x00, 0x28, 0x45, 0x2F};
	static byte[] plainText3 = new byte[] {0x30, 0x01, 0x05, 0x3A, 0x00, 0x28, 0x45, 0x2F, 0x1C, 0x6A, 0x4B, 0x05, 0x30, 0x20, 0x19, 0x0A, (byte)0xCB, (byte)0x89, 0x1B, (byte)0xE5, (byte)0xFF, 0x10, 0x30, 0x4A};
	static byte[] plainTextBT = new byte[]{0x42, 0x75, 0x73, 0x69, 0x6e, 0x65, 0x73, 0x73, 0x20, 0x54, 0x6f, 0x75, 0x72, 0x20, 0x4d, 0x61, 0x73, 0x74, 0x65, 0x72, 0x20, 0x3a, 0x20, 0x4d, 0x6f, 0x6e, 0x6e, 0x79};

	public enum AESmode {ECB, CBC, CTR};

	
	public static SecretKeySpec generateKeyAES(boolean random) throws NoSuchAlgorithmException {
		if(random == false) return (new SecretKeySpec(keyBytes, "AES"));
		else return (SecretKeySpec) KeyGenerator.getInstance("AES").generateKey();
	}
	

	public static byte[] encryptAES(byte[] plainTextBytes, boolean randomKey, AESmode mode) throws Exception {
		// Generating key.
		SecretKeySpec sKeySpec = generateKeyAES(randomKey);

		// Encrypt
		try {
			switch (mode) {
				case ECB:
					return cipherAesECB.encryptAesECB(plainTextBytes, sKeySpec);
				case CBC: 
					return cipherAesCBC.encryptAesCBC(plainTextBytes, sKeySpec);
				default:
					break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static byte[] decryptAES(byte[] encryptedDataBytes, SecretKeySpec sKeySpec, AESmode mode) throws Exception {
    	// Decrypt
		try {
			switch (mode) {
			case ECB:
				return cipherAesECB.decryptAesECB(encryptedDataBytes, sKeySpec);
			case CBC:
				return cipherAesCBC.decryptAesCBC(encryptedDataBytes, sKeySpec);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public static void rawBytesEncryptionDecryption(byte[] input) {
		AESmode mode = AESmode.CBC;
		
		System.out.println("=== Plain Text ===");
		System.out.println("[ascii]: " + new String(input));
		System.out.println(" [hex] : " + Arrays.toString(input));
		try {
			System.out.println("\n=== Text Encryption ===");
			byte[] cipherData = encryptAES(input, false, mode);
			System.out.println("[ascii]: " + new String(cipherData));
			System.out.println(" [hex] : " + Arrays.toString(cipherData));
			try {
				System.out.println("\n=== Text Decryption ===");
				byte[] decodeCipherData = decryptAES(cipherData, generateKeyAES(false), mode);
				System.out.println("[ascii]: " + new String(decodeCipherData));
				System.out.println(" [hex] : " + Arrays.toString(decodeCipherData));
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void fileEncryptionDecryption(String fileName) {
		System.out.println("\n=== File Encrypt/Decrypt ===");
		try {
			cipherAesFile.encryptFileAES(fileName);
			cipherAesFile.decryptFileAES(fileName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	public static void main (String[] args){
		rawBytesEncryptionDecryption(plainTextBT);
		fileEncryptionDecryption("test.txt");
	}
			

}
