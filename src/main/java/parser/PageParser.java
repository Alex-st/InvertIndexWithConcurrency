package parser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PageParser {
	//private String searchURL = "httð://ukr.net";

	
	public InputStream getPage(String searchParam) throws IOException {
        URL url = new URL(searchParam);
 
        return (InputStream) url.getContent();
    }
	
	public void parsePageFromStrim(String searchParam) {
	 
		List<String> links = new ArrayList<String>();
		
		//System.out.println(System.getProperties());
		
		try {
         //BufferedReader page = new BufferedReader(new InputStreamReader(getPage(searchParam)));
         
         BufferedReader page = new BufferedReader(new InputStreamReader(getPage(searchParam)));

         //todo create personal pattern
         //
         Pattern link = Pattern.compile("<a href=\"[^\"]+\"");
         Pattern teg = Pattern.compile("<[^\"]+>");

         //StringBuilder pageContent = new StringBuilder("");

         String line;

         while ((line = page.readLine()) != null) {
        	 
        	 Matcher matcher = link.matcher(line);
        	 
             while (matcher.find()) {
            	 System.out.println(matcher.group());
                 links.add(matcher.group());
             }
             
             System.out.println(line);
         }

     } catch (IOException e) {
         e.printStackTrace();
     }
	}
	
	public static void main(String[] args) {
		PageParser test = new PageParser();
		test.parsePageFromStrim("http://www.ukr.net");
	}
}
