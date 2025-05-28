package handlers;

import controllers.levels.LevelController;
import entity.Entity;
import entity.KillMethod;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.logging.Logger;

/* StatisticsHandler
 *
 * Collects statistics data from the game
 * Writes collected data to the output file
 */

public class StatisticsHandler implements Runnable, Scrolling {

    private Logger logger = Logger.getLogger(StatisticsHandler.class.getName());

    private LevelController level;
    private EntityHandler entityHandler;
    private boolean running;
    private Map<String, Long> rewards;
    //private Map<String, Long> entities;   // Might be used to collect statistics about entities encountered
    private double pixeldistance;
    private long score, distance;

    StatisticsHandler() {
        distance = score = 0;
    }

    void stop() {
        running = false;
    }

    private void entityCollisionNotice(Entity e) {
        score += rewards.get(e.getClass().getName());
    }

    public double getDistanceInPixels() {
        return pixeldistance;
    }

    public long getDistance() {
        return distance;
    }

    public long getScore() {
        return score;
    }

    public void setRewards(Map<String, Long> rewards) {
        this.rewards = rewards;
    }

    public void setLevel(LevelController level) {
        this.level = level;
    }

    public void setEntityHandler(EntityHandler entityHandler) {
        this.entityHandler = entityHandler;
    }

    boolean writeStatisticsToFile(String path) {
        boolean success = true;
        Properties prop = new Properties();
        OutputStream output = null;

        try {
            output = new FileOutputStream(path);

            // SET THE PROPERTIES VALUE
            prop.setProperty("run", "test");
            prop.setProperty("pixeldistance", Double.toString(pixeldistance));
            prop.setProperty("score", Long.toString(score));

            // SAVE PROPERTIES TO THE PROJECT ROOT FOLDER
            prop.store(output, null);
            logger.info("Saved score: " + String.format("%d", score) + " distance: " + String.format("%d", distance) + " by user \"test\"");
        } catch (IOException io) {
            success = false;
        } finally {
            if (output != null) {
                try {
                    output.close();
                } catch (IOException e) {
                    success = false;
                }
            }

        }
        return success;
    }

    @Override
    public void run() {
        running = true;
        while (entityHandler == null || level == null) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                logger.warning("Interrupted Statistics thread in phase 0");
            }
        }
        while (running) {
            Set<Entity> deadEntities = entityHandler.getDeadEntities();
            deadEntities.forEach(e -> {
                KillMethod causeOfDeath = e.getCauseOfDeath();
                if (causeOfDeath != null && causeOfDeath == KillMethod.KOSHISHTA) {
                    entityCollisionNotice(e);
                }
            });
            deadEntities.clear();
            distance = (long) (pixeldistance / 120);
            level.updateText(distance, score);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                logger.warning("Interrupted Statistics thread in phase 1");
            }
        }
    }

    @Override
    public void setScrollingSpeed(double scrollingSpeed) {
        pixeldistance += scrollingSpeed;
    }
}
