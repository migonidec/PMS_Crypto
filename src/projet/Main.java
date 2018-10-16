package projet;

import symetric.CipherAES;

public class Main {
	
	public static void main (String[] args){
		byte[] plainData = new byte[]{0x42, 0x75, 0x73, 0x69, 0x6e, 0x65, 0x73, 0x73, 0x20, 0x54, 0x6f, 0x75, 0x72, 0x20, 0x4d, 0x61, 0x73, 0x74, 0x65, 0x72, 0x20, 0x3a, 0x20, 0x4d, 0x6f, 0x6e, 0x6e, 0x79};

		User bob = new User("Bob", "IMT");
		User alice = new User("Alice", "Lockup");
		
		bob.generateSymetricKey();
		
		bob.receivePublicKey(alice.getName(), alice.sendPublicKey());
		System.out.println(alice.keyPair.getPublic());
		System.out.println(bob.keySet.get(alice.getName()));
		
		byte[] encryptedSymKey = bob.encryptSymetricKey(alice);
		alice.decryptSymetricKey(encryptedSymKey);
		System.out.println(bob.keySym.toString());
		System.out.println(alice.keySym.toString());
		
		byte[] encryptedData = bob.sendCryptedData(plainData);
		alice.receiveCryptedData(encryptedData);
	}

}
