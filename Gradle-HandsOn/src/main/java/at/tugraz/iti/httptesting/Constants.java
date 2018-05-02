package at.tugraz.iti.httptesting;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Loads constants from SimpleForum.properties file and runs self-check.
 * 
 * @author Simon Mayer, CT US
 *
 */
public class Constants {

	public static int serverPort;
	
	static Logger logger = Logger.getLogger(Constants.class.getName());

	static {
		logger.setLevel(Level.ALL);
	}
	
	public static void initialize() {
		initializeProperties();
		checkConstants();
	}

	private static void checkConstants() {
		for (Field field : Constants.class.getDeclaredFields()) {
		    field.setAccessible(true);
		    String name = field.getName();
		    
			try {
			    Object value = field.get(null);
			    logger.info("Parameter \"" + name + "\" : " + value);
				if (value == null) System.out.printf("Field " + name + " has probably not been correctly initialized (value: null)");
			} catch (IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
			}		    
		}
	}

	private static void initializeProperties() {
		Properties configFile = new Properties();

		try {
			configFile.load(Constants.class.getClassLoader().getResourceAsStream("SimpleForum.properties"));
			serverPort = Integer.parseInt(configFile.getProperty("ServerPort"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
