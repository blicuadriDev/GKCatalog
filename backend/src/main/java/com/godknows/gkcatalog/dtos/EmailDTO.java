package com.godknows.gkcatalog.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class EmailDTO {
	
	@NotBlank (message="Campo obrigatório")
	@Email(message="Insira um email válido")
	private String email;
	
	
	
	public EmailDTO() {
	}

	public EmailDTO(String email) {
		this.email = email;
	}

	
	public String getEmail() {
		return email;
	}

}
