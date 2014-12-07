package mawa.mobica.com.util;

import java.util.ArrayList;
import java.util.List;

import mawa.mobica.com.model.Dictionary;
import mawa.mobica.com.model.Language;

public final class DictionaryHelper extends ResourceHelper<Dictionary, mawa.mobica.com.rest.dto.Dictionary>{

	private static DictionaryHelper dictionaryHelper = null;
	public static synchronized DictionaryHelper getInstance(){
		if(dictionaryHelper == null){
			dictionaryHelper = new DictionaryHelper();
		}
		return dictionaryHelper;
	}
	private DictionaryHelper(){}

	public mawa.mobica.com.rest.dto.Dictionary toDto(Dictionary dictionary){
		mawa.mobica.com.rest.dto.Dictionary dto = new mawa.mobica.com.rest.dto.Dictionary();
		dto.setId(dictionary.getId());
		dto.setName(dictionary.getName());
		dto.setDescription(dictionary.getDescription());
		dto.setUuid(dictionary.getUuid());
		dto.setBaseLanguageId(dictionary.getBaseLanguage().getId());
		dto.setRefLanguageId(dictionary.getRefLanguage().getId());
		dto.setUuid(dictionary.getUuid());
		return dto;
	}

	public Dictionary fromDto(
			mawa.mobica.com.rest.dto.Dictionary dto) {
		Dictionary dictionary = new Dictionary();
		dictionary.setId(dto.getId());
		dictionary.setName(dto.getName());
		dictionary.setDescription(dto.getDescription());
		dictionary.setUuid(dto.getUuid());
		
		Language baseLanguage = new Language();
		baseLanguage.setId(dto.getBaseLanguageId());
		dictionary.setBaseLanguage(baseLanguage);

		Language refLanguage = new Language();
		refLanguage.setId(dto.getRefLanguageId());
		dictionary.setRefLanguage(refLanguage);

		return dictionary;
	}

	public List<mawa.mobica.com.rest.dto.Dictionary> toDto(
			List<Dictionary> dictionaries) {
		List<mawa.mobica.com.rest.dto.Dictionary> dtos = new ArrayList<mawa.mobica.com.rest.dto.Dictionary>();
		for (Dictionary dictionary : dictionaries) {
			dtos.add(toDto(dictionary));
		}
		return dtos;
	}
}
