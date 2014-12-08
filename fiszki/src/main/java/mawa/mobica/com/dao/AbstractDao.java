package mawa.mobica.com.dao;

import java.sql.SQLException;

import mawa.mobica.com.dao.exception.InvalidObjectException;
import mawa.mobica.com.dao.exception.NotFoundException;
import mawa.mobica.com.util.LogHelper;
import mawa.mobica.com.util.StringHelper;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.ObjectNotFoundException;
import org.hibernate.PropertyValueException;
import org.hibernate.Session;
import org.hibernate.StaleStateException;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

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
			LogHelper.info(AbstractDao.class, "create", StringHelper.toString(object));
			session = HibernateUtil.getSessionFactory().getCurrentSession();
			transaction = session.beginTransaction();
			session.save(object);
			transaction.commit();
			transaction = null;
			LogHelper.info(AbstractDao.class, "create", StringHelper.toString(object));
		} catch (PropertyValueException exc) {
			LogHelper.error(AbstractDao.class, "create", StringHelper.toString(object), exc);
			throw new InvalidObjectException(String.format(
					"Could not insert object: '%s' into database", object), exc);
		} catch (RuntimeException exc) {
			LogHelper.error(AbstractDao.class, "create", StringHelper.toString(object), exc);
			throw new SQLException("DAO create failed", exc);
		} finally {
			if (transaction != null) {
				try {
					transaction.rollback();
				} catch (HibernateException exc) {
					LogHelper.error(AbstractDao.class, "create", "transactionRollback failed", exc);
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
			LogHelper.info(AbstractDao.class, "delete", StringHelper.toString(objectId));
			session = HibernateUtil.getSessionFactory().getCurrentSession();
			transaction = session.beginTransaction();
			Object persistentLanguage = session.get(type, objectId);
			if (persistentLanguage != null) {
				session.delete(persistentLanguage);
			}else{
				throw new NotFoundException(String.format(
						"Object with given ID: '%d' not found", objectId));				
			}
			transaction.commit();
			transaction = null;
			LogHelper.info(AbstractDao.class, "delete", "object successfully deleted");
		} catch (ObjectNotFoundException exc) {
			LogHelper.error(AbstractDao.class, "delete", String.format("object with given ID %d not found", objectId), exc);
			throw new NotFoundException(String.format(
					"Object with given ID: '%d' not found", objectId), exc);
		} catch (RuntimeException exc) {
			LogHelper.error(AbstractDao.class, "create", "DAO delete failed", exc);
			throw new SQLException("DAO delete failed", exc);
		} finally {
			if (transaction != null) {
				try {
					transaction.rollback();
				} catch (HibernateException exc) {
					LogHelper.error(AbstractDao.class, "create", "transactionRollback failed", exc);
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
			transaction = null;
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

	@SuppressWarnings("unchecked")
	@Override
	public T getByName(String objectName) throws SQLException {

		Session session = null;
		Transaction transaction = null;
		T object = null;

		try {
			session = HibernateUtil.getSessionFactory().getCurrentSession();
			transaction = session.beginTransaction();
			
			Criteria criteria = session.createCriteria(type);
			criteria.add(Restrictions.eq(DB.DICTIONARY__NAME, objectName));

			Object persistentObject = criteria.uniqueResult();
			if(persistentObject == null){
				throw new NotFoundException(String.format(
						"Object with given name: '%s' not found", objectName));
			}
			persistentObject.toString();
			object = (T) persistentObject;
			transaction.commit();
			transaction = null;
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
