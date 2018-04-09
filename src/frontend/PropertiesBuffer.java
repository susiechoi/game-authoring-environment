package frontend;

import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;

import authoring.frontend.exceptions.MissingPropertiesException;

public abstract class PropertiesBuffer {
    private final String DEFAULT_PROMPT_KEY = "PromptDNE";
    private final String DEFAULT_LANGUAGE = "English";
    public static final String DEFAULT_NO_FILE_MESSAGE = "No language files detected!";
    private String language = "English";
    private final PropertiesReader PROP_READER;
    private final View SCREEN_MANAGER;
    private Properties languageProperty;
    private Properties errorProperty;
    private Map<String, String> languageMap;
    private Map<String, String> errorMap;


    public PropertiesBuffer(String languageIn, View screenManager) {
	PROP_READER = new PropertiesReader();
	SCREEN_MANAGER = screenManager;
	languageMap = new TreeMap<String,String>();
	errorMap = new TreeMap<String,String>();
	languageProperty = new Properties();
	updateLanguage(languageIn);
    }

    public String resourceDisplayText(String key) {
	if(languageMap.containsKey(key)) {
	    return languageMap.get(key);
	}
	else if(languageMap.containsKey(DEFAULT_PROMPT_KEY)) {
	    return languageMap.get(DEFAULT_PROMPT_KEY);
	}
	return ""; //TODO make this reasonable
    }

    public void updateLanguage(String languageIn) {
	language = languageIn;
	try {
	    languageProperty = PROP_READER.loadProperties(makeFilepath(language));
	} catch (MissingPropertiesException e) {
	    try {
		languageProperty = PROP_READER.loadProperties(makeFilepath(DEFAULT_LANGUAGE));
	    } catch (MissingPropertiesException e1) {
		SCREEN_MANAGER.loadErrorScreenToStage(DEFAULT_NO_FILE_MESSAGE);
	    }
	}
	languageMap = PROP_READER.read(languageProperty);
    }
    protected abstract String makeFilepath(String language);

}
