package mawa.mobica.com.dao.exception;

import java.sql.SQLException;

public class NotFoundException extends SQLException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NotFoundException(String message){
		super(message);
	}

	public NotFoundException(String message, Throwable throwable){
		super(message, throwable);
	}
}
