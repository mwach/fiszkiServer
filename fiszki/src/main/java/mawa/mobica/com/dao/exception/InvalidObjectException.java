package mawa.mobica.com.dao.exception;

import java.sql.SQLException;

public class InvalidObjectException extends SQLException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InvalidObjectException(String message, Throwable throwable){
		super(message, throwable);
	}
}
