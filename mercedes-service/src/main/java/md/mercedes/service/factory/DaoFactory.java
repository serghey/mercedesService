package md.mercedes.service.factory;

import md.mercedes.service.dao.ProductDao;

public interface DaoFactory {

	ProductDao getProductDao();

}
