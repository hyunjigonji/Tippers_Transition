package tippersTree;

import com.google.gson.Gson;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.util.ArrayList;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.ClientResource;
import org.restlet.resource.ResourceException;

public class Call {
	public static String callVS(Statement request) {
		String response = "";
		String formerJson = getJson(request.Former);
		StringRepresentation requestMsg = new StringRepresentation(formerJson);

		String addr = "http://" + request.Address + "/" + request.Observation;

		System.out.println("< Sending request to  " + addr + " >");
		System.out.println(formerJson);
		System.out.println();

		ClientResource client = new ClientResource(addr);

//		try {
//			response = client.post(requestMsg).getText(); // {count = ?}
//
//			JsonParser jp = new JsonParser();
//			response = jp.parse(response).getAsJsonObject().get("count").getAsString();
//
//		} catch (ResourceException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			System.out.println("Failed");
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			System.out.println("Failed");
//		}

		return response;
	}

	private static String getJson(ArrayList<Statement> request) {
		Gson gson = new Gson();

		return gson.toJson(request);
	}
}
