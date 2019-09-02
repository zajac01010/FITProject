package org.springBootPro.Model;

public class VowelGroupRecord {
	
	public String vowelGroup;
	
	private int count; 
	
	private double amount;
	
	private int id;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getVowelGroup() {
		return vowelGroup;
	}

	public void setVowelGroup(String vowelGroup) {
		this.vowelGroup = vowelGroup;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	} 
	
	public VowelGroupRecord(String vowelGroup, int count, double amount, int id) {
		this.id = id;
		this.vowelGroup = vowelGroup;
		this.count = count;
		this.amount = amount;
	}
	
	public String toString() {
		return "({" + this.vowelGroup.charAt(0) +", " + this.vowelGroup.charAt(1) + "}, " + this.id + ") -> " + this.amount;
	}
}
