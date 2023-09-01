package com.godknows.gkcatalog.services;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.godknows.gkcatalog.dtos.ProductDTO;
import com.godknows.gkcatalog.entities.Category;
import com.godknows.gkcatalog.entities.Product;
import com.godknows.gkcatalog.repositories.CategoryRepository;
import com.godknows.gkcatalog.repositories.ProductRepository;
import com.godknows.gkcatalog.services.exceptions.DatabaseException;
import com.godknows.gkcatalog.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;
import tests.ProductFactoryTests;

@ExtendWith(SpringExtension.class)
public class ProductServiceTests {
	
	@InjectMocks
	private ProductService service;
	
	@Mock
	private ProductRepository repository;
	
	@Mock
	private CategoryRepository catRepository;
	
	
	private long existingId;
	private long unexistingId;
	private long dependentId;
	private PageImpl<Product> page;
	private Product product;
	private Category category;
	
	@BeforeEach
	void setUp() throws Exception {
		existingId = 1L;
		unexistingId = 2L;
		dependentId = 3L;
		product = ProductFactoryTests.createProduct();
		category = ProductFactoryTests.createCategory();
		page = new PageImpl<>(List.of(product));
		
		
		//Simulating the expected behaviour from mocked ProductRepository for ProdcutService actions (existsId and deletebyId)
		Mockito.when(repository.existsById(existingId)).thenReturn(true);
		Mockito.when(repository.existsById(unexistingId)).thenReturn(false);
		Mockito.when(repository.existsById(dependentId)).thenReturn(true);
		
		Mockito.doNothing().when(repository).deleteById(existingId);
		Mockito.doThrow(DataIntegrityViolationException.class).when(repository).deleteById(dependentId);
		
		Mockito.when(repository.findAll((Pageable)ArgumentMatchers.any())).thenReturn(page);
		
		Mockito.when(repository.findById(existingId)).thenReturn(Optional.of(product));
		Mockito.when(repository.findById(unexistingId)).thenReturn(Optional.empty());
		
		Mockito.when(repository.getReferenceById(existingId)).thenReturn(product);
		Mockito.when(repository.getReferenceById(unexistingId)).thenThrow(EntityNotFoundException.class);
		
		Mockito.when(catRepository.getReferenceById(existingId)).thenReturn(category);
		Mockito.when(catRepository.getReferenceById(unexistingId)).thenThrow(EntityNotFoundException.class);
		
		Mockito.when(repository.save(ArgumentMatchers.any())).thenReturn(product);
		
	}
	
	
	
	
	@Test
	public void deleteShouldDoNothingWhenIdExists() {
		Assertions.assertDoesNotThrow(() -> {
			service.delete(existingId);
		});
		
		Mockito.verify(repository, Mockito.times(1)).deleteById(existingId);
	}
	
	
	@Test
	public void deleteShouldThrowResourceNotFoundExceptionWhenIdUnexists() {
		Assertions.assertThrows(ResourceNotFoundException.class, () -> {
			service.delete(unexistingId);
		});
	}
	
	
	@Test
	public void deleteShouldThrowDatabaseExceptionWhenDeletingDependentId() {
		Assertions.assertThrows(DatabaseException.class, () -> {
			service.delete(dependentId);
		});
		Mockito.verify(repository, Mockito.times(1)).deleteById(dependentId);
	}
	
	@Test
	public void findAllPagedShouldReturnPage() {
		//Arrenge
		Pageable pageable = PageRequest.of(0, 10);
		//Action
		Page<ProductDTO> result = service.findAllPaged(pageable);
		//Assert
		Assertions.assertNotNull(result);
		Mockito.verify(repository).findAll(pageable);
	}
	
	
	@Test
	public void findByIdShouldReturnProducutDTOWhenIdExists() {
		
		ProductDTO result = service.findById(existingId);
		
		Assertions.assertNotNull(result);
		Mockito.verify(repository, Mockito.times(1)).findById(existingId);
	}
	
	
	@Test
	public void findByIdShouldThrowResourceNotFoundExceptionWhenIdDoesNotExists() {
		
		Assertions.assertThrows(ResourceNotFoundException.class, () -> {
			service.findById(unexistingId);
		});
	}
	
	
	
	@Test
	public void updateShouldReturnProductDTOWhenIdExists() {
		//Arrange
		ProductDTO productDTO= ProductFactoryTests.createProductDTO();
		//Action
		ProductDTO result = service.update(existingId, productDTO);
		//Assert
		Assertions.assertNotNull(result);
	}
	
	
	@Test
	public void updateShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist() {
		//Arrange
		ProductDTO productDTO= ProductFactoryTests.createProductDTO();
		
		//Assert
		Assertions.assertThrows(ResourceNotFoundException.class, () -> {
			//Action
			service.update(unexistingId, productDTO);
		});
	}
	
	
	

}
