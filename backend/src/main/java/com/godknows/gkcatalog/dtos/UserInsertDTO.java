package com.godknows.gkcatalog.dtos;

import com.godknows.gkcatalog.services.validations.UserInsertValid;

@UserInsertValid
public class UserInsertDTO extends UserDTO{
	private static final long serialVersionUID = 1L;
	
	
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
