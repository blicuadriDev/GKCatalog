package com.godknows.gkcatalog.dtos;

import com.godknows.gkcatalog.services.validations.UserInsertValid;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@UserInsertValid
public class UserInsertDTO extends UserDTO{
	private static final long serialVersionUID = 1L;
	
	@NotBlank(message = "Campo Obrigatório")
	@Size(min = 8, message = "Deve ter pelo menos 8 caracteres")
	private String password;
	
	
	public UserInsertDTO() {
	}

	public UserInsertDTO(String password) {
		super();
		this.password = password;
	}

	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
}
