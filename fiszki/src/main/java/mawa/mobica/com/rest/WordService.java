package mawa.mobica.com.rest;

import javax.ws.rs.Path;

import mawa.mobica.com.dao.IDao;
import mawa.mobica.com.dao.WordDao;
import mawa.mobica.com.model.Word;
import mawa.mobica.com.util.ResourceHelper;
import mawa.mobica.com.util.WordHelper;

@Path("/word")
public class WordService extends RestService<Word, mawa.mobica.com.rest.dto.Word>{

	@Override
	protected IDao<Word> getDao() {
		return WordDao.getInstance();
	}

	@Override
	protected ResourceHelper<Word, mawa.mobica.com.rest.dto.Word> getResourceHelper() {
		return WordHelper.getInstance();
	}

}
