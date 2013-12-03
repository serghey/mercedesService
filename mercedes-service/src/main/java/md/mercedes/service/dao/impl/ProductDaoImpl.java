package md.mercedes.service.dao.impl;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import com.netflix.astyanax.MutationBatch;
import com.netflix.astyanax.connectionpool.exceptions.ConnectionException;

import md.mercedes.service.backend.CassandraConnection;
import md.mercedes.service.dao.ProductDao;
import md.mercedes.service.endpoint.ProductEndpoint;
import md.mercedes.service.model.Product;

public class ProductDaoImpl implements ProductDao {

	private final CassandraConnection connection;

	private ProductEndpoint endpoint = ProductEndpoint.getInstance();

	public static enum ColumnNames {
		factoryName, description, stockSize, price;
	}

	public ProductDaoImpl(CassandraConnection connection) {
		this.connection = connection;
	}

	public void loadProductToDB(String code, Product product) throws Exception {
		if (product != null) {
			final MutationBatch m = connection.prepareMutationBatch();
			m.withRow(connection.getPRODUCT_CF(), code)
					.putColumn(ColumnNames.factoryName.toString(),
							product.getFactoryName())
					.putColumn(ColumnNames.description.toString(),
							product.getDescription())
					.putColumn(ColumnNames.stockSize.toString(),
							product.getStockSize())
					.putColumn(ColumnNames.price.toString(), product.getPrice());
			try {
				m.execute();
			} catch (ConnectionException e) {
				throw new Exception("Failed to write data to Cassandra: "
						+ e.getMessage(), e.getCause());
			}
		}
	}

	public void loadAllProductsToDB() throws IOException, Exception {

		Map<String, Product> allProducts = endpoint.getAllProducts();

		if (allProducts != null && !allProducts.isEmpty()) {
			Iterator<Entry<String, Product>> it = allProducts.entrySet()
					.iterator();
			while (it.hasNext()) {
				Map.Entry<String, Product> pairs = (Map.Entry<String, Product>) it
						.next();
				loadProductToDB(pairs.getKey(), pairs.getValue());
			}
		}
	}
	
	public void loadProductsToDB() throws IOException, Exception {

		Map<String, Product> allProducts = endpoint.getProducts();
		System.out.println("Size :"+allProducts.size());
		if (allProducts != null && !allProducts.isEmpty()) {
			Iterator<Entry<String, Product>> it = allProducts.entrySet()
					.iterator();
			while (it.hasNext()) {
				Map.Entry<String, Product> pairs = (Map.Entry<String, Product>) it
						.next();
				loadProductToDB(pairs.getKey(), pairs.getValue());
			}
		}
	}
}
