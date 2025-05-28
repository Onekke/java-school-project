package controllers.menu;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import main.SceneSelector;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * MenuController
 * Binds menu buttons with corresponding actions
 */

public class MenuController implements Initializable {

    private SceneSelector sceneSelector;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    public void setGame() {
        sceneSelector.setGame();
    }

    public void setLeaderboard() {
        sceneSelector.setLeaderboard();
    }

    public void shutdown() {
        System.exit(0);
    }

    public void setSceneSelector(SceneSelector sceneSelector) {

        this.sceneSelector = sceneSelector;
    }

    public void openControlsMenu() {

    }
}