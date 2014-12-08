package mawa.mobica.com.rest;

import java.sql.SQLException;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response.Status;

import mawa.mobica.com.dao.LanguageDao;
import mawa.mobica.com.rest.dto.Language;
import mawa.mobica.com.util.LanguageHelper;
import mawa.mobica.com.util.LogHelper;

@Path("/languages")
public class LanguagesService {

	LanguageDao languageDao = LanguageDao.getInstance();

	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public List<Language> enumerate(){

		try {
			return LanguageHelper.getInstance().toDto(languageDao.enumerate());
		} catch (SQLException e) {
			LogHelper.error(getClass(), "getLanguages", e.getLocalizedMessage());
			throw new WebApplicationException(Status.NOT_FOUND.getStatusCode());
		}
	}
}
