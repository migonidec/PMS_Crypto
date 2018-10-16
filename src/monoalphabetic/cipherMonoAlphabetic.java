package monoalphabetic;

import java.util.*;

public class CipherMonoAlphabetic {
	
	private static String cipher = "pbdfqqf fqrxq crxsf hfkufkdf fccf fkq yfkq fquf fqf ibcxf ebkd rvbed sfir fdmkxddf pfqqf yfqxqf gxzkuf dbwtuf pbdfqqf fqrxq wrxzuf fq tcfwf fccf rvrxq yufd sf hkxq red be ckx fe fkq sbeef r yfxef dxn dfd zuresd ofkn fegbepfd sred kef dbuqf sbwtuf yubgbesf fqrxfeq yufdmkf fqfxeqd r gbupf srvbxu ycfkuf cfd pbxed sf dr tbkphf rvrxfeq pfqqf pbkutf sf c rezbxddf hrtxqkfccf mk be btdfuvf phfa cfdpbesrwefd fq phfa cfd wrcrsfd sfdfdyfufd dfd wrxed fqrxfeq pbwwf dr wfuf c rvrxq sfvxef yfuskfd s fezfckufd cf gfk mkx c fpcrxurxq fe pf wbwfeq grxdrxq drxccxu cfd rezcfd sf dfd bd fq ufesrxq dr wrxzufku rggufkdfwfeq vxdxtcf pbwwf fccf zufcbqrxq qbkibkud fccf rvrxq yuxd c hrtxqksf sf dfuufu dfd sfkn zfebkn c ke pbequf c rkquf qbkq dbe vfqfwfeq e fqrxq mk ke hrxccbe mkx fkq grxq yxqxf c fqf fq mkx grxdrxq hbuufku c hxvfu fccf e rvrxq dku fccf mkf sf cr qbxcf qubkff yrd ke phxggbe sf crxef be vborxq dr yfrk pr fq cr fq c be o sxdqxezkrxq yruqbkq sfd qrphfd tcfkfd bk ebxufd mkx xesxmkrxfeq cfd fesubxqd bk cr qhferusxfu c rvrxq qbkphff dfd irwtfd ekfd fqrxfeq ubkzfd fq zufcfd cf pufkn sf dfd pcrvxpkcfd fqrxq r grxuf ycfkufu qbkqf cr yfudbeef sf pfqqf fegreq dbe rcckuf dbe rqqxqksf cf dbe sf dr vbxn dfd xeqfuvrccfd fequf ke wbq fq c rkquf dbe ufzrus dbe dxcfepf dbe wbxesuf zfdqf fnyuxwrxfeq fq qurskxdrxfeq kef dfkcf xsff cr purxeqf cr purxeqf fqrxq ufyreskf dku fccf fccf fe fqrxq ybku rxedx sxuf pbkvfuqf cr purxeqf urwferxq dfd pbksfd pbequf dfd hrephfd ufqxurxq dfd qrcbed dbkd dfd ikyfd ckx grxdrxq qfexu cf wbxed sf ycrpf ybddxtcfef ckx crxddrxq sf dbkggcf mkf cf efpfddrxuf fq fqrxq sfvfekf pf mkbe ybkuurxq ryyfcfu dbe hrtxqksf sf pbuyddred vruxrqxbe ybddxtcf mkf s rkzwfeqfu xc o rvrxq rk gbes sf dr yukefccf ke pbxe fqbeef bk fqrxq cr qfuufku";
	
	private static Character[] alphabet = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};	
	
	private static Map<Character, Character> alphabetCorrespDecipher = new LinkedHashMap<>(); 
	
	private static Character[] alphabetShuffler() {
		Character[] alphabetShuffled = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};
		java.util.List<Character> alphabetShuffledList = Arrays.asList(alphabetShuffled);
		Collections.shuffle(alphabetShuffledList);
		return alphabetShuffled;
	}
	
	private static void monoalphabeticDecipher(String text) {
		char[] textArray = text.toCharArray();
		
		for(int i=0; i<text.length(); i++) {
			char currentLetter = textArray[i];
			if(alphabetCorrespDecipher.containsKey(currentLetter)) textArray[i] = alphabetCorrespDecipher.get(currentLetter);
		}
		
		System.out.println(textArray);
		
	}

	private static void alphabetDetermination(String text) {
		Map<String, Integer> letterRankedMap = CryptoUtils.letterFreqRanked(text);
		Character[] letterRankedArray = new Character[26];
		Iterator it = letterRankedMap.keySet().iterator();
		for(int i=0; it.hasNext(); i++) {
			String currentLetter = (String) it.next();
			letterRankedArray[i] = currentLetter.charAt(0);
		}
		for(int i=0; i<26; i++) alphabetCorrespDecipher.put(letterRankedArray[i], CryptoUtils.alphabetRanked[i]);

		
	}


	
	public static void main (String[] args){
		
		System.out.println("Bigram Frq : " + CryptoUtils.bigramFreqRanked(cipher));
		alphabetDetermination(cipher);
		System.out.println("Decipher Alphabet : " + alphabetCorrespDecipher);
		
		monoalphabeticDecipher(cipher);
	}
}
