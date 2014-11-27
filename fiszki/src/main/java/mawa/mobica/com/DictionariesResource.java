package mawa.mobica.com;

import java.util.Arrays;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import mawa.mobica.com.model.Dictionary;

@Path("/dictionaries")
public class DictionariesResource {

	@GET
	  @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	 public List<Dictionary> getDictionaries(
             @QueryParam(value = "baseLanguage") 
             final String baseLanguage,
             @QueryParam(value = "refLanguage") 
             final String refLanguage
			 ){
		Dictionary dict = new Dictionary();
		dict.setDescription("desc");
		dict.setName("name");
		return Arrays.asList(dict, dict);
	}
}
