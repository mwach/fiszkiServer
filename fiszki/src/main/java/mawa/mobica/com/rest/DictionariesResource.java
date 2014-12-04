package mawa.mobica.com.rest;

import java.sql.SQLException;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response.Status;
import javax.xml.ws.http.HTTPException;

import mawa.mobica.com.dao.DictionaryDao;
import mawa.mobica.com.rest.dto.Dictionary;
import mawa.mobica.com.util.DictionaryHelper;
import mawa.mobica.com.util.LogHelper;

@Path("/dictionaries")
public class DictionariesResource {

	DictionaryDao dictionaryDao = DictionaryDao.getInstance();

	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public List<Dictionary> getDictionaries(
			@QueryParam("baseLanguage") String baseLanguage,
			@QueryParam("refLanguage") String refLanguage
			){

		try {
			return DictionaryHelper.getInstance().toDto(dictionaryDao.enumerate(baseLanguage, refLanguage));
		} catch (SQLException e) {
			LogHelper.error(getClass(), "getDictionaries", e.getLocalizedMessage());
			throw new HTTPException(Status.NOT_FOUND.getStatusCode());
		}
	}
}
