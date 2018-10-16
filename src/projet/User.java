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
	private String password; 
	public KeyPair keyPair;
	public SecretKeySpec keySym;
	public Map<String, PublicKey> keySet = new LinkedHashMap<>(); 
	public byte[] encryptedKeySym;
	
	public User(String name, String password) {
		this.name = name;
		this.password = password;
		try { this.keyPair = CipherRSA.generateKeyPairRSA();
		} catch (NoSuchAlgorithmException e) { e.printStackTrace();}
	}
	
	public String getName() {
		return name;
	}
	
	public PublicKey sendPublicKey() {
		return this.keyPair.getPublic();
	}
	
	public void receivePublicKey(String name, PublicKey publicKey) {
		this.keySet.put(name, publicKey);
	}
	
	public void generateSymetricKey() {
		try { this.keySym = CipherAES.generateKeyPwdAES(this.password); } 
		catch (NoSuchAlgorithmException e) { e.printStackTrace();}
	}
	
	public byte[] encryptSymetricKey(User user) {
		byte[] keySymBytes = this.keySym.getEncoded();
		if(this.keySet.containsKey(user.getName())) {
			try {
				byte[]encryptedSymKey = CipherRSA.encryptRSA(keySymBytes, this.keySet.get(user.getName()));
				return encryptedSymKey;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	public void decryptSymetricKey(byte[] encryptedSymKey) {
		try {
			byte[] keySymBytes = CipherRSA.decryptRSA(encryptedSymKey, this.keyPair.getPrivate());
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
	
	public void receiveCryptedData(byte[] encryptedData) {
		try {
			byte[] decryptedData = CipherAES.decryptAES(encryptedData, this.keySym, AESmode.CBC);
			System.out.println(new String(decryptedData));
		} catch (Exception e) {
			e.printStackTrace();	
		}
	}
	
}
