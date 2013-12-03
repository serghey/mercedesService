package md.mercedes.service.model;

public class Product {

	private String factoryName;
	private String code;
	private String description;
	private String stockSize;
	private String price;
	
	
	public String getFactoryName() {
		return factoryName;
	}
	public void setFactoryName(String name) {
		this.factoryName = name;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getStockSize() {
		return stockSize;
	}
	public void setStockSize(String stockSize) {
		this.stockSize = stockSize;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	@Override
	public String toString() {
		return "Product [factoryName=" + factoryName + ", code=" + code
				+ ", description=" + description + ", stockSize=" + stockSize
				+ ", price=" + price + "]";
	}
}
