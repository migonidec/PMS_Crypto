package asymetric;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.KeySpec;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;


public class CipherRSA {
	
	static byte[] plainTextBT = new byte[]{0x42, 0x75, 0x73, 0x69, 0x6e, 0x65, 0x73, 0x73, 0x20, 0x54, 0x6f, 0x75, 0x72, 0x20, 0x4d, 0x61, 0x73, 0x74, 0x65, 0x72, 0x20, 0x3a, 0x20, 0x4d, 0x6f, 0x6e, 0x6e, 0x79};
	
	/**
	 * Generate an random 2048 bytes long RSA pair of keys
	 * @return the key pair (public and private)
	 * @throws NoSuchAlgorithmException
	 */
    public static KeyPair generateKeyPairRSA() throws NoSuchAlgorithmException {
        final int keySize = 2048;
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(keySize);      
        return keyPairGenerator.genKeyPair();
    }
    
    /**
     * Encrypts bytes data with a RSA public Key    
     * @param plainData 
     * @param publicKey 
     * @return a byte array with the encrypted data
     * @throws Exception
     */
    public static byte[] encryptRSA(byte[] plainData, PublicKey publicKey) throws Exception {
    	byte[] encryptedDataBytes;
    	Cipher cipher = Cipher.getInstance("RSA");
    	cipher.init(Cipher.ENCRYPT_MODE, publicKey);
    	encryptedDataBytes = cipher.doFinal(plainData);
    	return encryptedDataBytes;
    }
    
    public static byte[] decryptRSA(byte[] encrypteData, PrivateKey privateKey) throws Exception {
    	byte[] decryptedDataBytes;
    	Cipher decipher = Cipher.getInstance("RSA");
    	decipher.init(Cipher.DECRYPT_MODE, privateKey);
    	decryptedDataBytes = decipher.doFinal(encrypteData);
    	return decryptedDataBytes;
    }
    
    public static void rawBytesEncryptionDecryption(byte[] input) throws Exception {
    	KeyPair keyPair = generateKeyPairRSA();
		System.out.println("=== Private Key ===\n" + keyPair.getPrivate().getClass());
		System.out.println("=== Public Key ===\n " + keyPair.getPublic());
		//System.out.println("\n=== Plain Text ===\n" + "[ascii]: " + new String(input) + "\n [hex] : " + Arrays.toString(input));
		try {
			System.out.println("\n=== Text Encryption ===");
			byte[] cipherData = encryptRSA(input, keyPair.getPublic());
			System.out.println("[ascii]: " + new String(cipherData) + "\n [hex] : " + Arrays.toString(cipherData));
			try {
				System.out.println("\n=== Text Decryption ===");
				byte[] decodeCipherData = decryptRSA(cipherData, keyPair.getPrivate());
				System.out.println("[ascii]: " + new String(decodeCipherData) + "\n [hex] : " + Arrays.toString(decodeCipherData));

			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
    }
    
    public static void main (String[] args){
		try {
			rawBytesEncryptionDecryption(plainTextBT);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
