package asymetric;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Security;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;
import org.bouncycastle.jce.ECNamedCurveTable;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

public class DigitalSignECDSA {

	public static KeyPair generateKeyPair() throws Exception{
		KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("ECDSA", "BC"); //BC for BouncyCastle Provider
		keyPairGenerator.initialize(ECNamedCurveTable.getParameterSpec("prime256v1"), new SecureRandom());
		return keyPairGenerator.generateKeyPair();
	}
	
	public static void writeKeys(KeyPair keyPair) throws Exception {
		byte[] publicKeyBytes = keyPair.getPublic().getEncoded();
		FileOutputStream publicFileOutput = new FileOutputStream("public.key");
		publicFileOutput.write(publicKeyBytes);
		publicFileOutput.close();
		
		byte[] privateKeyBytes = keyPair.getPrivate().getEncoded();
		FileOutputStream privateFileOutput = new FileOutputStream("private.key");
		privateFileOutput.write(privateKeyBytes);
		privateFileOutput.close();
	}
	
	public static byte[] readBytesFromFile(String fileName) throws Exception {
		FileInputStream fileInput = new FileInputStream(fileName);
		byte[] dataBytes = new byte[1024];
		int numberByteRead = 0;
		int oldNumberByteRead = numberByteRead;
		while (numberByteRead >= 0) { //numberByteRead=-1, end of file
			oldNumberByteRead = numberByteRead;
			numberByteRead = fileInput.read(dataBytes);	//read file and put it in dataBytes
		}
		fileInput.close();		
		return Arrays.copyOfRange(dataBytes, 0, oldNumberByteRead);

	}
	
	public static PublicKey readPublicKey(String fileName) throws Exception {
		byte[] publicKeyBytes = readBytesFromFile("public.key");
		KeyFactory keyFactory = KeyFactory.getInstance("ECDSA", "BC"); 
		return keyFactory.generatePublic(new X509EncodedKeySpec(publicKeyBytes));
	}
	
	public static PrivateKey readPrivateKey(String fileName) throws Exception {
		byte[] privateKeyByte = readBytesFromFile(fileName);
		KeyFactory keyFactory = KeyFactory.getInstance("ECDSA", "BC");
		return keyFactory.generatePrivate(new PKCS8EncodedKeySpec(privateKeyByte));
	}
	
	
	public static byte[] generateSignature(byte[] plainData, PrivateKey privateKey) throws Exception {
		Signature sigatureECDSA = Signature.getInstance("SHA256withECDSA", "BC"); //BC for BouncyCastle Provider
		sigatureECDSA.initSign(privateKey);
		sigatureECDSA.update(plainData);
		return sigatureECDSA.sign();
	}
	
	public static boolean validateSignature(byte[] plainData, PublicKey publicKey, byte[] signature) throws Exception{
		Signature sigatureECDSA = Signature.getInstance("SHA256withECDSA", "BC");
		sigatureECDSA.initVerify(publicKey);
		sigatureECDSA.update(plainData);
		return sigatureECDSA.verify(signature);
	}
	
	public static void writeSignature(byte[] signature) throws Exception{
		FileOutputStream privateFileOutput = new FileOutputStream("signature.sig");
		privateFileOutput.write(signature);
		privateFileOutput.close();
	}
	

	

	public static void main (String[] args) throws Exception{
		Security.addProvider(new BouncyCastleProvider());
		
		System.out.println("=== Generates Key Pair ===");
		KeyPair keyPair = generateKeyPair();
		
		System.out.println("=== Writes keys on HDD ===");
		writeKeys(keyPair);
		
		System.out.println("=== Generates test file signature ===");
		//byte[] signature = generateSignatureFile("test.txt", keyPair.getPrivate());
		byte[] signature = generateSignature(readBytesFromFile("test.txt"), readPrivateKey("private.key"));

		System.out.println("=== Writes signature on HDD ===");
		writeSignature(signature);
				
		System.out.println("=== Tests the file signature ===");
		boolean isValidated = validateSignature(readBytesFromFile("test.txt"), readPublicKey("public.key"), signature);
		System.out.println("Result: " + isValidated);
		
	}
}
