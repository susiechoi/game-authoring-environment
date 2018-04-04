package gameplayer;

import java.util.Map;
import java.util.Properties;

import authoring.frontend.PropertiesReader;
import authoring.frontend.exceptions.MissingPropertiesException;

public class PromptReader {
    private final String DEFAULT_LANGUAGE_FILEPATH = "languages/";
    private final String DEFAULT_PROMPT_FILENAME = "/Prompts.properties";
    private final String DEFAULT_LANGUAGE = "English";
    private String language = "English";
    private final PropertiesReader PROP_READER;
    private final ScreenManager SCREEN_MANAGER;
    private Properties languageProperty;
    private Map<String, String> languageMap;
    
    public PromptReader(String languageIn, ScreenManager screenManager) {
	PROP_READER = new PropertiesReader();
	SCREEN_MANAGER = screenManager;
	updateLanguage(languageIn);
    }
    
    public String resourceDisplayText(String key) {
	if(languageMap.containsKey(key)) {
	    return languageMap.get(key);
	}
	return ""; //TODO make this reasonable
    }
    
    public void updateLanguage(String languageIn) {
	language = languageIn;
	try {
	    languageProperty = PROP_READER.loadProperties(DEFAULT_LANGUAGE_FILEPATH + language +DEFAULT_PROMPT_FILENAME );
	} catch (MissingPropertiesException e) {
	    try {
		languageProperty = PROP_READER.loadProperties(DEFAULT_LANGUAGE_FILEPATH + DEFAULT_LANGUAGE +DEFAULT_PROMPT_FILENAME );
	    } catch (MissingPropertiesException e1) {
		SCREEN_MANAGER.loadErrorScreen();
	    }

	}
	languageMap = PROP_READER.read(languageProperty);
    }

}
