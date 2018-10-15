package symetric;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class cipherEAS {
	
	static byte[] keyBytes = new byte[] {0x2F, 0x11, 0x1D, 0x00, 0x14, 0x0E, 0x14, 0x07,0x18, 0x19, 0x0A, 0x0F, 0x1C, 0x11, 0x3A, 0x1F};
	
	static byte[] plainText1 = new byte[] {0x30, 0x01, 0x05, 0x3A, 0x00, 0x28, 0x45, 0x2F, 0x1C, 0x6A, 0x4B, 0x05, 0x30, 0x20, 0x19, 0x0A};
	static byte[] plainText2 = new byte[] {0x30, 0x01, 0x05, 0x3A, 0x00, 0x28, 0x45, 0x2F};
	static byte[] plainText3 = new byte[] {0x30, 0x01, 0x05, 0x3A, 0x00, 0x28, 0x45, 0x2F, 0x1C, 0x6A, 0x4B, 0x05, 0x30, 0x20, 0x19, 0x0A, (byte)0xCB, (byte)0x89, 0x1B, (byte)0xE5, (byte)0xFF, 0x10, 0x30, 0x4A};
	static byte[] plainTextBT = new byte[]{0x42, 0x75, 0x73, 0x69, 0x6e, 0x65, 0x73, 0x73, 0x20, 0x54, 0x6f, 0x75, 0x72, 0x20, 0x4d, 0x61, 0x73, 0x74, 0x65, 0x72, 0x20, 0x3a, 0x20, 0x4d, 0x6f, 0x6e, 0x6e, 0x79};
	
	
	public enum AESmode {ECB, CBC, CTR};

	
	private static SecretKeySpec generateKeyAES(boolean random) throws NoSuchAlgorithmException {
		if(random == false) return (new SecretKeySpec(keyBytes, "AES"));
		else return (SecretKeySpec) KeyGenerator.getInstance("AES").generateKey();
	}
	
	private static byte[] encryptAesECB(byte[] plainTextBytes, SecretKeySpec sKeySpec) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		byte[] encryptedTextBytes;
		Cipher cipher = Cipher.getInstance("AES");
		cipher.init(Cipher.ENCRYPT_MODE, sKeySpec);
		encryptedTextBytes = cipher.doFinal(plainTextBytes);
		return encryptedTextBytes;
	}
	
	private static byte[] encryptAesCBC(byte[] plainTextBytes, SecretKeySpec sKeySpec) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
		// Generating IV.
    	int ivByteSize = 16;
    	byte[] ivBytes = new byte[ivByteSize];
    	(new SecureRandom()).nextBytes(ivBytes);
        IvParameterSpec iv = new IvParameterSpec(ivBytes);
    	System.out.println("ivBytes : " + Arrays.toString(ivBytes));
    	// Encrypt
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		cipher.init(Cipher.ENCRYPT_MODE, sKeySpec, iv);
		byte[] encryptedTextBytes = cipher.doFinal(plainTextBytes);
		// Combine IV and encrypted
		byte[] encryptedIVAndText = new byte[ivByteSize + encryptedTextBytes.length];
		System.arraycopy(ivBytes, 0, encryptedIVAndText, 0, ivByteSize);
		System.arraycopy(encryptedTextBytes, 0, encryptedIVAndText, ivByteSize, encryptedTextBytes.length);
		return encryptedIVAndText;

	}
	
    public static byte[] encryptAES(byte[] plainTextBytes, boolean randomKey, AESmode mode) throws Exception {
        // Generating key.
        SecretKeySpec sKeySpec = generateKeyAES(randomKey);

        // Encrypt
        try {
			switch (mode) {
				case ECB:
					return encryptAesECB(plainTextBytes, sKeySpec);
				case CBC: 
					return encryptAesCBC(plainTextBytes, sKeySpec);
				default:
					break;
			}
        } catch (Exception e) {
        	e.printStackTrace();
		}
        return null;
    }
    
    private static  byte[] decryptAesECB(byte[] encryptedDataBytes, SecretKeySpec sKeySpec) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
    	Cipher decipher = Cipher.getInstance("AES");
		decipher.init(Cipher.DECRYPT_MODE, sKeySpec);
		byte[] decryptedTextBytes = decipher.doFinal(encryptedDataBytes);
		return decryptedTextBytes;
    }
    
    private static byte[] decryptAesCBC(byte[] encryptedDataBytes, SecretKeySpec sKeySpec) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
    	// Extract IV
    	int ivByteSize = 16;
    	byte[] ivBytes = new byte[ivByteSize];
    	System.arraycopy(encryptedDataBytes, 0, ivBytes, 0, ivBytes.length);
    	System.out.println("ivBytes : " +  Arrays.toString(ivBytes));
    	IvParameterSpec ivParameterSpec = new IvParameterSpec(ivBytes);
    
    	// Extract encrypted part (without IV)
		int encryptedTextSize = encryptedDataBytes.length - ivByteSize;
		byte[] encryptedTextBytes = new byte[encryptedTextSize];
		System.arraycopy(encryptedDataBytes, ivByteSize, encryptedTextBytes, 0, encryptedTextSize);

		//Decrypt
		Cipher decipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		decipher.init(Cipher.DECRYPT_MODE, sKeySpec, ivParameterSpec);
		byte[] decryptedTextBytes = decipher.doFinal(encryptedTextBytes);
		return decryptedTextBytes;
    }
    
    public static byte[] decryptAES(byte[] encryptedDataBytes, SecretKeySpec sKeySpec, AESmode mode) throws Exception {
        // Decrypt
        try {
        	switch (mode) {
    		case ECB:
    			return decryptAesECB(encryptedDataBytes, sKeySpec);
    		case CBC:
    			return decryptAesCBC(encryptedDataBytes, sKeySpec);
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
    
	public static void main (String[] args){
		rawBytesEncryptionDecryption(plainTextBT);
		
	}

}
