package projet;

import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import asymetric.CipherRSA;
import asymetric.DigitalSignECDSA;
import symetric.CipherAES;
import symetric.CipherAES.AESmode;

public class User {

	private String name; 
	private KeyPair keyPairRSA;
	private KeyPair keyPairECDSA;
	private SecretKeySpec keySym;
	private Map<String, PublicKey> keySetRSA = new LinkedHashMap<>();
	private Map<String, PublicKey> keySetECDSAS = new LinkedHashMap<>();
	
	public User(String name) {
		try { 
			this.name = name;
			this.keyPairRSA = CipherRSA.generateKeyPairRSA();
			this.keyPairECDSA = DigitalSignECDSA.generateKeyPair();
		} catch (Exception e) { e.printStackTrace();}
	}
	
	public String getName() {
		return name;
	}
	
	/**
	 * Sends the public Key to a specified user.
	 * @param user the User that needs to receive the key
	 */
	public void sendPublicKeyRSA(User user) {
		if(user != null) user.receivePublicKeyRSA(this.name, this.keyPairRSA.getPublic());
	}
	
	public PublicKey sendPublicKeyRSA() { return this.keyPairRSA.getPublic(); }
	
	public PublicKey sendPublicKeyECDSA() { return this.keyPairECDSA.getPublic(); }
	
	/**
	 * Method to store another User's public key into the keySet
	 * @param name Identity of the other user
	 * @param publicKey Public key of the other user
	 */
	public void receivePublicKeyRSA(String name, PublicKey publicKey) {
		this.keySetRSA.put(name, publicKey);
	}
	
	public void receivePublicKeyECDSA(String name, PublicKey publicKey) {
		this.keySetECDSAS.put(name, publicKey);
	}
	
	/**
	 * Generates a random symetric key and stores it in the User attributes this.keySym
	 */
	public void generateSymetricKey() {
		try { this.keySym = CipherAES.generateKeyAES(true);} 
		catch (NoSuchAlgorithmException e) { e.printStackTrace();}
	}
	
	/**
	 * Encryptes a symetric key with a specified user public key
	 * @param user the user that needs to be able to decrypt the symetric key
	 * @return the encrypted symetric key
	 */
	public byte[] encryptSymetricKey(User user) {
		byte[] keySymBytes = this.keySym.getEncoded();
		//if the specified user's Public key can be found in the keySet
		if(this.keySetRSA.containsKey(user.getName())) {
			try {
				byte[]encryptedSymKey = CipherRSA.encryptRSA(keySymBytes, this.keySetRSA.get(user.getName()));
				return encryptedSymKey;
			} catch (Exception e) {
				e.printStackTrace();
			}
		} 
		return null;
	}
	
	public byte[] encryptSymetricKey(PublicKey publicKey) {
		byte[] keySymBytes = this.keySym.getEncoded();
		try {
			byte[]encryptedSymKey = CipherRSA.encryptRSA(keySymBytes, publicKey);
			System.out.println("encryptedSymKey (hash) : " + encryptedSymKey.hashCode());
			return encryptedSymKey;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void decryptSymetricKey(byte[] encryptedSymKey) {
		try {
			byte[] keySymBytes = CipherRSA.decryptRSA(encryptedSymKey, this.keyPairRSA.getPrivate());
			this.keySym = new SecretKeySpec(keySymBytes, 0, keySymBytes.length, "AES");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public byte[] sendCryptedData(byte[] plainData) {
		try {
			return CipherAES.encryptAES(plainData, this.keySym, AESmode.CBC);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public void receiveCryptedData(byte[] encryptedData, byte[] signature) {
		try {
			byte[] decryptedData = CipherAES.decryptAES(encryptedData, this.keySym, AESmode.CBC);
			System.out.println(new String(decryptedData));
			if(DigitalSignECDSA.validateSignature(decryptedData, this.keySetECDSAS.get("Bob"), signature)) {
				System.out.println("The received data hasn't been alterated");
			} else {
				System.out.println("The received data has been alterated");
			}
		} catch (Exception e) {
			e.printStackTrace();	
		}
	}
	
	public void receiveCryptedData(byte[] encryptedData, String signatureFileName) {
		try {
			receiveCryptedData(encryptedData, DigitalSignECDSA.readBytesFromFile(signatureFileName));
		} catch (Exception e) { e.printStackTrace(); }	
	}
	
	
	
	public byte[] sendSignature(byte[] plainData) {
		try {
			return DigitalSignECDSA.generateSignature(plainData, this.keyPairECDSA.getPrivate());
		} catch (Exception e) {
			e.printStackTrace();	
		}
		return null; 
	}
	
	
	
}
