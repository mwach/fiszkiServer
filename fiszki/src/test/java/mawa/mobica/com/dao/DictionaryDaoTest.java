package mawa.mobica.com.dao;

import static org.junit.Assert.*;

import java.sql.SQLException;
import java.util.UUID;

import mawa.mobica.com.dao.exception.InvalidObjectException;
import mawa.mobica.com.dao.exception.NotFoundException;
import mawa.mobica.com.model.Dictionary;
import mawa.mobica.com.model.Language;

import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class DictionaryDaoTest {

	@Rule
	public ExpectedException exception = ExpectedException.none();

	private IDictionaryDao dictionaryDao = DictionaryDao.getInstance();

	private static Language baseLanguage = null;
	private static Language refLanguage = null;
	
	@BeforeClass
	public static void beforeClass() throws SQLException{
		baseLanguage = new Language();
		baseLanguage.setDescription("description");
		baseLanguage.setName("baseLanguageDictionary");

		refLanguage = new Language();
		refLanguage.setDescription("description");
		refLanguage.setName("refLanguageDictionary");

		LanguageDao.getInstance().create(baseLanguage);
		LanguageDao.getInstance().create(refLanguage);
	}

	@Test
	public void testNotCompleteCreate() throws SQLException {
		
		exception.expect(InvalidObjectException.class);
		Dictionary dictionary = new Dictionary();
		dictionary.setDescription(null);
		dictionary.setName("nameNCC");
		dictionaryDao.create(dictionary);
	}
	@Test
	public void testCreateGet() throws SQLException {
		Dictionary dictionary = prepareDictionary("nameCG");
		dictionaryDao.create(dictionary);
		assertEquals(dictionary, dictionaryDao.get(dictionary.getId()));
	}

	@Test
	public void testCreateDelete() throws SQLException {
		Dictionary dictionary = prepareDictionary("nameCD");
		dictionaryDao.create(dictionary);
		dictionaryDao.delete(dictionary.getId());

		exception.expect(NotFoundException.class);
		dictionaryDao.get(dictionary.getId());
	}

	@Test
	public void testCreateUpdate() throws SQLException {
		Dictionary dictionary = prepareDictionary("nameCU");
		dictionaryDao.create(dictionary);
		assertEquals(dictionary, dictionaryDao.get(dictionary.getId()));

		dictionary.setName(UUID.randomUUID().toString());
		dictionaryDao.update(dictionary);
		assertEquals(dictionary, dictionaryDao.get(dictionary.getId()));
	}

	@Test
	public void testEnumerate() throws SQLException {
		Dictionary dictionary = prepareDictionary(UUID.randomUUID().toString());
		dictionaryDao.create(dictionary);
		//should returns nothing
		assertFalse(dictionaryDao.enumerate("abc", "xyz").contains(dictionary));

		//should returns at least one dictionary
		assertTrue(dictionaryDao.enumerate(baseLanguage.getName(), refLanguage.getName()).contains(dictionary));

	}

	@Test
	public void testMissingLanguate() throws SQLException {

		exception.expect(InvalidObjectException.class);
		Dictionary dictionary = prepareDictionary("dictML");
		dictionary.setBaseLanguage(new Language());
		dictionaryDao.create(dictionary);
	}

	@Test
	public void testRemoveLanguage() throws SQLException {

		Language testLanguage = new Language();
		testLanguage.setDescription("test");
		testLanguage.setName("nameRemove");
		LanguageDao.getInstance().create(testLanguage);
		Dictionary dictionary = prepareDictionary("dictML");
		Dictionary dictionary2 = prepareDictionary("dictML2");
		dictionary.setBaseLanguage(testLanguage);
		dictionary2.setRefLanguage(testLanguage);
		dictionaryDao.create(dictionary);
		dictionaryDao.create(dictionary2);
		LanguageDao.getInstance().delete(testLanguage.getId());

		try{
			DictionaryDao.getInstance().get(dictionary.getId());
			throw new RuntimeException("DAO not thrown exception");
		}catch(Exception exc){
			assertTrue(exc instanceof NotFoundException);
		}
		exception.expect(NotFoundException.class);
		DictionaryDao.getInstance().get(dictionary2.getId());
	}

	@Test
	public void testNonExistingGet() throws SQLException {

		exception.expect(NotFoundException.class);
		dictionaryDao.get(-1);
	}

	@Test
	public void testNonExistingDelete() throws SQLException {

		exception.expect(NotFoundException.class);
		dictionaryDao.delete(-1);
	}

	private Dictionary prepareDictionary(String name) {
		Dictionary dictionary = new Dictionary();
		dictionary.setDescription("description");
		dictionary.setName(name);
		dictionary.setUuid(name);
		dictionary.setBaseLanguage(baseLanguage);
		dictionary.setRefLanguage(refLanguage);
		return dictionary;
	}

}
