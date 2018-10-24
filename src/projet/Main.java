package projet;

import java.security.PublicKey;
import java.security.Security;
import java.util.Arrays;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import asymetric.DigitalSignECDSA;

public class Main {
	
	public static void print(String text) { System.out.println("\n=== " + text + " ==="); }
	
	public static void main (String[] args){
		Security.addProvider(new BouncyCastleProvider());
		
		//Text to exchange between Bob & Alice
		print("Plain Text");
		byte[] plainData = null;
		try { plainData = DigitalSignECDSA.readBytesFromFile("enic1.txt"); } catch (Exception e) { e.printStackTrace(); }
		System.out.println(new String(plainData));
		
		//Bob and Alice creation 
		User bob = new User("Bob");
		User alice = new User("Alice");
		
		//Generate the symetric key (Ks) for Bob & Alice exchange
		print("Bob generates a symetric key");
		bob.generateSymetricKey();
		
		//Alice shares her public key to bob. She could share it over the network for exemple
		print("Alice send her RSA public key to Bob");
		PublicKey alicePublicKeyRSA = alice.sendPublicKeyRSA();
		bob.receivePublicKeyRSA("Alice", alicePublicKeyRSA);
		
		print("Bob send his ECDSA public key to Bob");
		PublicKey bobPublicKeyECDSA = bob.sendPublicKeyECDSA();
		alice.receivePublicKeyECDSA("Bob", bobPublicKeyECDSA);
		
		//Bob encrypts the symetric key with Alice's public key
		print("Bob encrypt the symetric key w/ Alice's public key");
		byte[] encryptedSymKey = bob.encryptSymetricKey(alice);
		
		print("Alice decrypt the symetric key her private key");
		alice.decryptSymetricKey(encryptedSymKey);
		
		print("Bob sends encrypted data to Alice");
		byte[] encryptedData = bob.sendCryptedData(plainData);
		
		print("Bob sends data  signature to Alice");
		byte[] signatureData = bob.sendSignature(plainData);
		try { DigitalSignECDSA.writeSignature(signatureData);
		} catch (Exception e) { e.printStackTrace();}
		
		
		print("Alice receives encrypted data");
		//alice.receiveCryptedData(encryptedData, signatureData);
		alice.receiveCryptedData(encryptedData, "signature.sig");
		
		
	}

}
