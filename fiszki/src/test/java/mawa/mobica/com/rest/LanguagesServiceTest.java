package mawa.mobica.com.rest;

import static org.junit.Assert.*;

import java.sql.SQLException;

import javax.ws.rs.core.Response;

import mawa.mobica.com.dao.LanguageDao;
import mawa.mobica.com.rest.dto.Language;

import org.junit.BeforeClass;
import org.junit.Test;

public class LanguagesServiceTest{

	private static LanguageService languageService = null;
	private static LanguagesService service = null;
	private static LanguageDao dao = null;

	/**
	 * Init objects, clean database
	 * @throws SQLException
	 */
	@BeforeClass
	public static void beforeClass() throws SQLException{
		service = new LanguagesService();
		languageService = new LanguageService();
		dao = LanguageDao.getInstance();
		
		for (mawa.mobica.com.model.Language language  : dao.enumerate()) {
			dao.delete(language.getId());
		}
	}

	@Test
	public void enumerate(){
		Language languageOne = prepareLanguage();
		Response response = languageService.create(languageOne);
		languageOne.setId(Long.parseLong((String)response.getEntity()));

		assertEquals(1, service.enumerate().size());
		assertEquals(languageOne, service.enumerate().get(0));

		Language languageTwo = prepareLanguage();
		response = languageService.create(languageTwo);
		languageTwo.setId(Long.parseLong((String)response.getEntity()));

		assertEquals(2, service.enumerate().size());
		assertTrue(service.enumerate().contains(languageTwo));
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