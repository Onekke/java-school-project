package handlers;

import entity.characters.hero.Hero;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/** PlayerControlHandler
 *
 * Sets, changes, updates hero's speed and position
 * Listens to the input from the keyboard
 */

public class PlayerControlHandler {

    /* Hero settings */
    private static final double MAX_SPEED = 15;
    private static final double MIN_SPEED = 3;
    private double speed;
    private boolean jump = false, crawl = false, gamePaused = false, speed_down = false, speed_up = false;
    private Hero hero;

    /* Game handlers */
    private GameLoopHandler gameLoop;
    private ScrollingHandler scrollingHandler;

    PlayerControlHandler(GameLoopHandler gameLoop,
                         ScrollingHandler scrollingHandler,
                         Hero hero) {

        this.gameLoop = gameLoop;
        this.speed = 0;
        this.scrollingHandler = scrollingHandler;
        this.scrollingHandler.setScrollingSpeed(speed);
        this.hero = hero;
    }


    void keyPressed(KeyEvent event) {
        switchControl(event, true);
        hero.getAnimation().play();
    }

    void keyReleased(KeyEvent event) {

        switchControl(event, false);
        if (event.getCode() == KeyCode.ESCAPE) {

            // RESUME GAME
            if (gamePaused) {
                gameLoop.resumeGameLoop();
                gamePaused = false;

            // PAUSE GAME
            } else {
                gameLoop.pauseGameLoop();
                gamePaused = true;
            }
        }
    }

    private void switchControl(KeyEvent event, boolean value) {
        switch (event.getCode()) {

            // JUMP
            case Z:
            case W:
            case UP:
            case SPACE:
            case ENTER:
                jump = value;
                break;

            // CRAWL
            case X:
            case S:
            case SHIFT:
            case DOWN:
                crawl = value;
                break;

            // SLOW DOWN
            case LEFT:
            case A:
                speed_down = true;
                break;

            // RUN FASTER
            case RIGHT:
            case D:
                speed_up = true;
                break;
        }
    }

    public void update() {

        // GAME OVER
        if (!hero.isAlive()) {
            scrollingHandler.setScrollingSpeed(0);
            gameLoop.endGameLoop();
            return;
        }

        // CHANGE HERO'S SPEED
        speed = scrollingHandler.getScrollingSpeed();
        hero.setJump(jump);
        hero.crawl(crawl);

        if (speed_down && speed > MIN_SPEED) {
            speed -= 0.4;
            speed_down = false;
        } else if (speed_up && speed < MAX_SPEED) {
            speed += 0.4;
            speed_up = false;
        }
         else if (speed < MAX_SPEED) {
            speed += 0.025;                         // average speed update rate
        }

        hero.setNodeX(hero.getNodeX());             // +0.1 ?
        scrollingHandler.setScrollingSpeed(speed);
    }
}
