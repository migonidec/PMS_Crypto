package projet;

import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import asymetric.CipherRSA;
import symetric.CipherAES;
import symetric.CipherAES.AESmode;

public class User {

	private String name; 
	private KeyPair keyPair;
	private SecretKeySpec keySym;
	private Map<String, PublicKey> keySet = new LinkedHashMap<>(); 
	
	public User(String name) {
		try { 
			this.name = name;
			this.keyPair = CipherRSA.generateKeyPairRSA();
		} catch (NoSuchAlgorithmException e) { e.printStackTrace();}
	}
	
	public String getName() {
		return name;
	}
	
	/**
	 * Sends the public Key to a specified user.
	 * @param user the User that needs to receive the key
	 */
	public void sendPublicKey(User user) {
		if(user != null) user.receivePublicKey(this.name, this.keyPair.getPublic());
	}
	
	public PublicKey sendPublicKey() { return this.keyPair.getPublic(); }
	
	/**
	 * Method to store another User's public key into the keySet
	 * @param name Identity of the other user
	 * @param publicKey Public key of the other user
	 */
	public void receivePublicKey(String name, PublicKey publicKey) {
		System.out.println(name +  " public key (hash) seen from " + this.name + " : " + publicKey.hashCode());
		this.keySet.put(name, publicKey);
	}
	
	/**
	 * Generates a random symetric key and stores it in the User attributes this.keySym
	 */
	public void generateSymetricKey() {
		try { this.keySym = CipherAES.generateKeyAES(true); System.out.println("keySym (hash) : " + this.keySym.hashCode());} 
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
		if(this.keySet.containsKey(user.getName())) {
			try {
				byte[]encryptedSymKey = CipherRSA.encryptRSA(keySymBytes, this.keySet.get(user.getName()));
				System.out.println("encryptedSymKey (hash) : " + encryptedSymKey.hashCode());
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
			byte[] keySymBytes = CipherRSA.decryptRSA(encryptedSymKey, this.keyPair.getPrivate());
			this.keySym = new SecretKeySpec(keySymBytes, 0, keySymBytes.length, "AES");
			System.out.println("keySym (hash) : " + this.keySym.hashCode());
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
	
	public void receiveCryptedData(byte[] encryptedData) {
		try {
			byte[] decryptedData = CipherAES.decryptAES(encryptedData, this.keySym, AESmode.CBC);
			System.out.println(new String(decryptedData));
		} catch (Exception e) {
			e.printStackTrace();	
		}
	}
	
}
