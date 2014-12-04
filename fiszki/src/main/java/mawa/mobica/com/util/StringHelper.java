package mawa.mobica.com.util;

public final class StringHelper {

	private StringHelper(){}
	
	public static String toString(Object object){
		return object == null ? "(null)" : object.toString();
	}
}
