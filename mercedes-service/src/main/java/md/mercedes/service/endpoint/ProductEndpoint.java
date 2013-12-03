package md.mercedes.service.endpoint;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import md.mercedes.service.model.Product;
import md.mercedes.service.util.ExcelParser;
import md.mercedes.service.util.HtmlParser;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;

public class ProductEndpoint {

	private static final ProductEndpoint INSTANCE = new ProductEndpoint();
	private static final String benettAutoURL = "http://www.benett-auto.md/catalog/_sqquery=";
	protected ExcelParser excelParser;
	protected HtmlParser htmlParser;
	protected ExecutorService executor = Executors.newFixedThreadPool(20);
	protected ListeningExecutorService service = MoreExecutors.listeningDecorator(executor);

	private ProductEndpoint() {
		excelParser = new ExcelParser();
		htmlParser = new HtmlParser();
	}

	public static ProductEndpoint getInstance() {
		return INSTANCE;
	}

	public Map<String, Product> getAllProducts() throws IOException, Exception {

		Map<String, Product> allProducts = new HashMap<String, Product>();

		List<String> productCodes = excelParser.getProductCodesFromClassPath();

		if (productCodes != null && productCodes.size() > 0) {
			for (String productCode : productCodes) {
				if (productCode != null && productCode.length() > 0) {
					Product product = htmlParser.getProductFromUrl(benettAutoURL + productCode);
					if (product != null) {
						allProducts.put(productCode, product);
					}
				}

			}
		}

		return allProducts;
	}

	public Map<String, Product> getProducts() throws IOException, Exception {

		final Map<String, Product> allProducts = new ConcurrentHashMap<String, Product>();
		//final Builder <ServiceFuture> listBuilder = ImmutableList.<ServiceFuture> builder();
		List<String> productCodes = excelParser.getProductCodesFromClassPath();
		final Builder <ListenableFuture<Product>> futureProducts  = ImmutableList.<ListenableFuture<Product>> builder();
		
		if (productCodes != null && productCodes.size() > 0) {
			for (String productCode : productCodes) {
				if (productCode != null && productCode.length() > 0) {
					ServiceFuture future = new ServiceFuture(productCode);
					futureProducts.add(service.submit(future));
					//listBuilder.add(future);
				}
			}
		}
		
//		List<Future<Product>> futureProducts = service.invokeAll(listBuilder.build());
//		
//		for (Future<Product> future : futureProducts) {
//			Product product = future.get();
//			if(product!=null){
//				allProducts.put(product.getCode(), product);
//			}
//		}
		
		for (ListenableFuture<Product> future : futureProducts.build()) {
			Product product = future.get();
			if(product!=null){
				allProducts.put(product.getCode(), product);
			}
		}
		
		service.shutdown();
		
		return allProducts;
	}
	
	private static final class ServiceFuture implements Callable<Product>{
		
		private final String productCode;
		
		public ServiceFuture(String productCode){
			this.productCode = productCode;
		}

		public Product call() throws Exception {
			HtmlParser parser = new HtmlParser();
			Product result = parser.getProductFromUrl(benettAutoURL + productCode);
			return result;
		}
		
	}

}
