package projet;

import java.security.PublicKey;

public class Main {
	
	public static void main (String[] args){
		
		//Text to exchange between Bob & Alice
		byte[] plainData = new byte[]{0x42, 0x75, 0x73, 0x69, 0x6e, 0x65, 0x73, 0x73, 0x20, 0x54, 0x6f, 0x75, 0x72, 0x20, 0x4d, 0x61, 0x73, 0x74, 0x65, 0x72, 0x20, 0x3a, 0x20, 0x4d, 0x6f, 0x6e, 0x6e, 0x79};
		System.out.println("=== Plain Text ===\n" + new String(plainData));
		
		//Bob and Alice creation 
		User bob = new User("Bob");
		User alice = new User("Alice");
		
		//Generate the symetric key (Ks) for Bob & Alice exchange
		System.out.println("=== Bob generates a symetric key ===");
		bob.generateSymetricKey();
		
		//Alice shares her public key to bob. She could share it over the network for exemple
		System.out.println("=== Alice send her public key to Bob ===");
		//alice.sendPublicKey(bob);
		PublicKey alicePublicKey = alice.sendPublicKey();
		bob.receivePublicKey("Alice", alicePublicKey);
		
		//Bob encrypts the symetric key with Alice's public key
		System.out.println("=== Bob encrypt the symetric key w/ Alice's public key ===");
		byte[] encryptedSymKey = bob.encryptSymetricKey(alice);
		
		System.out.println("=== Alice decrypt the symetric key her private key ===");
		alice.decryptSymetricKey(encryptedSymKey);
		
		System.out.println("=== Bob sends encrypted data to Alice ===");
		byte[] encryptedData = bob.sendCryptedData(plainData);
		System.out.println("Encrypted data (hash) : " + encryptedData.hashCode());
		
		System.out.println("=== Alice receives encrypted ===");
		alice.receiveCryptedData(encryptedData);
		
	}

}
