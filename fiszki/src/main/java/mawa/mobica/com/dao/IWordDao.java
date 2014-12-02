package mawa.mobica.com.dao;

import java.sql.SQLException;
import java.util.List;

import mawa.mobica.com.model.Word;

public interface IWordDao extends IDao<Word>{

	public List<Word> enumerate(Long dictionaryId) throws SQLException;
}
