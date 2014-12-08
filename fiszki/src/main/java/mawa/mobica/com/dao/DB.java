package mawa.mobica.com.dao;

public final class DB {

	private DB(){}

	private static final String ID = "id";
	private static final String NAME = "name";

	public static final String LANGUAGE = "language";
	public static final String LANGUAGE__ID = ID;
	public static final String LANGUAGE__NAME = NAME;
	public static final String LANGUAGE__DESC = "description";

	public static final String DICTIONARY = "dictionary";
	public static final String DICTIONARY__ID = ID;
	public static final String DICTIONARY__NAME = NAME;
	public static final String DICTIONARY__UUID = "uuid";
	public static final String DICTIONARY__DESC = "description";
	public static final String DICTIONARY__BASE_LANG = "baseLanguage";
	public static final String DICTIONARY__REF_LANG = "refLanguage";

	public static final String WORD = "word";
	public static final String WORD__ID = ID;
	public static final String WORD__DICTIONARY = "dictionary";
	public static final String WORD__NAME = NAME;
	public static final String WORD__REF_NAME = "refName";

}
