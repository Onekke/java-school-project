package controllers.levels;

import handlers.*;
import javafx.fxml.Initializable;

/**
 * LevelController
 * Handles level-related game logic:
 * Scrolls background
 * Creates path
 * Spawns entities
 */

public abstract class LevelController implements Initializable {
    protected GameLoopHandler gameLoopHandler;
    protected EntityHandler entityHandler;
    protected ScrollingHandler scrollingHandler;
    protected LevelTerrain terrain;

    public void setHandlers(GameLoopHandler gameLoopHandler, StatisticsHandler statisticsHandler,
                            ScrollingHandler scrollingHandler,
                            GravityHandler gravityHandler) {
        this.gameLoopHandler = gameLoopHandler;
        this.scrollingHandler = scrollingHandler;
        this.entityHandler = new EntityHandler(scrollingHandler, gravityHandler);
        this.scrollingHandler.add(statisticsHandler);

    }
    public abstract EntityHandler getEntityHandler();

    abstract public void update(double c);

    abstract public void updateText(double distance, long score);

    abstract public void reset();

    public void resumeGame() {
        gameLoopHandler.resumeGameLoop();
    }

    public void restartGame() {
        gameLoopHandler.restartGame();
        gameLoopHandler.sceneSelector.setGame();
    }

    public void backToMenu() {
        gameLoopHandler.restartGame();
        gameLoopHandler.sceneSelector.setMainMenu();
    }

    public abstract void setPauseMenu(boolean status);
    public abstract void setGameOverMenu(boolean status);
}
