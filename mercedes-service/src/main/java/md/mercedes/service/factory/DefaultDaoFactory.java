package md.mercedes.service.factory;

import md.mercedes.service.backend.CassandraConnection;
import md.mercedes.service.dao.ProductDao;
import md.mercedes.service.dao.impl.ProductDaoImpl;

public class DefaultDaoFactory implements DaoFactory {
	
	private static final CassandraConnection BACKEND = new CassandraConnection();
	private static final ProductDao PRODUCT_DAO = new ProductDaoImpl(BACKEND);
	private static final DefaultDaoFactory INSTANCE = new DefaultDaoFactory();
	
	private DefaultDaoFactory() {
	}
	
	public static final DefaultDaoFactory getInstance(){
		return INSTANCE;
	}
	
	public ProductDao getProductDao() {
		return PRODUCT_DAO;
	}
	
}
