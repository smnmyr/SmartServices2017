package at.tugraz.iti.httptesting.resources;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Constants for all resources
 * 
 * @author Simon Mayer, CT US
 *
 */
public abstract class AbstractResource {

	public static final String rootPath = "/";
	public static final String postsPath = rootPath + "posts/";

	protected static Logger logger = Logger.getLogger(AbstractResource.class.getName());
	
	static {
		logger.setLevel(Level.INFO);
	}
	
}
