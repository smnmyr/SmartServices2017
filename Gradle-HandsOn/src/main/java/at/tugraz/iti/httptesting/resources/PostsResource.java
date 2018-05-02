package at.tugraz.iti.httptesting.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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

@Path(AbstractResource.postsPath)
public class PostsResource extends AbstractResource {

	@GET
	@Produces(MediaType.TEXT_HTML)
	public Response returnPostsHTML(@Context HttpHeaders headers) {
		logger.log(Level.INFO, "Got a GET at the " + AbstractResource.postsPath + " endpoint " + MediaType.TEXT_HTML);

		Map root = new HashMap();

		Date date = new Date(System.currentTimeMillis());
		SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
		String dateString = formatter.format(date);

		root.put("serverTime", dateString);
		root.put("postsCount", ForumPosts.getInstance().getNumReceivedPosts());

		if (ForumPosts.getInstance().getNumReceivedPosts() > 0) {
			List<ForumPost> allPosts = ForumPosts.getInstance().getAllPosts();
			root.put("latestPosts", ServerUtilities.createSimpleForumPostsList(allPosts));
		}

		root.put("postsResource", true);

		return ResponsesFactory.createHTMLResponseFromTemplate("forum_index.ftl", root);
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response returnPostsJSON(@Context HttpHeaders headers) {
		logger.log(Level.INFO, "Got a GET at " + PostsResource.class.getName() + " endpoint (JSON).");

		List<ForumPost> allPosts = ForumPosts.getInstance().getAllPosts();

		JSONObject allPostsObject = new JSONObject();
		allPostsObject.put("totalItems", allPosts.size());

		JSONArray forumPostsArray = new JSONArray();

		for (ForumPost forumPost : allPosts) {
			forumPostsArray.put(ForumPost.marshall(forumPost));
		}

		allPostsObject.put("items", forumPostsArray);

		return ResponsesFactory.createJSONResponse(allPostsObject);
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response receivePost(String newPost, @Context HttpHeaders headers) {
		logger.log(Level.INFO, "Got a POST of (application/json) at the " + AbstractResource.postsPath + " endpoint.");

		logger.log(Level.INFO, "Received ForumPost object: " + newPost);

		try {
			ForumPost incomingForumPost = ForumPost.unMarshall(new JSONObject(newPost));
			ForumPosts.getInstance().createNewPost(incomingForumPost);
		} catch (JSONException e) {
			throw new WebApplicationException(ResponsesFactory.createSimpleResponse(Status.BAD_REQUEST, "JSONException when parsing entity: " + e.getMessage()));
		}

		return ResponsesFactory.createSimpleResponse(Status.CREATED, "Post received.\n\nNo new resource was created in response to this request.\n");
	}

}
