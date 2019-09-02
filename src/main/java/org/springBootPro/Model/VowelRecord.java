package org.springBootPro.Model;

public class VowelRecord {
		
	char [] vowels = {'a', 'e', 'i', 'o', 'u'};
	
	int amount;

	public char[] getVowels() {
		return vowels;
	}

	public void setVowels(char[] vowels) {
		this.vowels = vowels;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}
	
	public VowelRecord() {}
}
