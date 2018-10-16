package crypto;

import java.util.LinkedHashMap;
import java.util.Map;
import org.apache.commons.codec.*;
import org.apache.commons.codec.binary.Hex;

public class cryptoUtils {
	
	private static Character[] alphabet = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};
	public static Character[] alphabetRanked = {'e', 'a', 'i', 's', 'n', 'r', 't', 'o', 'l', 'u', 'd', 'c', 'm', 'p', 'g', 'b', 'v', 'h', 'f', 'q', 'y', 'x', 'j', 'k', 'w', 'z'};
	public static String[] bigramRanked = {"es", "de", "le", "en", "re"};
	
	/**
	 * Count the occurrence of alphabet item variable inside a text
	 * @param text 
	 * @return a map of all the letters (ranked in the order of alphabet) and the number of occurrence
	 */
	public static Map<String, Integer> letterFreq(String text){
		Map<String, Integer> letterFreq = new LinkedHashMap<>(); 
		for(int i=0; i<alphabet.length; i++) letterFreq.put(alphabet[i].toString(), 0);
		
		for(int i=0; i<text.length(); i++) {
			String currentLetter = String.valueOf(text.charAt(i));
			if(letterFreq.containsKey(currentLetter)){
				int currentFreq = letterFreq.get(currentLetter);
				letterFreq.put(currentLetter, currentFreq+1);
			}
		}
		return letterFreq;
	}
	
	public static String highestLetterFreq(String text) {
		Map<String, Integer> letterFreq = letterFreq(text);
		int maxFreq = 0;
		String maxLetter = "e";
		
		for (Map.Entry<String, Integer> entry : letterFreq.entrySet()){
		    int currentFreq = entry.getValue();
		    if(currentFreq>maxFreq) {
		    	maxLetter = entry.getKey(); 
		    	maxFreq = currentFreq;
		    }
		}
		return maxLetter; 
	}
	
	public static Map<String, Integer> letterFreqRanked(String text){
		Map<String, Integer> letterFreq = letterFreq(text);
		Map<String, Integer> letterFreqRanked = new LinkedHashMap<>();
		
		int maxFreq = -1;
		String maxLetter = null;
		int currentFreq = -1;
		for(int i=0; i<letterFreq.size(); i++) {
			maxFreq = -1;
			
			for(int j=0; j<letterFreq.size(); j++) {
				currentFreq = letterFreq.get(Character.toString(alphabet[j]));
				if((currentFreq>maxFreq)&&(currentFreq!=-1)){	
					maxFreq = currentFreq;
					maxLetter = Character.toString(alphabet[j]);
				}
			}
			
			letterFreqRanked.put(maxLetter, maxFreq);
			letterFreq.put(maxLetter, -1);
		}
		
		
		return letterFreqRanked;
	}

	public static String[] alphabetBigrams(){
		String[] bigrams = new String[alphabet.length*alphabet.length];
		int index = 0;
		for(int i=0; i<alphabet.length; i++) {
			for(int j=0; j<alphabet.length; j++) {
				bigrams[index] = "" + alphabet[i] + alphabet[j];
				index++;
			}		
		}
		return bigrams;
	}
	
	public static Map<String, Integer> bigramFreq(String text){
		Map<String, Integer> bigramFreq = new LinkedHashMap<>(); 
		String[] bigrams = alphabetBigrams();
		
		for(int i=0; i<bigrams.length; i++) {
			String currentBigram = bigrams[i];
			if(text.contains(currentBigram)){
				int currentFreq = text.split(currentBigram).length-1; 
				bigramFreq.put(currentBigram, currentFreq);
			}
		}
		return bigramFreq;
	}
	
	public static Map<String, Integer> bigramFreqRanked(String text){
		String[] bigrams = alphabetBigrams();
		Map<String, Integer> bigramFreq = bigramFreq(text);
		Map<String, Integer> bigramFreqRanked = new LinkedHashMap<>();
		
		int maxFreq = -1;
		String maxBigram = null;
		int currentFreq = -1; 
		
		for(int i=0; i<bigramFreq.size(); i++) {
			maxFreq = -1; 
			for(int j=0; j<bigramFreq.size(); j++) {
				if(bigramFreq.containsKey(bigrams[j])) {
					currentFreq = bigramFreq.get(bigrams[j]);
					if((currentFreq>maxFreq)&&(currentFreq!=-1)) {
						maxFreq = currentFreq;
						maxBigram = bigrams[j];
					}
				}
			}
			bigramFreqRanked.put(maxBigram, maxFreq);
			bigramFreq.put(maxBigram, -1);
		}
		return bigramFreqRanked;
	}
	
	public static String bytesToHex(byte[] input) {
		String bytesToHex = "";
		for(int i=0; i<input.length; i++) {
			bytesToHex =  bytesToHex + String.format("%x", input[i]);
		}
		return Hex.encodeHexString(input);
	}
	
	
	
	
	/*
	public static void mostLonelyLetter(String text) {
		
		Map<String, Integer> lonelyLetters = new LinkedHashMap<>();
		String[] splittedText = text.split(" ");
		for(int i=0; i<splittedText.length; i++) {
			if(splittedText[i].length() == 1) {
				System.out.println(i + "\t" + splittedText[i]);
				if(lonelyLetters.containsKey(splittedText[i])) {
					lonelyLetters.put(splittedText[i], lonelyLetters.get(splittedText[i])+1);
				} else {
					lonelyLetters.put(splittedText[i], 1);
				}
			}
		}
		System.out.println(lonelyLetters);
	}
	 */
}
