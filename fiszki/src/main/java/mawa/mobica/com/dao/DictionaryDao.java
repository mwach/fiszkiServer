package mawa.mobica.com.dao;

import java.util.UUID;

import org.hibernate.Session;

import mawa.mobica.com.model.Dictionary;

public class DictionaryDao {

	public Dictionary getDictionary(int i) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		Dictionary d = new Dictionary();
		d.setDescription("desc");
		d.setName("name");
		d.setUuid(UUID.randomUUID().toString());
		session.save(d);
		session.getTransaction().commit();
		// TODO Auto-generated method stub
		return d;
	}

}
