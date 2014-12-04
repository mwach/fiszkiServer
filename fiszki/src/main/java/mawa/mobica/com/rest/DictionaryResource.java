package mawa.mobica.com.rest;

import java.sql.SQLException;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.xml.ws.http.HTTPException;

import mawa.mobica.com.dao.DictionaryDao;
import mawa.mobica.com.model.Dictionary;
import mawa.mobica.com.util.LogHelper;

@Path("/dictionary")
public class DictionaryResource {

	DictionaryDao dictionaryDao = DictionaryDao.getInstance();

	@GET
	@Path("/{id}")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Dictionary getDictionary(
		@PathParam("id") long id){

		try {
			return dictionaryDao.get(id);
		} catch (SQLException e) {
			LogHelper.error(DictionariesResource.class, "getDictionary", e.getLocalizedMessage());
			throw new HTTPException(Status.NOT_FOUND.getStatusCode());
		}
	}

	@POST
	@Consumes({ MediaType.APPLICATION_JSON })
	public Response addDictionary(Dictionary dictionary){

		Long dictionaryId = null;
		try {
			dictionaryId = dictionaryDao.create(dictionary).getId();
		} catch (SQLException e) {
			LogHelper.error(DictionariesResource.class, "addDictionary", e.getLocalizedMessage());
			throw new HTTPException(Status.INTERNAL_SERVER_ERROR.getStatusCode());
		}
		return Response.status(Status.CREATED).entity(dictionaryId).build();
	}

	@POST
	@Path("/{id}")
	@Consumes({ MediaType.APPLICATION_JSON })
	public Response updateDictionary(
			@PathParam("id") long id, 
			Dictionary dictionary){

		dictionary.setId(id);
		Long dictionaryId = null;
		try {
			dictionaryDao.update(dictionary);
		} catch (SQLException e) {
			LogHelper.error(DictionariesResource.class, "updateDictionary", e.getLocalizedMessage());
			throw new HTTPException(Status.INTERNAL_SERVER_ERROR.getStatusCode());
		}
		return Response.status(Status.OK).entity(dictionaryId).build();
	}

}
