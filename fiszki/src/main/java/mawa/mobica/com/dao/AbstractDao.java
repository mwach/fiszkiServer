package mawa.mobica.com.dao;

import java.sql.SQLException;

import mawa.mobica.com.dao.exception.InvalidObjectException;
import mawa.mobica.com.dao.exception.NotFoundException;

import org.hibernate.HibernateException;
import org.hibernate.ObjectNotFoundException;
import org.hibernate.PropertyValueException;
import org.hibernate.Session;
import org.hibernate.StaleStateException;
import org.hibernate.Transaction;

public class AbstractDao<T> implements IDao<T> {

	private Class<T> type;

	public AbstractDao(Class<T> type) {
		this.type = type;
	}

	@Override
	public T create(T object) throws SQLException {
		Session session = null;
		Transaction transaction = null;
		try {
			session = HibernateUtil.getSessionFactory().getCurrentSession();
			transaction = session.beginTransaction();
			session.save(object);
			transaction.commit();
			transaction = null;
		} catch (PropertyValueException exc) {
			throw new InvalidObjectException(String.format(
					"Could not insert object: '%s' into database", object), exc);
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
		return object;
	}

	@Override
	public void delete(long objectId) throws SQLException {
		Session session = null;
		Transaction transaction = null;

		try {
			session = HibernateUtil.getSessionFactory().getCurrentSession();
			transaction = session.beginTransaction();
			Object persistentLanguage = session.load(type, objectId);
			if (persistentLanguage != null) {
				session.delete(persistentLanguage);
			}
			transaction.commit();
			transaction = null;
		} catch (ObjectNotFoundException exc) {
			throw new NotFoundException(String.format(
					"Object with given ID: '%d' not found", objectId), exc);
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
	public void update(T object) throws SQLException {

		Session session = null;
		Transaction transaction = null;

		try {
			session = HibernateUtil.getSessionFactory().getCurrentSession();
			transaction = session.beginTransaction();
			session.update(object);
			transaction.commit();
			transaction = null;
		} catch (StaleStateException exc) {
			throw new NotFoundException(String.format(
					"Object '%s' not fount in the DB", object), exc);

		} catch (RuntimeException exc) {
			throw new SQLException("DAO update failed", exc);
		} finally {
			if (transaction != null) {
				try {
					transaction.rollback();
				} catch (RuntimeException exc2) {

				}
			}

		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public T get(long objectId) throws SQLException {

		Session session = null;
		Transaction transaction = null;
		T object = null;

		try {
			session = HibernateUtil.getSessionFactory().getCurrentSession();
			transaction = session.beginTransaction();
			Object persistentLanguage = session.get(type, objectId);
			if(persistentLanguage == null){
				throw new NotFoundException(String.format(
						"Object with given ID: '%d' not found", objectId));
			}
			persistentLanguage.toString();
			object = (T) persistentLanguage;
			transaction.commit();
		} catch (RuntimeException exc) {
			throw new SQLException("DAO get failed", exc);
		} finally {
			if (transaction != null) {
				try {
					transaction.rollback();
				} catch (HibernateException exc) {

				}
			}
		}
		return object;
	}

}
