package symetric;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

public class cipherAesECB {

	public static byte[] encryptAesECB(byte[] plainTextBytes, SecretKeySpec sKeySpec) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		byte[] encryptedTextBytes;
		Cipher cipher = Cipher.getInstance("AES");
		cipher.init(Cipher.ENCRYPT_MODE, sKeySpec);
		encryptedTextBytes = cipher.doFinal(plainTextBytes);
		return encryptedTextBytes;
	}
	
	public static  byte[] decryptAesECB(byte[] encryptedDataBytes, SecretKeySpec sKeySpec) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		Cipher decipher = Cipher.getInstance("AES");
		decipher.init(Cipher.DECRYPT_MODE, sKeySpec);
		byte[] decryptedTextBytes = decipher.doFinal(encryptedDataBytes);
		return decryptedTextBytes;
	}
}
