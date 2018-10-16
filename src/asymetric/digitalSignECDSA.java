package asymetric;

import java.io.IOException;
import java.math.BigInteger;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;

import org.bouncycastle.jce.spec.ECParameterSpec;
import org.bouncycastle.math.ec.ECCurve;

public class digitalSignECDSA {
	
	public static KeyPair generateKeyPairECDSA() {
			
		return null;
	}
	
	/*
    public void setCurve() { 
        try { 
            curve = new ECCurve.Fp( 
                    new BigInteger("FFFFFFFFFFFFFFFF00000001FFFFFFFFFFFFFFFF", 16),  // p  
                    new BigInteger("FFFFFFFFFFFFFFFF00000001FFFFFFFFFFFFFFFC", 16),  // a 
                    new BigInteger("A68BEDC33418029C1D3CE33B9A321FCCBB9E0F0B", 16)); // b 
             
            spec = new ECParameterSpec(curve,  
                    curve.decodePoint(Hex.decode("128EC456487FD8FDF64E2437BC0A1F6D5AFDE2C5958557EB1DB001260425524DBC379D5AC5F4ADF")), // G 
                    new BigInteger("00FFFFFFFFFFFFFFFEFFFFB5AE3C523E63944F2127")); // n 
             
            g = KeyPairGenerator.getInstance("ECDSA", "BC"); 
            f = KeyFactory.getInstance("ECDSA", "BC"); 
            g.initialize(spec, new SecureRandom()); 
             
            keyPair = g.generateKeyPair(); 
        } catch (Exception e) { 
        } 
    } 
    */
	public static void main (String[] args){
		try {
			generateKeyPairECDSA();	
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
