package com.godknows.gkcatalog.services;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.godknows.gkcatalog.dtos.RoleDTO;
import com.godknows.gkcatalog.dtos.UserDTO;
import com.godknows.gkcatalog.dtos.UserInsertDTO;
import com.godknows.gkcatalog.dtos.UserUpdateDTO;
import com.godknows.gkcatalog.entities.Role;
import com.godknows.gkcatalog.entities.User;
import com.godknows.gkcatalog.repositories.RoleRepository;
import com.godknows.gkcatalog.repositories.UserRepository;
import com.godknows.gkcatalog.services.exceptions.DatabaseException;
import com.godknows.gkcatalog.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;

@Service
public class UserService {
	
	@Autowired
	private UserRepository repository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	
	@Transactional(readOnly = true)
	public Page<UserDTO> findAllPaged(Pageable pageable){
		Page<User> list = repository.findAll(pageable);
		return  list.map(x -> new UserDTO(x));
	}

	
	@Transactional(readOnly = true)
	public UserDTO findById(Long id) {
		Optional<User> obj = repository.findById(id);
		User entity = obj.orElseThrow( () -> new ResourceNotFoundException("Entity not found"));
		return new UserDTO(entity);
	}


	@Transactional
	public UserDTO insert(UserInsertDTO dto) {
		User entity = new User();
		copyDtoToEntity(dto, entity);
		entity.setPassword(passwordEncoder.encode(dto.getPassword()));
		entity = repository.save(entity);
		return new UserDTO(entity);
	}

	
	@Transactional
	public UserDTO update(Long id, UserUpdateDTO dto) {
		try {
			User entity = repository.getReferenceById(id);
			copyDtoToEntity(dto, entity);
			entity = repository.save(entity);
			return new UserDTO(entity);
		}
		catch(EntityNotFoundException e) {
			throw new ResourceNotFoundException("User ID " + id + " Not Found");
		}
	}


	@Transactional(propagation = Propagation.SUPPORTS)
	public void delete(Long id) {
		if (!repository.existsById(id)) {
			throw new ResourceNotFoundException("Recurso não encontrado");
		}
		try {
	        	repository.deleteById(id);    		
		}
	    	catch (DataIntegrityViolationException e) {
	        	throw new DatabaseException("Falha de integridade referencial");
	   	}
	}

	
	private void copyDtoToEntity (UserDTO dto, User entity) {
		entity.setFirstName(dto.getFirstName());
		entity.setLastName(dto.getLastName());
		entity.setEmail(dto.getEmail());
		
		entity.getRoles().clear();
		for (RoleDTO roleDto : dto.getRoles()) {
			Role role = roleRepository.getReferenceById(roleDto.getId());
			entity.getRoles().add(role);
		}
	}
	
}
