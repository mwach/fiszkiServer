package mawa.mobica.com.rest;

import java.sql.SQLException;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response.Status;

import mawa.mobica.com.dao.DictionaryDao;
import mawa.mobica.com.rest.dto.Dictionary;
import mawa.mobica.com.util.DictionaryHelper;
import mawa.mobica.com.util.LogHelper;

@Path("/dictionaries")
public class DictionariesService {

	DictionaryDao dictionaryDao = DictionaryDao.getInstance();

	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public List<Dictionary> enumerate(
			@QueryParam("baseLanguage") String baseLanguage,
			@QueryParam("refLanguage") String refLanguage,
			@QueryParam("baseLanguageId") Long baseLanguageId,
			@QueryParam("refLanguageId") Long refLanguageId
			){

		try {
			if(baseLanguageId != null && refLanguageId != null){
				return DictionaryHelper.getInstance().toDto(dictionaryDao.enumerate(baseLanguageId, refLanguageId));
			}else if(baseLanguageId == null && refLanguageId == null){
				return DictionaryHelper.getInstance().toDto(dictionaryDao.enumerate(baseLanguage, refLanguage));
			}
			LogHelper.error(getClass(), "getDictionaries", "Bad request. Both IDs must be either set or null");
			throw new WebApplicationException(new Throwable("Bad request. Both IDs must be either set or null"), Status.BAD_REQUEST.getStatusCode());
		} catch (SQLException e) {
			LogHelper.error(getClass(), "getDictionaries", e.getLocalizedMessage());
			throw new WebApplicationException(Status.NOT_FOUND.getStatusCode());
		}
	}
}
