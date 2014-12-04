package mawa.mobica.com.rest;

import javax.ws.rs.Path;

import mawa.mobica.com.dao.DictionaryDao;
import mawa.mobica.com.dao.IDao;
import mawa.mobica.com.model.Dictionary;
import mawa.mobica.com.util.DictionaryHelper;
import mawa.mobica.com.util.ResourceHelper;

@Path("/dictionary")
public class DictionaryResource extends RestResource<Dictionary, mawa.mobica.com.rest.dto.Dictionary>{

	@Override
	protected IDao<Dictionary> getDao() {
		return DictionaryDao.getInstance();
	}

	@Override
	protected ResourceHelper<Dictionary, mawa.mobica.com.rest.dto.Dictionary> getResourceHelper() {
		return DictionaryHelper.getInstance();
	}

}
