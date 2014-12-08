package mawa.mobica.com.dao;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import mawa.mobica.com.model.Language;

public final class LanguageDao extends AbstractDao<Language> implements ILanguageDao {

	private LanguageDao() {
		super(Language.class);
	}

	public static LanguageDao getInstance() {
		return new LanguageDao();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Language> enumerate() throws SQLException{

		Session session = null;
		Transaction transaction = null;
		List<Language> languages = null;

		try {
			session = HibernateUtil.getSessionFactory().getCurrentSession();
			transaction = session.beginTransaction();

			languages = session.createCriteria(Language.class)
					.list();
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
		return languages;

	}

	@Override
	public Language get(String name) throws SQLException {
		return null;
	}
}
