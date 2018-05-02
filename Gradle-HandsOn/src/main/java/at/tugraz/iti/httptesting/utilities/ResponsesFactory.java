package at.tugraz.iti.httptesting.utilities;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Map;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;

import org.json.JSONObject;

import at.tugraz.iti.httptesting.SimpleForumServer;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class ResponsesFactory {

	public static Response createJSONResponse(JSONObject returnObject) {
		return ResponsesFactory.justReturnThisContent(returnObject.toString());
	}

	@SuppressWarnings("rawtypes")
	public static Response createHTMLResponseFromTemplate(String templatePath, Map templateMap) {
		try {
			Template temp = SimpleForumServer.getFreemarker().getTemplate(templatePath);
			Writer out = new StringWriter();
			temp.process(templateMap, out);

			return ResponsesFactory.justReturnThisContent(out.toString());
		} catch (IOException | TemplateException e) {
			e.printStackTrace();
		}

		return null;
	}
	
	private static Response justReturnThisContent(String content) {
		ResponseBuilder response = Response.status(Status.OK).entity(content);
		addCORSAuthenticationHeaders(response);
		return response.build();
	}
	
	public static Response createSimpleResponse(Status status, String message) {
		ResponseBuilder response = Response.status(status).entity(message);
		addCORSAuthenticationHeaders(response);
		return response.build();
	}

	public static Response createStatusCodeResponse(Status statusCode) {
		return createSimpleResponse(statusCode, "A description of the status code.");
	}
	
	public static ResponseBuilder addCORSOptionsHeader(ResponseBuilder response) {
		return response.header("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-With, Session");
	}
	
	public static ResponseBuilder addCORSAuthenticationHeaders(ResponseBuilder response) {
		return response.header("Access-Control-Allow-Origin", "*")
				.header("Access-Control-Allow-Methods", "*");				
	}

}
