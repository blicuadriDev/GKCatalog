package com.godknows.gkcatalog.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.godknows.gkcatalog.dtos.CategoryDTO;
import com.godknows.gkcatalog.entities.Category;
import com.godknows.gkcatalog.repositories.CategoryRepository;

@Service
public class CategoryService {
	
	@Autowired
	private CategoryRepository repository;
	
	@Transactional(readOnly = true)
	public List<CategoryDTO> findAll(){
		List<Category> list = repository.findAll();
		
		//List<CategoryDTO> listDto = new ArrayList<>();
		//for(Category cat : list) {
		//	listDto.add(new CategoryDTO(cat));
		//}
		//return listDto;
		// OR ELSE VENE BETTER AND SAME RESULT
		
		return  list.stream().map(x -> new CategoryDTO(x)).collect(Collectors.toList());
	}

}
