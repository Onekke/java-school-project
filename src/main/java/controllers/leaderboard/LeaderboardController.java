package controllers.leaderboard;

import javafx.event.ActionEvent;
import main.SceneSelector;

/**
 * Controller for leaderboard screen
 */
public class LeaderboardController {
    private SceneSelector sceneSelector;

    public void setMainMenu(ActionEvent actionEvent) {
        sceneSelector.setMainMenu();
    }

    public void setGame(ActionEvent actionEvent) {
        sceneSelector.setGame();
    }

    public void setSceneSelector(SceneSelector sceneSelector) {
        this.sceneSelector = sceneSelector;
    }
}
