package org.springBootPro.vow;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.stream.Collectors;

import org.springBootPro.Model.VowelGroupRecord;
import org.springBootPro.utils.VowelsGroupComparator;

import static org.springBootPro.staticData.VowelsGroup.vowelGroups;

public class Parser {
	
	public List<VowelGroupRecord> parseFile(File file) throws IOException {
		FileInputStream fileStream = new FileInputStream(file); 
		InputStreamReader input = new InputStreamReader(fileStream); 
        
        String[] wordList = splitByLines(input);
        
        List<VowelGroupRecord> vowelGroupRecords = getListOfVowelGroups(wordList);
        List<VowelGroupRecord> vowelGroupRecordsWithoutDuplicates = removeDuplicates(vowelGroupRecords);
        Collections.sort(vowelGroupRecordsWithoutDuplicates, new VowelsGroupComparator());
        return vowelGroupRecordsWithoutDuplicates;
	}
	
	public List<VowelGroupRecord> removeDuplicates(List<VowelGroupRecord> vowelGroupRecords) {
		List<VowelGroupRecord> vowelGroupRecordsWithoutDuplicates = new ArrayList<VowelGroupRecord>();
		for(VowelGroupRecord record : vowelGroupRecords) {
			if(record.getAmount() > 1) {
				double fractionAmount = ((double) record.getAmount()) / record.getCount();
				vowelGroupRecordsWithoutDuplicates.add(new VowelGroupRecord(record.getVowelGroup(), record.getCount(), fractionAmount, record.getId()));
			} else {
				vowelGroupRecordsWithoutDuplicates.add(record);
			}
		}
		return vowelGroupRecordsWithoutDuplicates;
	}
	
	List<VowelGroupRecord> getListOfVowelGroups(String[] wordList) {
		List<VowelGroupRecord> vowelGroupRecords = new ArrayList<VowelGroupRecord>();
		for(String ln : wordList) {
        	List<VowelGroupRecord> resultSet = transformLineToVowelGroupRecord(ln);
        	/*System.out.println(resultSet.size());
        	for(VowelGroupRecord r: resultSet) {
        	System.out.println(r);
        	}*/
        	if(resultSet.isEmpty()) continue; 
        	List<VowelGroupRecord> existingVowelGroupId = (List<VowelGroupRecord>) vowelGroupRecords.stream().filter(c -> c.getId() == ln.length()).collect(Collectors.toList());
        	System.out.println("existingVowelGroupId size : " + existingVowelGroupId.size());
        	for(VowelGroupRecord newVowelGroupRecord : resultSet) {
        	if(existingVowelGroupId.isEmpty()) {
        		System.out.println(newVowelGroupRecord.toString() + " added");
        		vowelGroupRecords.add((VowelGroupRecord) newVowelGroupRecord);
        	} else {
        		VowelGroupRecord existingGroupInVowelGroups = (VowelGroupRecord) existingVowelGroupId.stream()
        														.filter(c -> c.getVowelGroup().equals(newVowelGroupRecord.getVowelGroup())).findAny().orElse(null);
           		System.out.println("Check if true: " + existingVowelGroupId.get(0).getVowelGroup().equals(newVowelGroupRecord.getVowelGroup()));
        		if(existingGroupInVowelGroups == null) {
        			vowelGroupRecords.add(newVowelGroupRecord);
        			System.out.println(newVowelGroupRecord.toString() + "added");
        		} else {
        			System.out.println(existingGroupInVowelGroups.vowelGroup + " found");
        			VowelGroupRecord newGroupInVowelGroups = new VowelGroupRecord(existingGroupInVowelGroups.getVowelGroup(),
        					existingGroupInVowelGroups.getCount() + 1, 
        					existingGroupInVowelGroups.getAmount() +  newVowelGroupRecord.getAmount(),
        					existingGroupInVowelGroups.getId());
        			vowelGroupRecords.add(newGroupInVowelGroups);
        			vowelGroupRecords.remove(existingGroupInVowelGroups);
        		}
        	}
        	}
        }
		return vowelGroupRecords;
	}
	
	String[] splitByLines(InputStreamReader input) throws IOException {
		String[] wordList = null;
		String line;
		BufferedReader reader = new BufferedReader(input);
		while((line = reader.readLine()) != null) {
        	if(!(line.equals(""))) {
        		wordList = line.split("\\s+"); 
        	}
        }
		return wordList;
	}
	
	
	
	public List<VowelGroupRecord> transformLineToVowelGroupRecord(String line) {
		//System.out.println("Inside method");
		String vowelGroup = null;
		List<VowelGroupRecord> resultList = new ArrayList<VowelGroupRecord>();
		Map<Character, Integer> myMap = new TreeMap<Character, Integer>();
		myMap.put('a', 0);
		myMap.put('e', 0);
		myMap.put('i', 0);
		myMap.put('o', 0);
		myMap.put('u', 0);
		SortedSet vowelGroupUnique = new TreeSet();
		char[] wordc = line.toLowerCase().toCharArray();
		int vowelsNumber = 0;
		System.out.println("For word " + line + " size = " + wordc.length);
		for(int w = 0;w<line.length();w++) {
				for(Character key : myMap.keySet()) {
					if(key.equals(wordc[w])) {
						vowelGroupUnique.add(key.charValue());
						myMap.merge(key, 1, Integer::sum);
					}
				}
		}
		if(vowelGroupUnique.size() > 1) {
		//System.out.println(vowelGroupUnique.toString() + " add");
		List<String> splittedGroups = groupsSplit(vowelGroupUnique);
		for(int i =0; i < splittedGroups.size(); i++) {
		int amount = countAmount(splittedGroups.get(i), myMap);	
		System.out.println("Adding: " + splittedGroups.get(i) + " " + amount);
		resultList.add(new VowelGroupRecord(splittedGroups.get(i), 1, amount, wordc.length));
		System.out.println(splittedGroups.get(i).toString() + " - test");
		}
		}
		return resultList;
	}
	
	int countAmount(String splittedGroup, Map<Character, Integer> myMap) {
		return myMap.get(splittedGroup.charAt(0)) + myMap.get(splittedGroup.charAt(1));
	}
		
		List<String> groupsSplit(Set vowelGroupUnique) {
			Object[] objArray = vowelGroupUnique.toArray();
			String vowelGroupUniqueString = "";
			for(int i = 0; i < objArray.length; i++) {
				vowelGroupUniqueString += objArray[i];
			}
			//System.out.println(vowelGroupUniqueString + " - " + vowelGroupUniqueString.length());
			List<String> splittedGroups = new ArrayList<String>();
			for(int i = 0; i < vowelGroupUniqueString.length(); i++) {
				for(int j = i + 1; j < vowelGroupUniqueString.length(); j++) {
					String splittedGroup = Character.toString(vowelGroupUniqueString.charAt(i)) + Character.toString(vowelGroupUniqueString.charAt(j));
					splittedGroups.add(splittedGroup);
					//System.out.println(splittedGroup + " gtoupsSplit");
				}
			}
			return splittedGroups;
		}
}
