package at.tugraz.iti.httptesting.utilities;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import javax.ws.rs.core.UriBuilder;

import at.tugraz.iti.httptesting.Constants;
import at.tugraz.iti.httptesting.ForumPost;
import at.tugraz.iti.httptesting.SimpleForum;

public class ServerUtilities {
	
	public static String getCurrentTime() {
		Date date = new Date(System.currentTimeMillis());
		SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
		String dateString = formatter.format(date);
		return dateString;
	}	
	
	public static List createSimpleForumPostsList(List<ForumPost> allActivities) {
		List latestActivities = new ArrayList();
				
		for (ForumPost activity : allActivities) {
			Map activityMap = new HashMap();
			activityMap.put("author", activity.getAuthor());
			activityMap.put("message", activity.getMessage());
			activityMap.put("pubdate", activity.getPubDate().toString("yyyy-MM-dd hh:mm:ss"));
			latestActivities.add(activityMap);
		}
		
		return latestActivities;
	}
	
	public static String getIPAddress() {
		try {
			Enumeration nInterfaces = NetworkInterface.getNetworkInterfaces();
			while (nInterfaces.hasMoreElements()) {
				NetworkInterface nInterface = (NetworkInterface) nInterfaces.nextElement();
				if (!nInterface.isLoopback() && nInterface.isUp()) {
					Enumeration nIfAddresses = nInterface.getInetAddresses();
					while (nIfAddresses.hasMoreElements()) {
						InetAddress nIfAddress = (InetAddress) nIfAddresses.nextElement();
						try {
							// Test for IPv4 address
							Inet4Address nicAddrIPv4 = (Inet4Address) nIfAddress;
							return nIfAddress.getHostAddress();
						} catch (Exception e) {
							SimpleForum.logger.log(Level.FINE, "Could not find IPv4 address of interface");
						}
					}
				}
			}
		} catch (SocketException e1) {
			System.out.println("SocketException in Networking.getIPAddress!");
		}
		
		return null;
	}

	public static URI getBaseURI(int port) {
		String interfaceAddress = getIPAddress();
	
		if (interfaceAddress != null) {
			String prefix = "http://";
			SimpleForum.logger.log(Level.FINE, "Binding to Network Interface IP Address: " + interfaceAddress);
			return UriBuilder.fromUri(prefix + interfaceAddress + "/").port(port).build();
		} else {
			System.err.println("Not Network Interface IP Address found! Exiting.");
			System.exit(1);
		}
	
		return null;
	}

	private static URI getLoopbackURI() {
		return UriBuilder.fromUri("http://localhost/").port(Constants.serverPort).build();
	}
}
