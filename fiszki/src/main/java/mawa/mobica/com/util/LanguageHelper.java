package mawa.mobica.com.util;

import java.util.ArrayList;
import java.util.List;

import mawa.mobica.com.model.Language;

public final class LanguageHelper {

	private LanguageHelper(){}

	public static mawa.mobica.com.rest.dto.Language toDto(Language language){
		mawa.mobica.com.rest.dto.Language dto = new mawa.mobica.com.rest.dto.Language();
		dto.setId(language.getId());
		dto.setName(language.getName());
		dto.setDescription(language.getDescription());
		return dto;
	}

	public static mawa.mobica.com.model.Language fromDto(
			mawa.mobica.com.rest.dto.Language dto) {
		Language language = new Language();
		language.setId(dto.getId());
		language.setName(dto.getName());
		language.setDescription(dto.getDescription());
		return language;
	}

	public static List<mawa.mobica.com.rest.dto.Language> toDto(
			List<mawa.mobica.com.model.Language> languages) {
		List<mawa.mobica.com.rest.dto.Language> dtos = new ArrayList<mawa.mobica.com.rest.dto.Language>();
		for (Language language : languages) {
			dtos.add(toDto(language));
		}
		return dtos;
	}
}
