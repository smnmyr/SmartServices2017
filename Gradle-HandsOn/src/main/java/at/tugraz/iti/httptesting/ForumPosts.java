package at.tugraz.iti.httptesting;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.json.JSONObject;

public class ForumPosts {

	public static ForumPosts getInstance() {
		if (INSTANCE == null) INSTANCE = new ForumPosts();
		return INSTANCE;
	}

	public int getNumReceivedPosts() {
		return allPosts.size();
	}

	public List<ForumPost> getAllPosts() {
		return allPosts;
	}

	public List<ForumPost> getNewestPosts() {
		List<ForumPost> newestPosts = new ArrayList<ForumPost>();
		for (int i = 0; i < 5; i++) newestPosts.add(allPosts.get(i));
		return newestPosts;
	}

	public void createNewPost(ForumPost newPost) {
		allPosts.add(newPost);
	}

	public void createNewPost(JSONObject postsObject) {
		createNewPost(ForumPost.unMarshall(postsObject));
	}

	private static List<ForumPost> allPosts = null;

	private static ForumPosts INSTANCE;

	private ForumPosts() {
		allPosts = new ArrayList<ForumPost>();

		DateTime dateTime = new DateTime(System.currentTimeMillis());

		allPosts.add(new ForumPost("User 1", "Message 1", dateTime));
		allPosts.add(new ForumPost("User 2", "Message 2", dateTime));
		allPosts.add(new ForumPost("User 3", "Message 3", dateTime));
		allPosts.add(new ForumPost("User 1", "Message 4", dateTime));
		allPosts.add(new ForumPost("User 2", "Message 5", dateTime));
		allPosts.add(new ForumPost("User 3", "Message 6", dateTime));
		allPosts.add(new ForumPost("simon", "hello world", dateTime));
	}

}
