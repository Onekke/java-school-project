package controllers.levels.level1;

import controllers.levels.LevelController;
import handlers.*;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import level.ScrollingBackground;
import level.ScrollingBackgroundCreator;
import main.SceneSelector;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * LevelOneController
 * Loads objects from FXML file
 * Calls scrolling background
 * Updates text with player statistics
 */

public class LevelOneController extends LevelController {

    @FXML
    ImageView sky_horizon,
              clouds_back_1, clouds_back_2, clouds_back_3, clouds_back_4, clouds_back_5,
              clouds_front_1, clouds_front_2, clouds_front_3, clouds_front_4, clouds_front_5,
              field_far_0, field_far_1,
              field_0, field_1, field_2, field_3,
              grass_back_1_0, grass_back_2_0, grass_back_1_1, grass_back_2_1,
              ground_0, ground_1,
              grass_front_1_0, grass_front_2_0, grass_front_1_1, grass_front_2_1;
    @FXML
    Text distanceText, scoreText;
    @FXML
    Pane pane, background, foreground;
    @FXML
    Group entityRoot;
    @FXML
    Pane pauseMenu, gameOverMenu;

    private ScrollingBackgroundCreator backgroundCreator;
    private SceneSelector sceneSelector;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        backgroundCreator = new ScrollingBackgroundCreator();
        double  SPEED_OFFSET = 0.025;

        // CREATE A 2D ARRAY OF BACKGROUND IMAGES SORTED ACCORDING TO THE LAYER POSITION
        ImageView backgrounds [][] = {{sky_horizon},
                                      {clouds_back_1, clouds_back_2, clouds_back_3, clouds_back_4, clouds_back_5},
                                      {clouds_front_1, clouds_front_2, clouds_front_3, clouds_front_4, clouds_front_5},
                                      {field_far_0, field_far_1},
                                      {field_0, field_1, field_2, field_3},
                                      {grass_back_1_0, grass_back_2_0,  grass_back_1_1, grass_back_2_1},
                                      {ground_0, ground_1},
                                      {grass_front_1_0, grass_front_2_0, grass_front_1_1, grass_front_2_1}
        };
        backgroundCreator.addLayer(new ScrollingBackground(backgrounds[0], (0.00025)));
        backgroundCreator.addLayer(new ScrollingBackground(backgrounds[1], (0.004)));
        backgroundCreator.addLayer(new ScrollingBackground(backgrounds[2], (0.012)));
        backgroundCreator.addLayer(new ScrollingBackground(backgrounds[3], (0.02)));
        backgroundCreator.addLayer(new ScrollingBackground(backgrounds[4], (0.1)));
        backgroundCreator.addLayer(new ScrollingBackground(backgrounds[5], (0.5)));
        backgroundCreator.addLayer(new ScrollingBackground(backgrounds[6], (1)));
        backgroundCreator.addLayer(new ScrollingBackground(backgrounds[7], (1.2)));
//        for (int i = 0; i < backgrounds.length; ++i) {
//            backgroundCreator.addLayer(new ScrollingBackground(backgrounds[i], (SPEED_OFFSET * i*0.5)));
//        }
    }

    @Override
    public void setHandlers(GameLoopHandler gameLoopHandler, StatisticsHandler statisticsHandler,
                            ScrollingHandler scrollingHandler,
                            GravityHandler gravityHandler) {
        // SET HANDLERS

        super.setHandlers(gameLoopHandler, statisticsHandler, scrollingHandler, gravityHandler);
        entityHandler.setRoot(entityRoot);
        backgroundCreator.setScrollingHandler(scrollingHandler);

        terrain = new LevelOneTerrain(scrollingHandler, entityHandler, background);
        gravityHandler.setPath(terrain.getPath());

        statisticsHandler.setRewards(terrain.getRewardsMap());
        statisticsHandler.setEntityHandler(entityHandler);
        statisticsHandler.setLevel(this);

        scrollingHandler.add(statisticsHandler);
    }

    @Override
    public EntityHandler getEntityHandler() {

        return entityHandler;
    }

    @Override
    public void update(double c) {

        backgroundCreator.update();
        terrain.update(c);
        entityHandler.update(c);
    }

    @Override
    public void updateText(double distance, long score) {

        this.distanceText.setText("Distance " + String.format("%.0f m", distance));
        this.scoreText.setText("Score " + Long.toString(score));
    }

    @Override
    public void reset() {

        entityHandler.removeAllEntities();
        terrain.getPath().removeAllSegments();
    }

    public void setLeaderboard() {
        sceneSelector.setLeaderboard();
    }

    public void shutdown() {
        System.exit(0);
    }

    @Override
    public void setPauseMenu(boolean status) {
        pauseMenu.setVisible(status);
        pauseMenu.setDisable(!status);
    }

    @Override
    public void setGameOverMenu(boolean status) {
        gameOverMenu.setVisible(status);
        gameOverMenu.setDisable(!status);
    }
}
