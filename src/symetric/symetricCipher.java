package symetric;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;


public class symetricCipher {

	static byte[] plainText1 = new byte[] {0x30, 0x01, 0x05, 0x3A, 0x00, 0x28, 0x45, 0x2F, 0x1C, 0x6A, 0x4B, 0x05, 0x30, 0x20, 0x19, 0x0A};
	static byte[] plainText2 = new byte[] {0x30, 0x01, 0x05, 0x3A, 0x00, 0x28, 0x45, 0x2F};
	static byte[] plainText3 = new byte[] {0x30, 0x01, 0x05, 0x3A, 0x00, 0x28, 0x45, 0x2F, 0x1C, 0x6A, 0x4B, 0x05, 0x30, 0x20, 0x19, 0x0A, (byte)0xCB, (byte)0x89, 0x1B, (byte)0xE5, (byte)0xFF, 0x10, 0x30, 0x4A};
	
	static byte[] plainTextBT = new byte[]{0x42, 0x75, 0x73, 0x69, 0x6e, 0x65, 0x73, 0x73, 0x20, 0x54, 0x6f, 0x75, 0x72, 0x20, 0x4d, 0x61, 0x73, 0x74, 0x65, 0x72, 0x20, 0x3a, 0x20, 0x4d, 0x6f, 0x6e, 0x6e, 0x79};
	
	
	private static byte[] encryptAES(String data, SecretKeySpec sKeySpec) throws Exception {
	    Cipher cipher = Cipher.getInstance("AES");
	    cipher.init(Cipher.ENCRYPT_MODE, sKeySpec);
	    
	    byte[] dataCiphered = cipher.doFinal(data.getBytes());
	    return Base64.getEncoder().encode((dataCiphered));
	}
	
	private static String decryptAES(byte[] encryptedData, SecretKeySpec sKeySpec) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
		Cipher cipher = Cipher.getInstance("AES");
		cipher.init(Cipher.DECRYPT_MODE, sKeySpec);
		
		byte[] data = cipher.doFinal(Base64.getDecoder().decode(encryptedData));
				
		return new String(data).trim();
	}
	
	
	

	public static void main (String[] args){
		
		System.out.println("plainTextBT (ascii): " + new String(plainTextBT));
		System.out.println("plainTextBT (hex): " + Arrays.toString(plainTextBT));
		
		byte[] keyBytes = new byte[] {0x2F, 0x11, 0x1D, 0x00, 0x14, 0x0E, 0x14, 0x07,0x18, 0x19, 0x0A, 0x0F, 0x1C, 0x11, 0x3A, 0x1F};
		
		SecretKeySpec keyAES = new SecretKeySpec(keyBytes, "AES");
		String keyAESString = Base64.getEncoder().encodeToString(keyAES.getEncoded());
		System.out.println("keyAES: " + keyAESString);
		
		
		try {
			byte[] cipherTextBT = encryptAES(Arrays.toString(plainTextBT), keyAES);
			System.out.println("cipherTextBT (ascii) : " + new String(cipherTextBT));
			System.out.println("cipherTextBT (hex) : " + Arrays.toString(cipherTextBT));
			try {
				String decodePlainTextBT = decryptAES(cipherTextBT, keyAES);
				System.out.println("decodePlainTextBT (hex) : " + decodePlainTextBT);
				
				if(decodePlainTextBT.equals(Arrays.toString(plainTextBT))) {
					System.out.println("BIAAATCH");
				} else {
					System.out.println("FAIIL");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
	
}
