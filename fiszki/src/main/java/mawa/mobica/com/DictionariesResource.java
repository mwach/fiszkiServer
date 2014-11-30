package mawa.mobica.com;

import java.sql.SQLException;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import mawa.mobica.com.dao.DictionaryDao;
import mawa.mobica.com.model.Dictionary;

/**
 * REST operations for multiple dictionaries
 * 
 * @author mawa
 *
 */
@Path("/dictionaries")
public class DictionariesResource {

	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public List<Dictionary> getDictionaries(
			@QueryParam(value = "baseLanguage") final String baseLanguage,
			@QueryParam(value = "refLanguage") final String refLanguage) {
		try {
			return DictionaryDao.getInstance().getDictionaries(baseLanguage, refLanguage);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
