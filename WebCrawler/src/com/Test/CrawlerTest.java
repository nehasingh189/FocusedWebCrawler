package com.Test;

import java.io.IOException;

import com.Crawler.*;


public class CrawlerTest {

	public static void main(String[] args) throws IOException, InterruptedException{
		
		WebCrawler crawler = new WebCrawler(); 
		CrawledData seed = new CrawledData(1, "https://en.wikipedia.org/wiki/Hugh_of_Saint-Cher");
		crawler.startCrawl(seed, "concordance");
	}
	
}       
