package com.godknows.gkcatalog.services.validations;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.godknows.gkcatalog.dtos.UserInsertDTO;
import com.godknows.gkcatalog.entities.User;
import com.godknows.gkcatalog.repositories.UserRepository;
import com.godknows.gkcatalog.resources.exceptions.FieldMessage;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class UserInsertValidator implements ConstraintValidator<UserInsertValid, UserInsertDTO> {
	
	@Autowired
	private UserRepository repository;
	
	@Override
	public void initialize(UserInsertValid ann) {
	}

	@Override
	public boolean isValid(UserInsertDTO dto, ConstraintValidatorContext context) {
		
		List<FieldMessage> list = new ArrayList<>();
		
		User user = repository.findByEmail(dto.getEmail());
		if (user != null) {
			list.add(new FieldMessage("email", "Email já cadastrado"));
		}
		
		for (FieldMessage e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName()).addConstraintViolation();
		}
		
		return list.isEmpty();
	}
}
