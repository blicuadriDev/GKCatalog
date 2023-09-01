package com.godknows.gkcatalog.resources;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.godknows.gkcatalog.dtos.ProductDTO;
import com.godknows.gkcatalog.services.ProductService;

import tests.ProductFactoryTests;

@WebMvcTest(ProductResource.class)
public class ProductResourceTest {
	
		private PageImpl<ProductDTO> page;
		private ProductDTO productDTO;
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private ProductService service;
	
	
	
	@BeforeEach
	void setUp() throws Exception {
		productDTO = ProductFactoryTests.createProductDTO();
		page = new PageImpl<>(List.of(productDTO));
		
		when(service.findAllPaged(any())).thenReturn(page);
	}
	
	
	@Test
	public void findAllShouldReturnPage() throws Exception {
		
		//mockMvc.perform(get("/products")).andExpect(status().isOk());
		//Better readable below:
		ResultActions result = mockMvc.perform(get("/products")
				.accept(MediaType.APPLICATION_JSON));
		result.andExpect(status().isOk());
	}

}
