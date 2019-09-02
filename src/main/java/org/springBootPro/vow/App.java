package org.springBootPro.vow;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import org.springBootPro.Model.VowelGroupRecord;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws IOException
    {
    	Parser parser = new Parser();
    	List<VowelGroupRecord> words = parser.parseFile(new File("/home/kamil/Pulpit/SpringTesty/testProd/vow/INPUT.TXT"));
    	try (PrintWriter out = new PrintWriter("/home/kamil/Pulpit/SpringTesty/testProd/vow/OUTPUT.TXT")) {
    		for(VowelGroupRecord word : words) {
    	   		out.println(word);
    		}
    	}
    }
}
