package at.tugraz.iti.httptesting;

import java.net.URISyntaxException;
import java.util.logging.Logger;

/**
 * This is the base class of SimpleForum.
 *   
 * @author Simon Mayer, CT US
 *
 */
public class SimpleForum {

	public static Logger logger = Logger.getLogger(SimpleForum.class.getName());

	public static void main(String[] args) throws URISyntaxException {
		Constants.initialize();
		SimpleForumServer.launchPublicServer();
		SimpleForumServer.listen();
	}
	
}
