package br.com.viniciussls.thecatfiles.utils;

import javafx.stage.Stage;

public class SingleInstance {

    private static SingleInstance singleInstance = null;
    private Stage currentStage;

    public static SingleInstance getInstance() {
        if (singleInstance == null) {
            singleInstance = new SingleInstance();
        }
        return singleInstance;
    }

    public Stage getCurrentStage() {
        return currentStage;
    }

    public void setCurrentStage(Stage currentStage) {
        this.currentStage = currentStage;
    }

}
