package at.tugraz.iti.httptesting;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Scanner;
import java.util.logging.Logger;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import at.tugraz.iti.httptesting.utilities.ServerUtilities;
import freemarker.template.Configuration;

/**
 * The SimpleForum server
 * 
 * @author Simon Mayer, CT US
 *
 */
public class SimpleForumServer {

	public static Logger logger = Logger.getLogger(SimpleForumServer.class.getName());

	private static Configuration freemarker = null;

	static void launchPublicServer() {
		try {
			URI bindURI = new URI("http://0.0.0.0:" + Constants.serverPort + "/");
			logger.info("Starting SimpleForum Server @ " + Constants.serverPort + ": " + ServerUtilities.getBaseURI(Constants.serverPort).toString());
			final ResourceConfig resourceConfiguration = setupResourceConfig();
			HttpServer server = GrizzlyHttpServerFactory.createHttpServer(bindURI, resourceConfiguration);			
			server.start();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
	}

	public static Configuration getFreemarker() {
		if (freemarker == null) setUpFreemarkerTemplatingEngine();
		return freemarker;
	}

	private static ResourceConfig setupResourceConfig() {
		return new ResourceConfig().packages("at.tugraz.iti.httptesting.resources");
	}

	private static void setUpFreemarkerTemplatingEngine() {
		freemarker = new Configuration(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS);
		freemarker.setClassForTemplateLoading(SimpleForumServer.class, "/freemarker_templates");
	}

	static void listen() {
		// This is an idiotic wait but works with nohup
		waitForeverWhile();

		// This is a better wait, but doesn't agree with the nohup program
		// waitForeverCommandLine();
		System.exit(0);
	}

	private static void waitForeverWhile() {
		System.out.println("SimpleForum has started and is ready to go. The server is now listening forever.");
		while (true) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private static void waitForeverCommandLine() {
		System.out.println("SimpleForum has started and is ready to go. Enter <exit> to exit.");
		Scanner s = new Scanner(System.in);

		while (true) {
			String msg = s.nextLine();

			if (msg.equals("exit")) break;
			else System.out.println("Enter <exit> to exit.");
		}

		s.close();
	}
}
