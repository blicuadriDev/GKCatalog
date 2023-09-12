package com.godknows.gkcatalog.resources.exceptions;

import java.io.Serializable;

public class FieldMessage implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String fieldName;
	private String fieldError;
	
	
	public FieldMessage() {
	}


	public FieldMessage(String fieldName, String fieldError) {
		this.fieldName = fieldName;
		this.fieldError = fieldError;
	}


	public String getFieldName() {
		return fieldName;
	}


	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}


	public String getFieldError() {
		return fieldError;
	}


	public void setFieldError(String fieldError) {
		this.fieldError = fieldError;
	}
	

}
