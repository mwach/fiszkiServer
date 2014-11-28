package mawa.mobica.com.dao;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.ObjectNotFoundException;
import org.hibernate.PropertyValueException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import mawa.mobica.com.dao.exception.InvalidObjectException;
import mawa.mobica.com.dao.exception.NotFoundException;
import mawa.mobica.com.model.Language;

public final class LanguageDao implements ILanguageDao {

	private LanguageDao() {
	}

	public static LanguageDao getInstance() {
		return new LanguageDao();
	}

	@Override
	public long create(Language object) throws SQLException {

		Session session = null;
		Transaction transaction = null;
		try {
			session = HibernateUtil.getSessionFactory().getCurrentSession();
			transaction = session.beginTransaction();
			session.save(object);
			transaction.commit();
			transaction = null;
		} catch (PropertyValueException exc) {
			throw new InvalidObjectException(String.format("Could not insert object: '%s' into database", object), exc);
		} catch (RuntimeException exc) {
			throw new SQLException("DAO create failed", exc);
		} finally {
			if (transaction != null) {
				try {
					transaction.rollback();
				} catch (HibernateException exc) {

				}
			}
		}
		return object.getId();
	}

	@Override
	public void delete(long objectId) throws SQLException {
		
		Session session = null;
		Transaction transaction = null;

		try {
			session = HibernateUtil.getSessionFactory().getCurrentSession();
			transaction = session.beginTransaction();
			Object persistentLanguage = session.load(Language.class, objectId);
			if (persistentLanguage != null) {
				session.delete(persistentLanguage);
			}
			transaction.commit();
			transaction = null;
		} catch (ObjectNotFoundException exc) {
			throw new NotFoundException(String.format("Object with given ID: '%d' not found", objectId), exc);
		} catch (RuntimeException exc) {
			throw new SQLException("DAO delete failed", exc);
		} finally {
			if (transaction != null) {
				try {
					transaction.rollback();
				} catch (HibernateException exc) {

				}
			}
		}
	}

	@Override
	public void update(Language object) throws SQLException {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		session.update(object);
		transaction.commit();
	}

	@Override
	public Language get(long objectId) throws SQLException {

		Session session = null;
		Transaction transaction = null;
		Language language = null;

		try {
			session = HibernateUtil.getSessionFactory().getCurrentSession();
			transaction = session.beginTransaction();
			Object persistentLanguage = session.load(Language.class, objectId);
			persistentLanguage.toString();
			language = (Language) persistentLanguage;
		} catch (ObjectNotFoundException exc) {
			throw new NotFoundException(String.format("Object with given ID: '%d' not found", objectId), exc);
		} catch (RuntimeException exc) {
			throw new SQLException("DAO get failed", exc);
		} finally {
			if (transaction != null) {
				try {
					transaction.commit();
				} catch (HibernateException exc) {

				}
			}
		}
		return language;
	}

	@Override
	public List<Language> enumerate() {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		@SuppressWarnings("unchecked")
		List<Language> languages = session.createCriteria(Language.class)
				.list();
		return languages;
	}

}
