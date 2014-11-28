package mawa.mobica.com.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import mawa.mobica.com.model.Dictionary;

public final class DictionaryDao {

	private DictionaryDao(){
	}

	public static DictionaryDao getInstance(){
		return new DictionaryDao();
	}

	public List<Dictionary> getDictionaries(String baseLanguage,
			String refLanguage) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();

		Criteria criteria = session.createCriteria(Dictionary.class);
		if(baseLanguage != null){
			criteria.add(Restrictions.eq(DB.DICTIONARY__BASE_LANG, baseLanguage));
		}
		if(refLanguage != null){
			criteria.add(Restrictions.eq(DB.DICTIONARY__REF_LANG, refLanguage));
		}
		@SuppressWarnings("unchecked")
		List<Dictionary> results = (List<Dictionary>)criteria.list();

		return results;
	}

}
