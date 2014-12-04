package mawa.mobica.com.rest.client;

import java.net.URI;

import javax.ws.rs.core.UriBuilder;

import mawa.mobica.com.model.Dictionary;

import com.google.gson.Gson;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.ClientResponse.Status;
import com.sun.jersey.api.client.WebResource;

public class DictionaryClient {

	public static void main(String[] args) {
		DictionaryClient dc = new DictionaryClient();
		dc.put();
		dc.get();
	}

	private void put() {

		Dictionary dictionary = new Dictionary();
		dictionary.setName("name");
		String dictionarySttring = new Gson().toJson(dictionary);
		
		Client client = Client.create();
		 
		WebResource webResource = client
		   .resource("http://localhost:8080/fiszki/rest/dictionary");
 
		ClientResponse response = webResource.type("application/json")
                   .post(ClientResponse.class, dictionarySttring);
 
		if (response.getStatus() != Status.CREATED.getStatusCode()) {
		   throw new RuntimeException("Failed : HTTP error code : "
			+ response.getStatus());
		}
 
		Long dictionaryId = response.getEntity(Long.class);
 
		System.out.println("Output from Server .... \n");
		System.out.println(dictionaryId);
	}

	private void get() {

		Client client = Client.create();
		 
		WebResource webResource = client
		   .resource("http://localhost:8080/fiszki/rest/dictionary/1");
 
		ClientResponse response = webResource.accept("application/json")
                   .get(ClientResponse.class);
 
		if (response.getStatus() != 200) {
		   throw new RuntimeException("Failed : HTTP error code : "
			+ response.getStatus());
		}
 
		Dictionary output = response.getEntity(Dictionary.class);
 
		System.out.println("Output from Server .... \n");
		System.out.println(output);
	}

	private static URI getBaseURI() {

		return UriBuilder.fromUri(
				"http://localhost:8080/fiszki/rest/dictionary").build();

	}
}
