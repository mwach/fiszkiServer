package mawa.mobica.com.util;

import java.util.logging.Level;
import java.util.logging.Logger;

public final class LogHelper {

	private static boolean debugLoggable = Logger.getGlobal().isLoggable(Level.CONFIG);
	private static boolean infoLoggable = Logger.getGlobal().isLoggable(Level.INFO);
	private static boolean warnLoggable = Logger.getGlobal().isLoggable(Level.WARNING);
	private static boolean severeLoggable = Logger.getGlobal().isLoggable(Level.SEVERE);

	private LogHelper(){}
	
	public static final void debug(Class<?> className, String method, String message){
		if(debugLoggable){
			Logger.getLogger(className.getName()).logp(Level.CONFIG, 
					className.getName(), message, format(message));
		}
	}

	public static final void info(Class<?> className, String method, String message){
		if(infoLoggable){
			Logger.getLogger(className.getName()).logp(Level.INFO, 
					className.getName(), message, format(message));
		}
	}

	public static final void warn(Class<?> className, String method, String message){
		if(warnLoggable){
			Logger.getLogger(className.getName()).logp(Level.WARNING, 
					className.getName(), message, format(message));
		}
	}

	public static final void error(Class<?> className, String method, String message, Throwable throwable){
		if(severeLoggable){
			Logger.getLogger(className.getName()).logp(Level.SEVERE, 
					className.getName(), message, format(message), throwable);
		}
	}

	public static final void error(Class<?> className, String method, String message){
		if(severeLoggable){
			Logger.getLogger(className.getName()).logp(Level.SEVERE, 
					className.getName(), message, format(message));
		}
	}

	private static final String format(String message){
		return String.format("[%d] %s" , Thread.currentThread().getId(), message);
	}
}
