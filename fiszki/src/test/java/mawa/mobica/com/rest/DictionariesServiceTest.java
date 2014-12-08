package mawa.mobica.com.rest;

import static org.junit.Assert.*;

import java.sql.SQLException;
import java.util.UUID;

import javax.ws.rs.core.Response;

import mawa.mobica.com.dao.LanguageDao;
import mawa.mobica.com.rest.dto.Dictionary;
import mawa.mobica.com.rest.dto.Language;

import org.junit.BeforeClass;
import org.junit.Test;

public class DictionariesServiceTest{

	private static DictionariesService service = null;
	private static DictionaryService dictionaryService = null;

	private static LanguageDao dao = null;

	private static Long baseLanguage = null;
	private static Long refLanguage = null;
	private static String baseLanguageName = null;
	private static String refLanguageName = null;

	/**
	 * Init objects, clean database
	 * @throws SQLException
	 */
	@BeforeClass
	public static void beforeClass() throws SQLException{

		service = new DictionariesService();
		dictionaryService = new DictionaryService();

		LanguageService languageService = new LanguageService();
		dao = LanguageDao.getInstance();
		
		for (mawa.mobica.com.model.Language language  : dao.enumerate()) {
			dao.delete(language.getId());
		}

		Language languageOne = prepareLanguage();
		Response response = languageService.create(languageOne);
		baseLanguage = Long.parseLong((String)response.getEntity());
		baseLanguageName = languageOne.getName();

		Language languageTwo = prepareLanguage();
		response = languageService.create(languageTwo);
		refLanguage = Long.parseLong((String)response.getEntity());
		refLanguageName = languageTwo.getName();
	}

	@Test
	public void enumerate(){
		Dictionary dictionaryOne = prepareDictionary();
		Response response = dictionaryService.create(dictionaryOne);
		dictionaryOne.setId(Long.parseLong((String)response.getEntity()));

		//by name
		assertEquals(1, service.enumerate(baseLanguageName, refLanguageName, null, null).size());
		assertEquals(dictionaryOne, service.enumerate(baseLanguageName, refLanguageName, null, null).get(0));

		//by id
		assertEquals(1, service.enumerate(null, null, baseLanguage, refLanguage).size());
		assertEquals(dictionaryOne, service.enumerate(null, null, baseLanguage, refLanguage).get(0));

		Dictionary dictionaryTwo = prepareDictionary();
		response = dictionaryService.create(dictionaryTwo);
		dictionaryTwo.setId(Long.parseLong((String)response.getEntity()));

		assertEquals(2, service.enumerate(baseLanguageName, refLanguageName, null, null).size());
		assertTrue(service.enumerate(baseLanguageName, refLanguageName, null, null).contains(dictionaryTwo));

		//by id
		assertEquals(2, service.enumerate(null, null, baseLanguage, refLanguage).size());
		assertTrue(service.enumerate(null, null, baseLanguage, refLanguage).contains(dictionaryTwo));

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
		dictionary.setBaseLanguageId(baseLanguage);
		dictionary.setRefLanguageId(refLanguage);
		return dictionary;
	}

	private static int counter = 0;
	private static synchronized int getCounter() {
		return ++counter;
	}
	
}