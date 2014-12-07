package mawa.mobica.com.rest;

import javax.ws.rs.Path;

import mawa.mobica.com.dao.IDao;
import mawa.mobica.com.dao.LanguageDao;
import mawa.mobica.com.model.Language;
import mawa.mobica.com.util.LanguageHelper;
import mawa.mobica.com.util.ResourceHelper;

@Path("/language")
public class LanguageService extends RestService<Language, mawa.mobica.com.rest.dto.Language>{

	@Override
	protected IDao<mawa.mobica.com.model.Language> getDao() {
		return LanguageDao.getInstance();
	}

	@Override
	protected ResourceHelper<Language, mawa.mobica.com.rest.dto.Language> getResourceHelper() {
		return LanguageHelper.getInstance();
	}

}
