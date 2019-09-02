package org.springBootPro.utils;

import java.util.Comparator;

import org.springBootPro.Model.VowelGroupRecord;

public class VowelsGroupComparator implements Comparator{
	
	@Override
	public int compare(Object o1, Object o2) {
		VowelGroupRecord firstGroup = (VowelGroupRecord) o1;
		VowelGroupRecord secondGroup = (VowelGroupRecord) o2;
		
		if(firstGroup.getId()==secondGroup.getId())
			return 0;
		else if(firstGroup.getId() > secondGroup.getId())
			return -1;
		else
		return 1;
	}
}
