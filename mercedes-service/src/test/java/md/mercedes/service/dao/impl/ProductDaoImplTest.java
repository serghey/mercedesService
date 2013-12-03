package md.mercedes.service.dao.impl;

import static org.junit.Assert.*;

import java.io.IOException;

import md.mercedes.service.dao.ProductDao;
import md.mercedes.service.factory.DefaultDaoFactory;

import org.junit.Test;

public class ProductDaoImplTest {

	private ProductDao productDao;

	public ProductDaoImplTest() {
		productDao = DefaultDaoFactory.getInstance().getProductDao();
	}

	@Test
	public void testLoadAllProductsToDB() {
		long start = System.currentTimeMillis();

		try {
			// productDao.loadAllProductsToDB();
			productDao.loadProductsToDB();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		long stop = System.currentTimeMillis();
		long result = (stop - start) / 60000;
		System.out.println("Test runs in : " + result + " mins");
		assertTrue(true);
	}

}
