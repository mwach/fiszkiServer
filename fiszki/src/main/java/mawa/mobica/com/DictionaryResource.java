package mawa.mobica.com;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import mawa.mobica.com.dao.DictionaryDao;
import mawa.mobica.com.model.Dictionary;

@Path("/dictionary")
public class DictionaryResource {

	DictionaryDao dd = DictionaryDao.getInstance();

	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Dictionary getDictionary() {

		return dd.getDictionaries(null, null).get(0);
	}
}
