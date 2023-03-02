package br.com.viniciussls.thecatfiles;

import br.com.viniciussls.thecatfiles.utils.SingleInstance;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class RunApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(RunApplication.class.getResource("views/home.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.show();
        SingleInstance.getInstance().setCurrentStage(stage);
    }

    public static void main(String[] args) {
        launch();
    }
}