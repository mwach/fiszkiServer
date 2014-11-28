package mawa.mobica.com.dao;

import java.util.List;

import mawa.mobica.com.model.Language;

public interface ILanguageDao extends IDao<Language>{

	public List<Language> enumerate();
}
