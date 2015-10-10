1. Eclipse Release Version 4.5.0 (Mars) was used to develop and test this crawler prototype.
2. java version : "1.8.0_60" 
3. External Library used: jsoup-1.8.3
4. Name of the workspace: WebCralwer
5. Import the workspace in eclipse
6. Add the external jar file jsoup-1.8.3.jar to the workspace [project->right click-Build Path ->build path->Add External Jars-><select jsoup-1.8.3 from its location> ->Apply-> OK
7. Main method present in the com.Test.Crawler.java is used to pass the seed page and the optional keyphrase.
		CrawledData seed = new CrawledData(1, "https://en.wikipedia.org/wiki/Hugh_of_Saint-Cher");
		::: pass depth =1 and the seed page URL
		crawler.startCrawl(seed, "concordance");
		::: pass the object 'seed' and the keyphrase
8. This will class the com.Crawler.WebCrawler.java file, which in return calls WebCrawlerHelper.java file. Together these two files ensure that if no Keyphrase is passed, links are rereived from each crawled page till size of crawled page reached 1000 or till depth 5 is reached. For my run,  1000 	URLs will be crawled before depth of 1000 is reached.
9.If a keyphrase is passed in line crawler.startCrawl(seed, <optional keyphrase>); then each page is crawled and if and only if the keyphrase is present in the current page the embedded URLs are retrieved. 
10. Politeness policy is introduces in crawler by using a time delay of 1 second before each page is crawled.

NOTE: 	Total Number of pages retrieved with successful Keyphrase match = 516
		Depth of crawl reached = 5

		7+ hours were spent in retreving output for the focused crawler with time delay. For a quick check of the output, one can comment out the delay part of the program and verify teh first few URLs. 
		
FILE DETAILS:
Crawl_results_without_keyphrase: List of URLs retreived without giving keyphrase
Crawl_results_with_keyphrase_concordance_LIST : List of URLs retreived when keyphrase = concordance 
Crawl_results_with_keyphrase_concordance_DEPTH_INFO : List of URLs retreived when keyphrase = concordance  and their depth details