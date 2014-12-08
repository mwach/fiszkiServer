package mawa.mobica.com.rest;

import static org.junit.Assert.*;

import java.sql.SQLException;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import mawa.mobica.com.dao.LanguageDao;
import mawa.mobica.com.rest.dto.Language;

import org.junit.BeforeClass;
import org.junit.Test;

//http://stackoverflow.com/questions/21413738/unit-testing-jersey-restful-services
public class LanguageServiceTest{

	private static LanguageService service = null;
	private static LanguageDao dao = null;

	/**
	 * Init objects, clean database
	 * @throws SQLException
	 */
	@BeforeClass
	public static void beforeClass() throws SQLException{
		service = new LanguageService();
		dao = LanguageDao.getInstance();
		
		for (mawa.mobica.com.model.Language language  : dao.enumerate()) {
			dao.delete(language.getId());
		}
	}

	@Test
	public void addGetLanguage(){
		Language baseLanguage = prepareLanguage();
		Response response = service.create(baseLanguage);
		assertEquals(Status.CREATED.getStatusCode(), response.getStatus());
		long languageId = Long.parseLong((String)response.getEntity());
		baseLanguage.setId(languageId);
		assertEquals(service.getResource(languageId), baseLanguage);
	}

	@Test
	public void addUpdateLanguage(){
		Language baseLanguage = prepareLanguage();
		Response createResponse = service.create(baseLanguage);
		assertEquals(Status.CREATED.getStatusCode(), createResponse.getStatus());
		long languageId = Long.parseLong((String)createResponse.getEntity());
		Language updatedLanguage = prepareLanguage();
		updatedLanguage.setId(languageId);

		service.updateResource(updatedLanguage, languageId);
		
		baseLanguage.setId(languageId);
		assertEquals(service.getResource(languageId), updatedLanguage);
	}

	@Test
	public void addDeleteLanguage(){
		Language baseLanguage = prepareLanguage();
		Response createResponse = service.create(baseLanguage);
		assertEquals(Status.CREATED.getStatusCode(), createResponse.getStatus());
		long languageId = Long.parseLong((String)createResponse.getEntity());

		service.deleteResource(languageId);

		try{
			service.getResource(languageId);
			throw new RuntimeException("delete does not work");
		}catch(WebApplicationException exc){
			assertEquals(Status.NOT_FOUND.getStatusCode(), exc.getResponse().getStatus());
		}
	}

	private Language prepareLanguage(){
		int counter = getCounter();
		Language language = new Language();
		language.setDescription("description_" + counter);
		language.setName("name_" + counter);
		return language;
	}

	private static int counter = 0;
	private synchronized int getCounter() {
		return ++counter;
	}
	
}