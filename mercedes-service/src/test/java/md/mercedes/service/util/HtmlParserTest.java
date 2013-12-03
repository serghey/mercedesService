package md.mercedes.service.util;

import static org.junit.Assert.*;

import java.io.IOException;

import md.mercedes.service.model.Product;

import org.junit.Test;

public class HtmlParserTest {
	
	
	private String testURL = "http://www.benett-auto.md/catalog/_sqquery=AG142291";
	HtmlParser parser;
	
	
	public HtmlParserTest(){
		parser = new HtmlParser();
	}
	@Test
	public void testParseBAHtmlPage() throws IOException, InterruptedException {
		long start = System.currentTimeMillis();
		Product product = parser.getProductFromUrl(testURL);
		long stop = System.currentTimeMillis();
		long result = (stop - start) / 1000;
		System.out.println("Test runs in : " + result + " sec");
		assertNotNull(product);
		System.out.println(product);
		
		
	}

}
