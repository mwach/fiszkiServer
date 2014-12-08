package mawa.mobica.com.dao;

import static org.junit.Assert.*;

import java.sql.SQLException;
import java.util.UUID;

import mawa.mobica.com.dao.exception.InvalidObjectException;
import mawa.mobica.com.dao.exception.NotFoundException;
import mawa.mobica.com.model.Language;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class LanguageDaoTest {

	@Rule
	public ExpectedException exception = ExpectedException.none();

	private ILanguageDao languageDao = LanguageDao.getInstance();
	
	@Test
	public void testNotCompleteCreate() throws SQLException {
		
		exception.expect(InvalidObjectException.class);
		Language language = prepareLanguage("name");
		language.setDescription(null);
		languageDao.create(language);
	}
	@Test
	public void testCreateGet() throws SQLException {
		Language language = prepareLanguage("nameCG");
		languageDao.create(language);
		assertEquals(language, languageDao.get(language.getId()));
		assertEquals(language, languageDao.getByName(language.getName()));

	}

	@Test
	public void testCreateDelete() throws SQLException {
		Language language = prepareLanguage("nameCD");
		languageDao.create(language);
		languageDao.delete(language.getId());

		exception.expect(NotFoundException.class);
		languageDao.get(language.getId());
	}

	@Test
	public void testCreateUpdate() throws SQLException {
		Language language = prepareLanguage("nameCU");
		languageDao.create(language);
		assertEquals(language, languageDao.get(language.getId()));

		language.setName(UUID.randomUUID().toString());
		languageDao.update(language);
		assertEquals(language, languageDao.get(language.getId()));
	}

	@Test
	public void testEnumerate() throws SQLException {
		Language language = prepareLanguage(UUID.randomUUID().toString());
		languageDao.create(language);
		assertTrue(languageDao.enumerate().contains(language));
	}

	@Test
	public void testNonExistingGet() throws SQLException {

		exception.expect(NotFoundException.class);
		languageDao.get(-1);
	}

	@Test
	public void testNonExistingGetByName() throws SQLException {

		exception.expect(NotFoundException.class);
		languageDao.getByName("aa");
	}

	@Test
	public void testNonExistingDelete() throws SQLException {

		exception.expect(NotFoundException.class);
		languageDao.delete(-1);
	}

	@Test
	public void testNonExistingUpdate() throws SQLException {

		exception.expect(NotFoundException.class);
		Language testLanguage = prepareLanguage("TNEU");
		testLanguage.setId(-1L);
		languageDao.update(testLanguage);
	}

	private Language prepareLanguage(String name){
		
		Language language = new Language();
		language.setName(name);
		language.setDescription("description");
		return language;
	}
}
