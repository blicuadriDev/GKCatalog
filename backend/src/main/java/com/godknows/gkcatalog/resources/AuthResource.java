package com.godknows.gkcatalog.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.godknows.gkcatalog.dtos.EmailDTO;
import com.godknows.gkcatalog.dtos.NewPasswordDTO;
import com.godknows.gkcatalog.services.AuthService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/auth")
public class AuthResource {
	
	@Autowired
	private AuthService authService;
	
	@PostMapping(value = "/recover-token")
	public ResponseEntity<Void> createRecoverToken(@Valid @RequestBody EmailDTO body){
		authService.createRecoverToken(body);
		return ResponseEntity.noContent().build();
	}
	
	@PutMapping(value = "/new-token")
	public ResponseEntity<Void> saveNewPassword(@Valid @RequestBody NewPasswordDTO body){
		authService.saveNewPassword(body);
		return ResponseEntity.noContent().build();
	}

	
}
