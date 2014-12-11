package mawa.mobica.com.rest;

import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;
import javax.xml.bind.JAXBContext;

import com.sun.jersey.api.json.JSONConfiguration;
import com.sun.jersey.api.json.JSONJAXBContext;

import mawa.mobica.com.rest.dto.Dictionary;
import mawa.mobica.com.rest.dto.Language;
import mawa.mobica.com.rest.dto.Word;

@Provider
public class JAXBContextResolver implements ContextResolver<JAXBContext> {

	private JAXBContext context;

	private Class<?>[] types = { Dictionary.class, Language.class, Word.class };

	public JAXBContextResolver() throws Exception {

		this.context = new JSONJAXBContext(JSONConfiguration.mapped()
				.arrays("dictionary").build(), types);
	}

	@Override
	public JAXBContext getContext(Class<?> objectType) {
		for (Class<?> type : types) {
			if (type == objectType) {
				return context;
			}
		}
		return null;
	}

}
