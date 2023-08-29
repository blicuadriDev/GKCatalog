package com.godknows.gkcatalog.cateories;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.godknows.gkcatalog.entities.Product;
import com.godknows.gkcatalog.repositories.ProductRepository;

import tests.ProductFactoryTests;

@DataJpaTest
public class ProductRepositoryTests {
	
	private Long existingId;
	private Long unexistingId;
	private Long countTotalProducts;
	
	@BeforeEach
	void setUp() throws Exception {
		existingId = 1L;
		unexistingId = 100L;
		countTotalProducts = 25L;
	}
	
	@Autowired
	private ProductRepository repository;
	
	
	@Test
	public void saveShouldPersistWithAutoIncrementWhenIdIsNull() {
		Product product = ProductFactoryTests.createProduct();
		product.setId(null);
		
		product = repository.save(product);
		
		Assertions.assertNotNull(product.getId());
		Assertions.assertEquals(countTotalProducts +1, product.getId());;
	}
	
	
	
	@Test
	public void deteleShouldDeleteObjectWhenIdExist() {
		//Arrange (I can remove this since I already set the id using Fixture @BeforeEach)
		//Long existingId = 1L;
		
		//Act
		repository.deleteById(existingId);
		
		//Assert
		Optional<Product> result = repository.findById(existingId);
		Assertions.assertFalse(result.isPresent());
	}
	
	
	@Test
	public void findByIdShouldReurnAnOptionalNotEmptyWhenIdExists() {
		//Arrange (I can remove this since I already set the id using Fixture @BeforeEach)
		//Long existingId = 1L;
		
		//Act
		Optional<Product> result = repository.findById(existingId);
		
		//Assert
		Assertions.assertTrue(result.isPresent());
	}
	
	
	@Test
	public void findByIdShouldReurnAnEmptyOptionalWhenIdDoesNotExist() {
		//Arrange (I can remove this since I already set the id using Fixture @BeforeEach)
		//Long unexistingId = 100L;
		
		//Act
		Optional<Product> result = repository.findById(unexistingId);
		
		//Assert
		Assertions.assertFalse(result.isPresent());
	}

}
