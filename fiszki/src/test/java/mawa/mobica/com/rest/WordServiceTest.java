package mawa.mobica.com.rest;

import static org.junit.Assert.*;

import java.sql.SQLException;
import java.util.UUID;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import mawa.mobica.com.dao.LanguageDao;
import mawa.mobica.com.rest.dto.Dictionary;
import mawa.mobica.com.rest.dto.Language;
import mawa.mobica.com.rest.dto.Word;

import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

//http://stackoverflow.com/questions/21413738/unit-testing-jersey-restful-services
public class WordServiceTest{

	@Rule
	public ExpectedException expectedException = ExpectedException.none();

	private static WordService service = null;
	private static Language baseLanguage = null;
	private static Language refLanguage = null;
	private static Dictionary dictionary = null;
	/**
	 * Init objects, clean database
	 * @throws SQLException
	 */
	@BeforeClass
	public static void beforeClass() throws SQLException{

		service = new WordService();
		LanguageService languageService = new LanguageService();
		DictionaryService dictionaryService = new DictionaryService();

		LanguageDao languageDao = LanguageDao.getInstance();
		
		for (mawa.mobica.com.model.Language language  : languageDao.enumerate()) {
			languageDao.delete(language.getId());
		}
		baseLanguage = prepareLanguage();
		baseLanguage.setId(Long.parseLong(
				(String)languageService.create(baseLanguage).getEntity()));
		refLanguage = prepareLanguage();
		refLanguage.setId(Long.parseLong(
				(String)languageService.create(refLanguage).getEntity()));
		
		dictionary = prepareDictionary();
		dictionary.setId(Long.parseLong(
				(String)dictionaryService.create(dictionary).getEntity()));
	}

	@Test
	public void addGetWord(){
		Word word = prepareWord();
		Response response = service.create(word);
		assertEquals(Status.CREATED.getStatusCode(), response.getStatus());
		long wordId = Long.parseLong((String)response.getEntity());
		word.setId(wordId);
		assertEquals(service.getResource(wordId), word);
	}

	@Test
	public void addInvalidWord(){
		expectedException.expect(WebApplicationException.class);
		Word word = prepareWord();
		word.setDictionary(null);
		service.create(word);
	}

	@Test
	public void addUpdateWord(){
		Word dictionary = prepareWord();
		Response response = service.create(dictionary);
		assertEquals(Status.CREATED.getStatusCode(), response.getStatus());
		long wordId = Long.parseLong((String)response.getEntity());
		Word updatedWord = prepareWord();
		updatedWord.setId(wordId);

		service.updateResource(updatedWord, wordId);
		assertEquals(service.getResource(wordId), updatedWord);
	}

	@Test
	public void addDeleteWord(){
		Word dictionary = prepareWord();
		Response response = service.create(dictionary);
		assertEquals(Status.CREATED.getStatusCode(), response.getStatus());
		long wordId = Long.parseLong((String)response.getEntity());
		service.deleteResource(wordId);

		try{
			service.getResource(wordId);
			throw new RuntimeException("delete does not work");
		}catch(WebApplicationException exc){
			assertEquals(Status.NOT_FOUND.getStatusCode(), exc.getResponse().getStatus());
		}
	}

	private static Language prepareLanguage(){
		int counter = getCounter();
		Language language = new Language();
		language.setDescription("description_" + counter);
		language.setName("name_" + counter);
		return language;
	}

	private static Dictionary prepareDictionary() {
		int counter = getCounter();
		Dictionary dictionary = new Dictionary();
		dictionary.setDescription("description_" + counter);
		dictionary.setName("name_" + counter);
		dictionary.setUuid(UUID.randomUUID().toString());
		dictionary.setBaseLanguageId(baseLanguage.getId());
		dictionary.setRefLanguageId(refLanguage.getId());
		return dictionary;
	}

	private Word prepareWord() {
		int counter = getCounter();
		Word word = new Word();
		word.setBaseWord("baseWord_" + counter);
		word.setRefWord("refWord_" + counter);
		word.setDictionary(dictionary.getId());
		return word;
	}

	private static int counter = 0;
	private static synchronized int getCounter() {
		return ++counter;
	}
	
}