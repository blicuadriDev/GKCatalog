package tests;

import java.time.Instant;

import com.godknows.gkcatalog.dtos.ProductDTO;
import com.godknows.gkcatalog.entities.Category;
import com.godknows.gkcatalog.entities.Product;

public class ProductFactoryTests {
	
	
	public static Product createProduct() {
		Product product = new Product(1L, "Phone", "Good Phone", 800.0, "http://img.com/img.png", Instant.parse("2020-10-20T03:00:00Z"));
		product.getCategories().add(new Category(2L, "Electronicis"));
		return product;
	}
	
	
	public static ProductDTO createProductDTO() {
		Product product = createProduct();
		return new ProductDTO(product, product.getCategories());
	}

}
