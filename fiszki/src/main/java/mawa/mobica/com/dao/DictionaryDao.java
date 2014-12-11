package mawa.mobica.com.dao;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Restrictions;

import mawa.mobica.com.model.Dictionary;

public final class DictionaryDao extends AbstractDao<Dictionary> implements IDictionaryDao{

	private DictionaryDao(){
		super(Dictionary.class);
	}

	public static DictionaryDao getInstance(){
		return new DictionaryDao();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Dictionary> enumerate(String baseLanguage,
			String refLanguage) throws SQLException{

		Session session = null;
		Transaction transaction = null;
		List<Dictionary> results = null;

		try {
			session = HibernateUtil.getSessionFactory().getCurrentSession();
			transaction = session.beginTransaction();

			Criteria criteria = session.createCriteria(Dictionary.class, DB.DICTIONARY);

			if(baseLanguage != null){
				criteria.createAlias(String.format("%s.%s", DB.DICTIONARY, DB.DICTIONARY__BASE_LANG), DB.DICTIONARY__BASE_LANG);
				criteria.add(Restrictions.eq(String.format("%s.%s", DB.DICTIONARY__BASE_LANG, DB.LANGUAGE__NAME), baseLanguage));
			}

			if(refLanguage != null){
				criteria.createAlias(String.format("%s.%s", DB.DICTIONARY, DB.DICTIONARY__REF_LANG), DB.DICTIONARY__REF_LANG);
				criteria.add(Restrictions.eq(String.format("%s.%s", DB.DICTIONARY__REF_LANG, DB.LANGUAGE__NAME), refLanguage));
			}
			criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);

			results = (List<Dictionary>)criteria.list();
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
		return results;
	}


	@SuppressWarnings("unchecked")
	public List<Dictionary> enumerate(Long baseLanguage,
			Long refLanguage) throws SQLException {
		Session session = null;
		Transaction transaction = null;
		List<Dictionary> results = null;

		try {
			session = HibernateUtil.getSessionFactory().getCurrentSession();
			transaction = session.beginTransaction();

			Criteria criteria = session.createCriteria(Dictionary.class, DB.DICTIONARY);

			if(baseLanguage != null){
				criteria.createAlias(String.format("%s.%s", DB.DICTIONARY, DB.DICTIONARY__BASE_LANG), DB.DICTIONARY__BASE_LANG);
				criteria.add(Restrictions.eq(String.format("%s.%s", DB.DICTIONARY__BASE_LANG, DB.LANGUAGE__ID), baseLanguage));
			}

			if(refLanguage != null){
				criteria.createAlias(String.format("%s.%s", DB.DICTIONARY, DB.DICTIONARY__REF_LANG), DB.DICTIONARY__REF_LANG);
				criteria.add(Restrictions.eq(String.format("%s.%s", DB.DICTIONARY__REF_LANG, DB.LANGUAGE__ID), refLanguage));
			}
			criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);

			results = (List<Dictionary>)criteria.list();

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
		return results;
	}

}
