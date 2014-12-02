package mawa.mobica.com.dao;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import mawa.mobica.com.model.Word;

public final class WordDao extends AbstractDao<Word> implements IWordDao{

	private WordDao(){
		super(Word.class);
	}

	public static WordDao getInstance(){
		return new WordDao();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Word> enumerate(Long dictionaryId) throws SQLException{

		Session session = null;
		Transaction transaction = null;
		List<Word> results = null;

		try {
			session = HibernateUtil.getSessionFactory().getCurrentSession();
			transaction = session.beginTransaction();

			Criteria criteria = session.createCriteria(Word.class, DB.WORD);

			if(dictionaryId != null){
				criteria.createAlias(String.format("%s.%s", DB.WORD, DB.DICTIONARY), DB.DICTIONARY);
				criteria.add(Restrictions.eq(String.format("%s.%s", DB.DICTIONARY, DB.DICTIONARY__ID), dictionaryId));
			}

			results = (List<Word>)criteria.list();

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
