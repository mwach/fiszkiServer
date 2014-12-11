package mawa.mobica.com.rest.client;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.ws.rs.core.UriBuilder;

import mawa.mobica.com.rest.dto.Language;

import com.google.gson.Gson;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.ClientResponse.Status;
import com.sun.jersey.api.client.WebResource;

public class LanguageClient {

	private static List<String[]> languages = new ArrayList<String[]>();
	static{
		languages.add(new String[]{"en", "English"});
		languages.add(new String[]{"pl", "Polish"});
	}

	public static void main(String[] args) {
		LanguageClient lc = new LanguageClient();
		lc.insertDefaultLanguages();
	}

	private void insertDefaultLanguages() {
		for (String[] languageStr : languages) {
			Language language = new Language();
			language.setName(languageStr[0]);
			language.setDescription(languageStr[1]);
			post(language);
		}
	}

	private long post(Language language) {

		String languageJson = new Gson().toJson(language);
		
		Client client = Client.create();
		 
		WebResource webResource = client
		   .resource(getBaseURI());
 
		ClientResponse response = webResource.type("application/json")
                   .post(ClientResponse.class, languageJson);
 
		if (response.getStatus() != Status.CREATED.getStatusCode()) {
		   throw new RuntimeException("Failed : HTTP error code : "
			+ response.getStatus());
		}else{
 
		String languageId = response.getEntity(String.class);
 
		System.out.println("Output from Server .... \n");
		System.out.println(languageId);
		return Long.parseLong(languageId);
		}
	}

	private void update(long languageId) {

		Language language = new Language();
		language.setName(UUID.randomUUID().toString());
		language.setDescription("english");
		String languageJson = new Gson().toJson(language);
		
		Client client = Client.create();
		 
		WebResource webResource = client
		   .resource(getBaseURI(languageId));
 
		ClientResponse response = webResource.type("application/json")
                   .put(ClientResponse.class, languageJson);
 
		if (response.getStatus() != Status.CREATED.getStatusCode()) {
		   throw new RuntimeException("Failed : HTTP error code : "
			+ response.getStatus());
		}
	}

	private void get(long languageId) {

		Client client = Client.create();
		 
		WebResource webResource = client
		   .resource(getBaseURI(languageId));
 
		ClientResponse response = webResource.accept("application/json")
                   .get(ClientResponse.class);
 
		if (response.getStatus() != 200) {
		   throw new RuntimeException("Failed : HTTP error code : "
			+ response.getStatus());
		}
 
		Language output = response.getEntity(Language.class);
 
		System.out.println("Output from Server .... \n");
		System.out.println(output);
	}

	private void delete(long languageId) {

		Client client = Client.create();
		 
		WebResource webResource = client
		   .resource(getBaseURI(languageId));
 
		ClientResponse response = webResource.accept("application/json")
                   .delete(ClientResponse.class);
 
		if (response.getStatus() != 200) {
		   throw new RuntimeException("Failed : HTTP error code : "
			+ response.getStatus());
		}
 	}

	private URI getBaseURI() {

		return UriBuilder.fromUri(
				"http://localhost:8080/fiszki/rest/language").build();
	}

	private URI getBaseURI(long resourceId) {

		return UriBuilder.fromUri(
				String.format("http://localhost:8080/fiszki/rest/language/%d", resourceId)).build();
	}

}
