package com.Crawler;

import java.beans.FeatureDescriptor;
import java.io.IOException;
import java.lang.Character.Subset;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.naming.directory.ModificationItem;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.jsoup.select.Evaluator.IsEmpty;


public class WebCrawlerHelper {

	private Document htmlDoc;
	private List<CrawledData> allURLs = new LinkedList<CrawledData>();
	private static int viewedPage=0;


	/**
	 * 
	 * @param page
	 * @param word
	 * @return true when page is successfully crawled and links have been extracted. 
	 * @throws IOException
	 * @throws InterruptedException
	 * This method is used to go the given URL and extract the list of subsequent URL on that page 
	 */
	public boolean pageCrawler(CrawledData page, String word) throws IOException, InterruptedException{
			viewedPage++;
			//use the below System.out.println only when you want to know details of the number of
			//pages  retrieved during focused crawling. This is commented out for better readability of the output
			//	System.out.println(viewedPage);
		try{
			//introduces time delay of 1 second before making another http connection
			TimeUnit.SECONDS.sleep(1);
			
			Connection connection = (Connection) Jsoup.connect(page.url);
			String Keyphrase = word;
		
			Document htmlDoc = connection.get();
			this.htmlDoc= htmlDoc;
	    	Elements links = htmlDoc.select("a[href]");
	    	/*The if statement below ensures that if keyphrase is missing continue crawling through each retrieved*/
	    	/*if keyphrase is present, call method KeyPhraseSearch and proceed if boolean 'true' is returned. */
	    	if ((word == null||word.isEmpty()) || KeyPhraseSearch(word, htmlDoc)){
	    		for(Element e : links){	  
	    			String URLtoString= (e.absUrl("href").toString());
	 		       	if(URLtoString.contains("#")){
		       		int index=URLtoString.indexOf("#");
		       		URLtoString=URLNormalization(URLtoString, index);
			       	}
			       	
			       	if (validateURL(URLtoString)==true)
			       	{
			       		//System.out.println("URL is "+URLtoString);
		       			this.allURLs.add(new CrawledData(page.depth + 1, URLtoString));
			       	}	       			
	    		}
	    		
	    		return true;
	    	}
		}catch(IOException e){
			e.printStackTrace();
		}
		return false;
	}
	
	
	/***
	 * @param fetchedURL
	 * @return true if the URL only follow links with the prefix http://en.wikipedia.org/wiki
	 * and do not follow links with a colon (:) 
	 * and do not follow links to the main page http://en.wikipedia.org/wiki/Main_Page
	 */
	
	public Boolean validateURL(String fetchedURL){
			String retrievedURL;
			int indexOfHash; 
			int lengthOfURL =fetchedURL.length();
			String sub= fetchedURL.substring(6,(lengthOfURL - 1));
			if(fetchedURL.contains("https://en.wikipedia.org/wiki/Main_Page"))
			return false;
			if(!fetchedURL.startsWith("https://en.wikipedia.org/wiki/"))
				return false;
			if(sub.contains(":")){
				return false;
			}
	     	return true;
     }

	/***
	 * 
	 * @param URL
	 * @param index
	 * @return it returns the modified URL after truncating the substring after '#'
	 * as they are pointers to same URL
	 */
	public String URLNormalization(String URL, int index){
		String modifiedURL;
		modifiedURL=URL.substring(0, index);
		return modifiedURL;
		
	}
	
	
	/**
	 * 
	 * @param word
	 * @return 'true' if given word is present in body of the doc, otherwise false
	 */
	
	public Boolean KeyPhraseSearch(String word, Document doc){
		String bodyText = this.htmlDoc.body().text().toLowerCase();
  		if(bodyText.contains(word.toLowerCase())){
			return true;
		}
		else{
			return false;
		}
	}
	
	/**
	 * 
	 * @returns the list of Objects that contains two values : URL and their depth of crawling.
	 */
	public List<CrawledData> getAllLinks(){
		
	 		return this.allURLs;
	}
	
	
}
