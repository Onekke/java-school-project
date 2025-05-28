package main;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Main
 *
 * Game title: Adventurous Koshishta
 * Authors: Vojtech Tollar, Alena Mikushina
 *
 * Main class is the application's entry point
 * Launches JavaFX
 * Starts Main Menu
 */

public class Main extends Application {

    public static void main(String[] args) {

        //System.setProperty("sun.java2d.opengl", "true");
        System.setProperty("-Dprism.verbose", "true");
        System.setProperty("-Dprism.forceGPU", "true");
        launch(args);
    }

    @Override
    public void start(Stage stage) {

        SceneSelector selector = new SceneSelector(stage);
        selector.setMainMenu();
    }

}
