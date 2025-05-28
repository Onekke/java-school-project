package main;

import controllers.leaderboard.LeaderboardController;
import controllers.levels.LevelController;
import controllers.menu.MenuController;
import handlers.GameLoopHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/* SceneSelector
 *
 * Loads FXML files on initialization
 * Sets the desired game scene
 *
 * Available scenes: Main Menu, Leaderboard, Game
 */

public class SceneSelector {

    /* Screen properties */
    private static final double SCREEN_WIDTH = 1200;
    private static final double SCREEN_HEIGHT = 600;

    /* Game stage settings */
    private Stage stage;
    private Scene scene;
    private FXMLLoader FXML_main_menu, FXML_game, FXML_leaderboard;
    private Parent root_main_menu, root_game, root_leaderboard;
    private boolean isRunning;

    SceneSelector(Stage stage) {

        this.stage = stage;

        // LOAD MAIN MENU
        FXML_main_menu = new FXMLLoader(getClass().getResource("/FXML/menu.fxml"));
        Logger logger = Logger.getLogger(SceneSelector.class.getName());
        try {
            root_main_menu = FXML_main_menu.load();
        } catch (IOException e) {
            logger.warning("menu.fxml not found");
            System.exit(404);
        }

        // LOAD LEADERBOARD
        FXML_leaderboard = new FXMLLoader(getClass().getResource("/FXML/leaderboard.fxml"));
        try {
            root_leaderboard = FXML_leaderboard.load();
        } catch (IOException e) {
            logger.warning("leaderboard.fxml not found");
            System.exit(404);
        }

        // LOAD GAME LEVEL
        FXML_game = new FXMLLoader(getClass().getResource("/FXML/level.fxml"));
        try {
            root_game = FXML_game.load();
        } catch (IOException e) {
            logger.warning("level.fxml not found");
            System.exit(404);
        }
    }

    /* Sets the chosen game scene */
    private void setScene(Parent root) {

        if (!isRunning) {
            scene = new Scene(root, SCREEN_WIDTH, SCREEN_HEIGHT);
            stage.setScene(scene);
            isRunning = true;
        } else {
            if (root != scene.getRoot()) {
                scene.setRoot(root);
            }
        }
    }

    /* Creates a main menu */
    public void setMainMenu() {

        setScene(root_main_menu);

        // GET MAIN MENU CONTROLLER
        MenuController menuController = FXML_main_menu.getController();
        menuController.setSceneSelector(this);
        stage.show();
    }

    /* Creates a leaderboard menu */
    public void setLeaderboard() {

        setScene(root_leaderboard);
        LeaderboardController leaderboardController = FXML_leaderboard.getController();
        leaderboardController.setSceneSelector(this);
        stage.show();
    }

    /* Creates main game scene */
    public void setGame() {

        setScene(root_game);

        // GET LEVEL/GAME CONTROLLER
        LevelController levelController = FXML_game.getController();
        GameLoopHandler gameLoop = new GameLoopHandler(scene, levelController, this);
        gameLoop.startGameLoop();
        stage.show();
    }
}
