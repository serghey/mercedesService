package md.mercedes.service.util;

import java.io.IOException;
import java.net.SocketTimeoutException;

import md.mercedes.service.model.Product;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class HtmlParser {

	private static final String CLASS_NAME = "catalog";
	private static final String TAG_NAME = "td";
	private static final int TIMEOUT = 20000;

	/**
	 * Returns the elements of the searched product code. From the url 1 -
	 * Description 2 - factory producer 3 - Code 4 - Quantity 5 - Price
	 * 
	 * @param pageURL
	 * @return
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public Product getProductFromUrl(String pageURL) throws IOException, InterruptedException {
		Product product = null;
		try {
			long start = System.currentTimeMillis();
			Document doc = Jsoup.connect(pageURL).timeout(TIMEOUT).get();

			Elements catalog = doc.getElementsByClass(CLASS_NAME);
			if (catalog != null && catalog.size() > 0 && !catalog.isEmpty()) {
				Element element = catalog.get(0);

				if (element != null) {
					if (!element.getElementsByTag(TAG_NAME).get(5).text().equals("0")) {
						product = new Product();
						product.setDescription(element.getElementsByTag(TAG_NAME).get(2).text());
						product.setFactoryName(element.getElementsByTag(TAG_NAME).get(3).text());
						product.setCode(element.getElementsByTag(TAG_NAME).get(4).text());
						product.setStockSize(element.getElementsByTag(TAG_NAME).get(5).text());
						product.setPrice(element.getElementsByTag(TAG_NAME).get(6).text());
					}
				}
			}
			long stop = System.currentTimeMillis();
			long average = stop-start ;
			System.out.println("Request for : "+pageURL+ " was :" + average + " mills");
		} catch (SocketTimeoutException e) {
			System.out.println("Timeout Exception Occured");
		}
		return product;
	}

	public Product getProductByCodeFromUrl(String url, String code) throws IOException, InterruptedException {
		return getProductFromUrl(url + code);
	}

}
