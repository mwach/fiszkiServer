package mawa.mobica.com.rest;

import static org.junit.Assert.*;

import java.sql.SQLException;
import java.util.UUID;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.xml.ws.http.HTTPException;

import mawa.mobica.com.dao.LanguageDao;
import mawa.mobica.com.rest.dto.Dictionary;
import mawa.mobica.com.rest.dto.Language;

import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

//http://stackoverflow.com/questions/21413738/unit-testing-jersey-restful-services
public class DictionaryServiceTest{

	@Rule
	public ExpectedException expectedException = ExpectedException.none();

	private static DictionaryService service = null;
	private static Language baseLanguage = null;
	private static Language refLanguage = null;

	/**
	 * Init objects, clean database
	 * @throws SQLException
	 */
	@BeforeClass
	public static void beforeClass() throws SQLException{

		service = new DictionaryService();

		LanguageDao languageDao = LanguageDao.getInstance();
		for (mawa.mobica.com.model.Language language  : languageDao.enumerate()) {
			languageDao.delete(language.getId());
		}

		LanguageService languageService = new LanguageService();
		baseLanguage = prepareLanguage();
		baseLanguage.setId(Long.parseLong(
				(String)languageService.create(baseLanguage).getEntity()));
		refLanguage = prepareLanguage();
		refLanguage.setId(Long.parseLong(
				(String)languageService.create(refLanguage).getEntity()));
	}

	@Test
	public void addGetDictionary(){
		Dictionary dictionary = prepareDictionary();
		Response response = service.create(dictionary);
		assertEquals(Status.CREATED.getStatusCode(), response.getStatus());
		long dictionaryId = Long.parseLong((String)response.getEntity());
		dictionary.setId(dictionaryId);
		assertEquals(service.getResource(dictionaryId), dictionary);
	}

	@Test
	public void addInvalidDictionary(){
		Dictionary dictionary = prepareDictionary();
		dictionary.setBaseLanguageId(-1L);
		
		expectedException.expect(HTTPException.class);
		service.create(dictionary);
	}

	@Test
	public void addUpdateLanguage(){
		Dictionary dictionary = prepareDictionary();
		Response response = service.create(dictionary);
		assertEquals(Status.CREATED.getStatusCode(), response.getStatus());
		long dictionaryId = Long.parseLong((String)response.getEntity());
		Dictionary updatedDictionary = prepareDictionary();
		updatedDictionary.setId(dictionaryId);

		Response updateResponse = service.updateResource(updatedDictionary, dictionaryId);
		assertEquals(Status.OK.getStatusCode(), updateResponse.getStatus());
		assertEquals(service.getResource(dictionaryId), updatedDictionary);
	}

	@Test
	public void addDeleteLanguage(){
		Dictionary dictionary = prepareDictionary();
		Response response = service.create(dictionary);
		assertEquals(Status.CREATED.getStatusCode(), response.getStatus());
		long dictionaryId = Long.parseLong((String)response.getEntity());

		Response deleteResponse = service.deleteResource(dictionaryId);
		assertEquals(Status.OK.getStatusCode(), deleteResponse.getStatus());

		try{
			service.getResource(dictionaryId);
			throw new RuntimeException("delete does not work");
		}catch(HTTPException exc){
			assertEquals(Status.NOT_FOUND.getStatusCode(), exc.getStatusCode());
		}
	}

	private static Language prepareLanguage(){
		int counter = getCounter();
		Language language = new Language();
		language.setDescription("description_" + counter);
		language.setName("name_" + counter);
		return language;
	}

	private Dictionary prepareDictionary() {
		int counter = getCounter();
		Dictionary dictionary = new Dictionary();
		dictionary.setDescription("description_" + counter);
		dictionary.setName("name_" + counter);
		dictionary.setUuid(UUID.randomUUID().toString());
		dictionary.setBaseLanguageId(baseLanguage.getId());
		dictionary.setRefLanguageId(refLanguage.getId());
		return dictionary;
	}

	private static int counter = 0;
	private static synchronized int getCounter() {
		return ++counter;
	}
	
}