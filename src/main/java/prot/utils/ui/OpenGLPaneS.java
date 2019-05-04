package prot.utils.ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import prot.Start;

import java.io.File;
import java.io.IOException;
import java.net.URL;


public class OpenGLPaneS extends Application {

    private Start start = new Start();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        primaryStage.setTitle("Test");

        File file = new File(getClass().getClassLoader().getResource("test.fxml").getFile());
        URL url = file.toURI().toURL();

        Parent root = FXMLLoader.load(url);
        primaryStage.setScene(new Scene(root, 800, 600));

        primaryStage.show();
        Thread thread = new Thread(() -> start.start());
        thread.start();
    }
}
