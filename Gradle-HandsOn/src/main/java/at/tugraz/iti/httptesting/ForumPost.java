package at.tugraz.iti.httptesting;

import org.joda.time.DateTime;
import org.json.JSONObject;

public class ForumPost {

	private String authorName;
	private String message;
	private DateTime publicationDate;
	
	public ForumPost(String authorName, String message, DateTime publicationDate) {
		assert(authorName != null);
		assert(message != null);
		assert(publicationDate != null);
		
		this.authorName = authorName;
		this.message = message;
		this.publicationDate = publicationDate;
	}

	public String getAuthor() {
		return this.authorName;
	}

	public String getMessage() {
		return this.message;
	}

	public DateTime getPubDate() {
		return this.publicationDate;
	}
	
	public static JSONObject marshall(ForumPost forumPost) {
		JSONObject postObject = new JSONObject();
		postObject.put("author", forumPost.getAuthor());
		postObject.put("message", forumPost.getMessage());
		postObject.put("pubdate", forumPost.getPubDate().toString("yyyy-MM-dd hh:mm:ss"));
		return postObject;
	}
	
	public static ForumPost unMarshall(JSONObject postsObject) {
		String author = postsObject.getString("author");
		String message = postsObject.getString("message");
		String pubDateString = postsObject.optString("pubdate");
		
		DateTime dateTime;
		if (pubDateString.length() == 0) dateTime = new DateTime(System.currentTimeMillis());
		else dateTime = DateTime.parse(pubDateString);
		
		return new ForumPost(author, message, dateTime);
	}
}
