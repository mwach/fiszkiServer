package mawa.mobica.com.dao;

import java.sql.SQLException;
import java.util.List;

import mawa.mobica.com.model.Language;

public interface ILanguageDao extends IDao<Language>{

	public List<Language> enumerate() throws SQLException;
}
