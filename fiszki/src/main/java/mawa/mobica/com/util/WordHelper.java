package mawa.mobica.com.util;

import java.util.ArrayList;
import java.util.List;

import mawa.mobica.com.model.Dictionary;
import mawa.mobica.com.model.Word;

public final class WordHelper extends ResourceHelper<Word, mawa.mobica.com.rest.dto.Word>{

	private static WordHelper wordHelper = null;
	public static synchronized WordHelper getInstance(){
		if(wordHelper == null){
			wordHelper = new WordHelper();
		}
		return wordHelper;
	}
	private WordHelper(){}

	public mawa.mobica.com.rest.dto.Word toDto(Word word){
		mawa.mobica.com.rest.dto.Word dto = new mawa.mobica.com.rest.dto.Word();
		dto.setId(word.getId());
		dto.setBaseWord(word.getBaseWord());
		dto.setRefWord(word.getRefWord());
		dto.setDictionary(word.getDictionary().getId());
		return dto;
	}

	public Word fromDto(
			mawa.mobica.com.rest.dto.Word dto) {
		Word word = new Word();
		word.setId(dto.getId());
		word.setBaseWord(dto.getBaseWord());
		word.setRefWord(dto.getRefWord());
		Dictionary dictionary = new Dictionary();
		dictionary.setId(dto.getDictionary());
		word.setDictionary(dictionary);

		return word;
	}

	public List<mawa.mobica.com.rest.dto.Word> toDto(
			List<Word> words) {
		List<mawa.mobica.com.rest.dto.Word> dtos = new ArrayList<mawa.mobica.com.rest.dto.Word>();
		for (Word word : words) {
			dtos.add(toDto(word));
		}
		return dtos;
	}
}
