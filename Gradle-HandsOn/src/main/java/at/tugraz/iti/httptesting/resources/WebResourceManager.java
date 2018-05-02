package at.tugraz.iti.httptesting.resources;

import java.io.InputStream;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import at.tugraz.iti.httptesting.utilities.ResponsesFactory;

/**
 * Pseudo-Resource that handles static resources: images, stylesheets, scripts, API documentation
 * 
 * @author Simon Mayer, CT US
 *
 */
@Path("/webresources")
public class WebResourceManager extends AbstractResource {

	@GET
	@Path("/images/{picturename}")
	@Produces("images/*")
	public Response getImage(@PathParam("size") String size, @PathParam("picturename") String picturename) {
		return getResourceResponse("images/" + picturename, "images/*");
	}

	@GET
	@Path("/stylesheets/{scriptname}")
	@Produces("text/css")
	public Response getStylesheet(@PathParam("scriptname") String scriptname) {
		return getResourceResponse("stylesheets/" + scriptname, "text/css");
	}
	
	@GET
	@Path("/scripts/{scriptname}")
	@Produces("text/css")
	public Response getScript(@PathParam("scriptname") String scriptname) {
		return getResourceResponse("scripts/" + scriptname, "application/js");
	}
		
	@GET
	@Path("/apidoc")
	@Produces("text/html")
	public Response getDescription() {
		return getResourceResponse("apidoc/index.html", "text/html");
	}

	private Response getResourceResponse(String string, String mediaType) {
		InputStream is = this.getClass().getClassLoader().getResourceAsStream(string);

		if (is != null) {
			return Response.ok(is, mediaType).build();
		} else {
			return ResponsesFactory.createStatusCodeResponse(Status.NOT_FOUND);
		}
	}
}
