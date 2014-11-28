package mawa.mobica.com.dao;

import static org.junit.Assert.*;

import java.sql.SQLException;

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
		Language language = new Language();
		language.setDescription(null);
		language.setName("name");
		languageDao.create(language);
	}
	@Test
	public void testCreateGet() throws SQLException {
		Language language = new Language();
		language.setDescription("description");
		language.setName("name");
		languageDao.create(language);
		assertEquals(language, languageDao.get(language.getId()));
	}

	@Test
	public void testCreateDelete() throws SQLException {
		Language language = new Language();
		language.setDescription("description");
		language.setName("name");
		languageDao.create(language);
		languageDao.delete(language.getId());

		exception.expect(NotFoundException.class);
		languageDao.get(language.getId());
	}

	@Test
	public void testNonExistingGet() throws SQLException {

		exception.expect(NotFoundException.class);
		languageDao.get(-1);
	}

	@Test
	public void testNonExistingDelete() throws SQLException {

		exception.expect(NotFoundException.class);
		languageDao.delete(-1);
	}

}
