package handlers;

import controllers.levels.LevelController;
import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import main.SceneSelector;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * GameLoopHandler
 *
 * Controls the flow of the game
 * Uses threads for collecting statistics data
 * Implements pause/resume game controls
 * Listens to inputs from player
 */
public class GameLoopHandler {

    private Logger logger = Logger.getLogger(GameLoopHandler.class.getName());

    /* Game scene components */
    private final Scene scene;
    public SceneSelector sceneSelector;
    public final LevelController level;

    /* Game handlers */
    private StatisticsHandler statisticsHandler;
    private ScrollingHandler scrollingHandler;
    private GravityHandler gravityHandler;

    private PlayerControlHandler playerControl;
    private AnimationTimer timer;

    public GameLoopHandler(Scene scene,
                           LevelController level,
                           SceneSelector sceneSelector) {

        this.scene = scene;

        // SET HANDLERS
        statisticsHandler = new StatisticsHandler();
        scrollingHandler = new ScrollingHandler();
        gravityHandler = new GravityHandler();

        this.level = level;
        this.level.setPauseMenu(false);
        this.level.setHandlers(this, statisticsHandler, scrollingHandler, gravityHandler);

        this.sceneSelector = sceneSelector;

        // SET KEY LISTENER
        scene.setOnKeyPressed(event -> {
            scene.setOnKeyPressed(new OnKeyPressedHandler());
            scene.setOnKeyReleased(new OnKeyReleasedHandler());
            playerControl = new PlayerControlHandler(this, scrollingHandler, this.level.getEntityHandler().getHero());
            playerControl.keyPressed(event);
        });
    }

    public void startGameLoop() {
        level.setGameOverMenu(false);

        Thread statistics = new Thread(statisticsHandler);
        statistics.setDaemon(true);
        statistics.start();

        // SET ANIMATION TIMER TO CONTROL PAUSE/RESUME CALLS
        timer = new AnimationTimer() {
            double c = 1;
            long prevNow = System.nanoTime() - (int) (1e9/60);

            @Override
            public void handle(long now) {
                c = (now - prevNow)/(1e9/60f);
                if (c > 10) c = 1;
                prevNow = now;
                if (playerControl != null)
                    playerControl.update();     //  Apply player input, affected: scrollingHandler and entityHandler (hero)
                scrollingHandler.update(c);     //  Apply scrolling speed
                gravityHandler.update(c);       //  GPath collisions, apply gravity
                level.update(c);                //  Update background, path, entityHandler(collisions, movement)
            }
        };

        timer.start();
    }

    public void pauseGameLoop() {
        level.setPauseMenu(true);
        level.getEntityHandler().setAllAnimations(false);
        timer.stop();
//        sceneSelector.setMainMenu();
    }

    public void endGameLoop() {
        level.setGameOverMenu(true);
        timer.stop();
//        sceneSelector.setMainMenu();
    }

    public void resumeGameLoop() {
        level.getEntityHandler().setAllAnimations(true);
        level.setPauseMenu(false);
        timer.start();
    }

    public void restartGame() {
        //scene.setOnKeyPressed(event -> {
//            pauseGameLoop();
//        endGameLoop();
//        level.getEntityHandler().setAllAnimations(false);
//        timer.stop();
        scene.setOnKeyPressed(noEvent -> {});
        scene.setOnKeyReleased(noEvent -> {});
        statisticsHandler.stop();
        boolean w = statisticsHandler.writeStatisticsToFile("leaderboard" + File.separator + "run");
        if (!w) logger.log(Level.WARNING, "Unable to write into file");
        timer = null;

        // RESET GAME
        level.reset();
        //});
    }

    private class OnKeyPressedHandler implements EventHandler<KeyEvent> {
        @Override
        public void handle(KeyEvent event) {
            playerControl.keyPressed(event);
        }
    }

    private class OnKeyReleasedHandler implements EventHandler<KeyEvent> {
        @Override
        public void handle(KeyEvent event) {
            playerControl.keyReleased(event);
        }
    }
}