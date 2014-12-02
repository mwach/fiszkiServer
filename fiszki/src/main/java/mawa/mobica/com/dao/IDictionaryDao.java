package mawa.mobica.com.dao;

import java.sql.SQLException;
import java.util.List;

import mawa.mobica.com.model.Dictionary;

public interface IDictionaryDao extends IDao<Dictionary>{

	public List<Dictionary> enumerate(String baseLanguage,
			String refLanguage) throws SQLException;
}
