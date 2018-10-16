package symetric;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class CipherAesCBC {

	public static byte[] encryptAesCBC(byte[] plainTextBytes, SecretKeySpec sKeySpec) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
		// Generating IV.
		int ivByteSize = 16;
		byte[] ivBytes = new byte[ivByteSize];
		(new SecureRandom()).nextBytes(ivBytes);
		IvParameterSpec iv = new IvParameterSpec(ivBytes);
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
	
	public static byte[] decryptAesCBC(byte[] encryptedDataBytes, SecretKeySpec sKeySpec) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
		// Extract IV
		int ivByteSize = 16;
		byte[] ivBytes = new byte[ivByteSize];
		System.arraycopy(encryptedDataBytes, 0, ivBytes, 0, ivBytes.length);
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
}
