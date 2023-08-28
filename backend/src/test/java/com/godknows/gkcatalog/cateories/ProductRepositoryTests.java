package com.godknows.gkcatalog.cateories;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.godknows.gkcatalog.entities.Product;
import com.godknows.gkcatalog.repositories.ProductRepository;

@DataJpaTest
public class ProductRepositoryTests {
	
	@Autowired
	private ProductRepository repository;
	
	@Test
	public void deteleShouldDeleteObjectWhenIdExist() {
		
		//Arrange
		Long existingId = 1L;
		
		//Act
		repository.deleteById(existingId);
		
		//Assert
		Optional<Product> result = repository.findById(existingId);
		Assertions.assertFalse(result.isPresent());

	}

}
