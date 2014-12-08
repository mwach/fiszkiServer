package mawa.mobica.com.rest;

import static org.junit.Assert.*;

import java.sql.SQLException;
import java.util.UUID;

import javax.ws.rs.core.Response;

import mawa.mobica.com.dao.LanguageDao;
import mawa.mobica.com.rest.dto.Dictionary;
import mawa.mobica.com.rest.dto.Language;
import mawa.mobica.com.rest.dto.Word;

import org.junit.BeforeClass;
import org.junit.Test;

public class WordsServiceTest{

	private static Long dictionaryId = null;

	private static WordService wordService;
	private static WordsService service;


	/**
	 * Init objects, clean database
	 * @throws SQLException
	 */
	@BeforeClass
	public static void beforeClass() throws SQLException{

		DictionaryService dictionaryService = new DictionaryService();

		LanguageService languageService = new LanguageService();
		LanguageDao dao = LanguageDao.getInstance();
		
		for (mawa.mobica.com.model.Language language  : dao.enumerate()) {
			dao.delete(language.getId());
		}

		Language languageOne = prepareLanguage();
		Response response = languageService.create(languageOne);
		long baseLanguage = Long.parseLong((String)response.getEntity());

		Language languageTwo = prepareLanguage();
		response = languageService.create(languageTwo);
		long refLanguage = Long.parseLong((String)response.getEntity());
		
		Dictionary dictionary = prepareDictionary(baseLanguage, refLanguage);
		response = dictionaryService.create(dictionary);
		dictionaryId = Long.parseLong((String)response.getEntity());

		wordService = new WordService();
		service = new WordsService();
	}

	@Test
	public void enumerate(){
		Word wordOne = prepareWord();
		Response response = wordService.create(wordOne);
		wordOne.setId(Long.parseLong((String)response.getEntity()));

		assertEquals(1, service.enumerate(dictionaryId).size());
		assertEquals(wordOne, service.enumerate(dictionaryId).get(0));

		Word wordTwo = prepareWord();
		response = wordService.create(wordTwo);
		wordTwo.setId(Long.parseLong((String)response.getEntity()));

		assertEquals(2, service.enumerate(dictionaryId).size());
		assertTrue(service.enumerate(dictionaryId).contains(wordTwo));

	}


	private static Language prepareLanguage(){
		int counter = getCounter();
		Language language = new Language();
		language.setDescription("description_" + counter);
		language.setName("name_" + counter);
		return language;
	}

	private static Dictionary prepareDictionary(long baseLanguage, long refLanguage) {
		int counter = getCounter();
		Dictionary dictionary = new Dictionary();
		dictionary.setDescription("description_" + counter);
		dictionary.setName("name_" + counter);
		dictionary.setUuid(UUID.randomUUID().toString());
		dictionary.setBaseLanguageId(baseLanguage);
		dictionary.setRefLanguageId(refLanguage);
		return dictionary;
	}

	private Word prepareWord() {
		int counter = getCounter();
		Word word = new Word();
		word.setBaseWord("baseWord_" + counter);
		word.setRefWord("refWord_" + counter);
		word.setDictionary(dictionaryId);
		return word;
	}

	private static int counter = 0;
	private static synchronized int getCounter() {
		return ++counter;
	}
	
}