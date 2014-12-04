package mawa.mobica.com.rest;

import java.sql.SQLException;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.xml.ws.http.HTTPException;

import mawa.mobica.com.dao.LanguageDao;
import mawa.mobica.com.rest.dto.Language;
import mawa.mobica.com.util.LanguageHelper;
import mawa.mobica.com.util.LogHelper;

@Path("/language")
public class LanguageResource {

	LanguageDao languageDao = LanguageDao.getInstance();

	@GET
	@Path("/{id}")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Language getLanguage(
		@PathParam("id") long id){

		try {
			return LanguageHelper.toDto(languageDao.get(id));
		} catch (SQLException e) {
			LogHelper.error(DictionariesResource.class, "getLanguage", e.getLocalizedMessage());
			throw new HTTPException(Status.NOT_FOUND.getStatusCode());
		}
	}

	@POST
	@Consumes({ MediaType.APPLICATION_JSON })
	public Response addLanguage(Language language){

		Long languageId = null;
		try {
			languageId = languageDao.create(LanguageHelper.fromDto(language)).getId();
		} catch (SQLException e) {
			LogHelper.error(DictionariesResource.class, "addLanguage", e.getLocalizedMessage());
			throw new HTTPException(Status.INTERNAL_SERVER_ERROR.getStatusCode());
		}
		return Response.status(Status.CREATED).entity(String.valueOf(languageId)).build();
	}

	@PUT
	@Path("/{id}")
	@Consumes({ MediaType.APPLICATION_JSON })
	public Response updateLanguage(Language language,
			@PathParam("id") long id){
		try {
			language.setId(id);
			languageDao.update(LanguageHelper.fromDto(language));
		} catch (SQLException e) {
			LogHelper.error(DictionariesResource.class, "updateLanguage", e.getLocalizedMessage());
			throw new HTTPException(Status.NOT_FOUND.getStatusCode());
		}
		return Response.status(Status.OK).build();
	}

	@DELETE
	@Path("/{id}")
	public Response deleteLanguage(
			@PathParam("id") long id){

		try {
			languageDao.delete(id);
		} catch (SQLException e) {
			LogHelper.error(DictionariesResource.class, "deleteLanguage", e.getLocalizedMessage());
			throw new HTTPException(Status.NOT_FOUND.getStatusCode());
		}
		return Response.status(Status.OK).build();
	}

}
