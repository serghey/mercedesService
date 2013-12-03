package md.mercedes.service.dao;

import java.io.IOException;

import md.mercedes.service.model.Product;

public interface ProductDao {
	
	public void loadProductToDB(String code, Product product) throws Exception;
	
	public void loadAllProductsToDB() throws IOException, Exception;
	
	public void loadProductsToDB() throws IOException, Exception;

}
