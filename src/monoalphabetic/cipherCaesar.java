package monoalphabetic;

public class CipherCaesar{
	
	static private int MAJCONST = 65;
	static private int MINCONST = 97;
	
	static private String cipher = "atgdxstugpcrtthiateajhejxhhpciegxcrtstatjgdetxacpedxcistbxcthsdgrdbbtatgdxsthepvcthdckdxhxcbpxhxapeajhstgxrwthhthfjtajxepgrtfjxaathixgtstapkpcxitsththhjytiheajhxctejxhpqatfjtathbxcthdcajxpkjtcigtegtcsgtdjhdjitcxgstvgpcsthvitggthcpnpcispjigthudcshfjtsthixigthswdcctjgpktcsgttiepgjcegdsxv�stadgvitxawjbpxchthigdjethhtigdjkpxtciepntthhtheaprthbjcxthtihthuadiithtfjxetth";
		
	private static int caesarShift(String text) {
		int maxLetter = CryptoUtils.highestLetterFreq(text).charAt(0);
		return (maxLetter-4);
	}
	
	public static String caesarDeCypher(String cipher, int shift) {
		
		char[] cipherToCharArray = cipher.toCharArray();
		char[] plainTxt = new char[cipherToCharArray.length];
		int decalage = 15;
		
		for(int i=0; i<cipherToCharArray.length; i++) {
			
			int cipheredCharInt = cipherToCharArray[i] - MINCONST;

			int plainCharInt = ((cipheredCharInt-decalage)%26);
			if (plainCharInt<0) plainCharInt += 26;
			
			plainCharInt += MINCONST;

			plainTxt[i] = (char) plainCharInt;
			
		}
			
		return String.valueOf(plainTxt); 
	}
	
	
	
	public static void main (String[] args){
		System.out.println(caesarDeCypher(cipher, caesarShift(cipher)));
	}

}
