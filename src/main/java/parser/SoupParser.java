package parser;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class SoupParser {

	//private static final String WEBLINK = "http://en.wikipedia.org/";
	private String webLink = "http://ukr.net";
	
//	public SoupParser(String link){
//		webLink = link;
//	}
	
	public static Set<String> getAllWordsFromPage(String webPage) {
		Document doc = new Document(webPage);
		try {
			doc = Jsoup.connect(webPage).get();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Elements allLines = doc.getAllElements();
		Set<String> clearWords = new HashSet<>();
		
		for (Element i: allLines) {
			
			String curString = i.text().trim().replaceAll("[,+.!?():\"\\d]+", " ");
			String[] curDirtyWords = curString.split("\\s");
			
			for (String dirtyWord: curDirtyWords) {
//				System.out.println(dirtyWord.toLowerCase());
				clearWords.add(dirtyWord.toLowerCase());
			}			
		}
		
//		for (String i: clearWords) {
//			System.out.println(i);
//		}	
		
		return clearWords;
	}
	
	public static Set<String> getAllLinks(String webPage) {
		Document doc = new Document(webPage);
		try {
			doc = Jsoup.connect(webPage).get();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Set<String> clearLinks = new HashSet<>();
		
		//Elements links = doc.getElementsByTag("a");
		 Elements links = doc.select("a");
		
		for (Element i: links) {
			System.out.println(i.attr("href"));
			clearLinks.add(i.attr("href"));
		}
		
		return clearLinks;
	}
	
	public static void main(String[] args) {
		//SoupParser test = new SoupParser();
		
		SoupParser.getAllWordsFromPage("http://ukr.net");	
		
	}
}
