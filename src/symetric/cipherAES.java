package symetric;

import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;

public class CipherAES {
	
	//https://www.programcreek.com/java-api-examples/index.php?source_dir=BigSecret-master/Cipher/src/edu/utdallas/bigsecret/cipher/AesCtr.java#
	
	static byte[] keyBytes = new byte[] {0x2F, 0x11, 0x1D, 0x00, 0x14, 0x0E, 0x14, 0x07,0x18, 0x19, 0x0A, 0x0F, 0x1C, 0x11, 0x3A, 0x1F};
	
	static byte[] plainText1 = new byte[] {0x30, 0x01, 0x05, 0x3A, 0x00, 0x28, 0x45, 0x2F, 0x1C, 0x6A, 0x4B, 0x05, 0x30, 0x20, 0x19, 0x0A};
	static byte[] plainText2 = new byte[] {0x30, 0x01, 0x05, 0x3A, 0x00, 0x28, 0x45, 0x2F};
	static byte[] plainText3 = new byte[] {0x30, 0x01, 0x05, 0x3A, 0x00, 0x28, 0x45, 0x2F, 0x1C, 0x6A, 0x4B, 0x05, 0x30, 0x20, 0x19, 0x0A, (byte)0xCB, (byte)0x89, 0x1B, (byte)0xE5, (byte)0xFF, 0x10, 0x30, 0x4A};
	static byte[] plainTextBT = new byte[]{0x42, 0x75, 0x73, 0x69, 0x6e, 0x65, 0x73, 0x73, 0x20, 0x54, 0x6f, 0x75, 0x72, 0x20, 0x4d, 0x61, 0x73, 0x74, 0x65, 0x72, 0x20, 0x3a, 0x20, 0x4d, 0x6f, 0x6e, 0x6e, 0x79};

	public enum AESmode {ECB, CBC, CTR};

	public static SecretKeySpec generateKeyPwdAES(String pwd) throws NoSuchAlgorithmException {
		System.arraycopy(pwd.getBytes(), 0, keyBytes, pwd.getBytes().length, pwd.getBytes().length);
		return generateKeyAES(false);
	}
	
	public static SecretKeySpec generateKeyAES(boolean random) throws NoSuchAlgorithmException {
		if(random == false) return (new SecretKeySpec(keyBytes, "AES"));
		else return (SecretKeySpec) KeyGenerator.getInstance("AES").generateKey();
	}
	

	public static byte[] encryptAES(byte[] plainTextBytes, SecretKeySpec sKeySpec, AESmode mode) throws Exception {

		// Encrypt
		try {
			switch (mode) {
				case ECB:
					return CipherAesECB.encryptAesECB(plainTextBytes, sKeySpec);
				case CBC: 
					return CipherAesCBC.encryptAesCBC(plainTextBytes, sKeySpec);
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
				return CipherAesECB.decryptAesECB(encryptedDataBytes, sKeySpec);
			case CBC:
				return CipherAesCBC.decryptAesCBC(encryptedDataBytes, sKeySpec);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public static void rawBytesEncryptionDecryption(byte[] input) throws Exception {
		AESmode mode = AESmode.CBC;
		
		//SecretKeySpec sKeySpec = generateKeyAES(false);
		SecretKeySpec sKeySpec = generateKeyPwdAES("Adrien");
		
		System.out.println("=== Plain Text ===");
		System.out.println("[ascii]: " + new String(input));
		System.out.println(" [hex] : " + Arrays.toString(input));
		try {
			System.out.println("\n=== Text Encryption ===");
			byte[] cipherData = encryptAES(input, sKeySpec, mode);
			System.out.println("[ascii]: " + new String(cipherData));
			System.out.println(" [hex] : " + Arrays.toString(cipherData));
			try {
				System.out.println("\n=== Text Decryption ===");
				byte[] decodeCipherData = decryptAES(cipherData, sKeySpec, mode);
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
			CipherAesFile.encryptFileAES(fileName);
			CipherAesFile.decryptFileAES(fileName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	public static void main (String[] args){
		try {
			rawBytesEncryptionDecryption(plainTextBT);
			fileEncryptionDecryption("test.txt");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
			

}
