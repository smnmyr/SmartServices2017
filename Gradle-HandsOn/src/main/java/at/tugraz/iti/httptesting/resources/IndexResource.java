package at.tugraz.iti.httptesting.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import at.tugraz.iti.httptesting.ForumPost;
import at.tugraz.iti.httptesting.ForumPosts;
import at.tugraz.iti.httptesting.utilities.ResponsesFactory;
import at.tugraz.iti.httptesting.utilities.ServerUtilities;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

@Path(AbstractResource.rootPath)
public class IndexResource extends AbstractResource {

	@GET
	@Produces(MediaType.TEXT_HTML)
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Response getASBaseOverviewHumanReadable(@Context HttpHeaders headers) {

		logger.log(Level.INFO, "Got a GET at the " + AbstractResource.rootPath + " endpoint " + MediaType.TEXT_HTML);

		Map root = new HashMap();

		Date date = new Date(System.currentTimeMillis());
		SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
		String dateString = formatter.format(date);

		root.put("serverTime", dateString);
		root.put("postsCount", ForumPosts.getInstance().getNumReceivedPosts());
		
		if (ForumPosts.getInstance().getNumReceivedPosts() > 0) {
			List<ForumPost> allPosts = ForumPosts.getInstance().getNewestPosts();
			root.put("latestPosts", ServerUtilities.createSimpleForumPostsList(allPosts));
		}

		return ResponsesFactory.createHTMLResponseFromTemplate("forum_index.ftl", root);
	}
}
