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

import mawa.mobica.com.dao.WordDao;
import mawa.mobica.com.rest.dto.Word;
import mawa.mobica.com.util.LogHelper;
import mawa.mobica.com.util.WordHelper;

@Path("/words")
public class WordsService {

	WordDao wordDao = WordDao.getInstance();

	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public List<Word> enumerate(
			@QueryParam("dictionaryId") Long dictionaryId
			){

		if(dictionaryId == null){
			LogHelper.error(getClass(), "getWords", "Missing parameter 'dictionaryId'");
			throw new WebApplicationException(new Throwable("Missing parameter 'dictionaryId'"), Status.BAD_REQUEST.getStatusCode());
		}
		try {
			return WordHelper.getInstance().toDto(wordDao.enumerate(dictionaryId));
		} catch (SQLException e) {
			LogHelper.error(getClass(), "getWords", e.getLocalizedMessage());
			throw new WebApplicationException(Status.NOT_FOUND.getStatusCode());
		}
	}
}
