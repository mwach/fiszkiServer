package mawa.mobica.com.dao;

import static org.junit.Assert.*;

import java.sql.SQLException;
import java.util.UUID;

import mawa.mobica.com.dao.exception.InvalidObjectException;
import mawa.mobica.com.dao.exception.NotFoundException;
import mawa.mobica.com.model.Dictionary;
import mawa.mobica.com.model.Language;
import mawa.mobica.com.model.Word;

import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class WordDaoTest {

	@Rule
	public ExpectedException exception = ExpectedException.none();

	private IWordDao wordDao = WordDao.getInstance();

	private static Language baseLanguage = null;
	private static Language refLanguage = null;
	private static Dictionary dictionary = null;
	
	@BeforeClass
	public static void beforeClass() throws SQLException{
		baseLanguage = new Language();
		baseLanguage.setDescription("description");
		baseLanguage.setName("baseLanguageWord");

		refLanguage = new Language();
		refLanguage.setDescription("description");
		refLanguage.setName("refLanguageWord");

		LanguageDao.getInstance().create(baseLanguage);
		LanguageDao.getInstance().create(refLanguage);

		dictionary = new Dictionary();
		dictionary.setBaseLanguage(baseLanguage);
		dictionary.setRefLanguage(refLanguage);
		dictionary.setDescription("description");
		dictionary.setName("nameWord");
		dictionary.setUuid(UUID.randomUUID().toString());
		DictionaryDao.getInstance().create(dictionary);
	}

	@Test
	public void testNotCompleteCreate() throws SQLException {
		
		exception.expect(InvalidObjectException.class);
		Word word = new Word();
		word.setBaseWord("base");
		wordDao.create(word);
	}
	@Test
	public void testCreateGet() throws SQLException {
		Word word = prepareWord("nameCG");
		wordDao.create(word);
		assertEquals(word, wordDao.get(word.getId()));
	}

	@Test
	public void testCreateDelete() throws SQLException {
		Word word = prepareWord("nameCD");
		wordDao.create(word);
		wordDao.delete(word.getId());

		exception.expect(NotFoundException.class);
		wordDao.get(word.getId());
	}

	@Test
	public void testCreateUpdate() throws SQLException {
		Word word = prepareWord("nameCU");
		wordDao.create(word);
		assertEquals(word, wordDao.get(word.getId()));

		word.setBaseWord(UUID.randomUUID().toString());
		wordDao.update(word);
		assertEquals(word, wordDao.get(word.getId()));
	}

	@Test
	public void testEnumerate() throws SQLException {
		Word word = prepareWord(UUID.randomUUID().toString());
		wordDao.create(word);
		//should returns nothing
		assertFalse(wordDao.enumerate(-1L).contains(word));

		//should returns at least one dictionary
		assertTrue(wordDao.enumerate(dictionary.getId()).contains(word));

	}

	@Test
	public void testMissingDictionary() throws SQLException {

		exception.expect(InvalidObjectException.class);
		Word word = prepareWord("dictML");
		word.setDictionary(new Dictionary());
		wordDao.create(word);
	}

	@Test
	public void testRemoveDictionary() throws SQLException {

		Dictionary testDictionary = new Dictionary();
		testDictionary.setDescription("test");
		testDictionary.setName("nameRemove");
		testDictionary.setUuid(UUID.randomUUID().toString());
		testDictionary.setBaseLanguage(baseLanguage);
		testDictionary.setRefLanguage(refLanguage);
		DictionaryDao.getInstance().create(testDictionary);
		Word word = prepareWord("wordML");
		Word word2 = prepareWord("wordML2");
		word.setDictionary(testDictionary);
		word2.setDictionary(testDictionary);
		wordDao.create(word);
		wordDao.create(word2);
		DictionaryDao.getInstance().delete(testDictionary.getId());

		try{
			WordDao.getInstance().get(word.getId());
			throw new RuntimeException("DAO not thrown exception");
		}catch(Exception exc){
			assertTrue(exc instanceof NotFoundException);
		}
		exception.expect(NotFoundException.class);
		WordDao.getInstance().get(word2.getId());
	}

	@Test
	public void testRemoveLanguage() throws SQLException {

		Language testLanguage = new Language();
		testLanguage.setDescription("test");
		testLanguage.setName("nameRemove");
		LanguageDao.getInstance().create(testLanguage);

		Dictionary testDictionary = new Dictionary();
		testDictionary.setDescription("test");
		testDictionary.setName("nameRemove");
		testDictionary.setUuid(UUID.randomUUID().toString());
		testDictionary.setBaseLanguage(testLanguage);
		testDictionary.setRefLanguage(refLanguage);
		DictionaryDao.getInstance().create(testDictionary);
		Word word = prepareWord("wordML");
		Word word2 = prepareWord("wordML2");
		word.setDictionary(testDictionary);
		word2.setDictionary(testDictionary);
		wordDao.create(word);
		wordDao.create(word2);
		
		LanguageDao.getInstance().delete(testLanguage.getId());

		try{
			WordDao.getInstance().get(word.getId());
			throw new RuntimeException("DAO not thrown exception");
		}catch(Exception exc){
			assertTrue(exc instanceof NotFoundException);
		}
		exception.expect(NotFoundException.class);
		WordDao.getInstance().get(word2.getId());
	}

	@Test
	public void testNonExistingGet() throws SQLException {

		exception.expect(NotFoundException.class);
		wordDao.get(-1);
	}

	@Test
	public void testNonExistingDelete() throws SQLException {

		exception.expect(NotFoundException.class);
		wordDao.delete(-1);
	}

	private Word prepareWord(String name) {
		Word word = new Word();
		word.setDictionary(dictionary);
		word.setBaseWord(name);
		word.setRefWord("refWord");
		return word;
	}

}
