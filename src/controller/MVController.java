package controller;

import authoring.frontend.exceptions.MissingPropertiesException;
import frontend.StageManager;

public interface MVController {
    public void playControllerDemo(StageManager manager, String instructions) throws MissingPropertiesException;
}
