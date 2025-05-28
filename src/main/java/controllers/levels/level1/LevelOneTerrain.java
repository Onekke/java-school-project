package controllers.levels.level1;

import controllers.levels.LevelTerrain;
import entity.Entity;
import entity.characters.hero.Hero;
import entity.characters.npc.Chicken;
import entity.characters.npc.Butterfly;
import entity.characters.npc.Spike;
import handlers.EntityHandler;
import handlers.ScrollingHandler;
import javafx.scene.layout.Pane;
import level.Spawner;
import level.path.PathBuilder;
import level.path.PathLineSegment;
import level.path.PathSegment;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

/**
 * LevelOneTerrain
 * Creates the ground for the game
 * Updates path segments
 * Spawns hero, loot and obstacles
 */
public class LevelOneTerrain extends LevelTerrain {

    /* Path properties */
    private static final double LINE_WIDTH = 70;
    private static final double SEGMENT_LENGTH = 100;
    private static final double UPPER_GROUND_LEVEL = 500;
    private static final double BASE_GROUND_LEVEL = 510;
    private static final double LOWER_GROUND_LEVEL = 530;

    /* Obstacles properties */
    private static final int OBSTACLE_DISTANCE = 12;
    private static final int OBSTACLE_DISTANCE_DEVIATION = 1;
    private int nextObstacleSegment = 13;

    /* Loot properties */
    private static final int LOOT_DISTANCE = 7;
    private static final int LOOT_DISTANCE_DEVIATION = 5;
    private int nextLootSegment = 4;

    private double lastY;

    LevelOneTerrain(ScrollingHandler scrollingHandler,
                    EntityHandler entityHandler,
                    Pane root) {

        // CREATE A PATH
        path = new PathBuilder(scrollingHandler, root);
        lastY = BASE_GROUND_LEVEL;
        path.addSegment(new PathLineSegment(-200, lastY, SEGMENT_LENGTH, LINE_WIDTH));

        // SPAWN HERO
        spawner = new Spawner(entityHandler);
        entityHandler.spawnHero(new Hero(100, BASE_GROUND_LEVEL - 100));
    }

    @Override
    public Map<String, Long> getRewardsMap() {

        Map<String, Long> rewards = new HashMap<>();
        // SET REWARDS
        rewards.put(Chicken.class.getName(), 10L);
        rewards.put(Butterfly.class.getName(), 20L);
        rewards.put(Spike.class.getName(), -5L);
        return rewards;
    }

    @Override
    public void update(double c) {
        super.update(c);

        // CREATE NEW SEGMENT
        double maxX = path.getMaxX();
        if (maxX < 2400) {
            int rng = ThreadLocalRandom.current().nextInt(100);
            if (rng >= 20) {
                int numberOfSegments = ThreadLocalRandom.current().nextInt(1, 8);
                for (int i = 0; i < numberOfSegments; i++) {
                    double fromX = maxX + i*SEGMENT_LENGTH - 1;
                    double fromY = this.lastY;
                    addSegment(new PathLineSegment(fromX, fromY, SEGMENT_LENGTH, LINE_WIDTH));
                }

            // CREATE NEW 2ND LEVEL SEGMET
            } else if (rng >= 3) {
                double fromX = maxX - 1;
                double fromY = this.lastY;
                int numberOfSegments = ThreadLocalRandom.current().nextInt(1, 8);
                addDoubleLayerStructure(fromX, fromY, numberOfSegments);

            // CREATE A HOLE
            } else if (rng >= 0) {
                double fromX = maxX - 1;
                double fromY = this.lastY;
                int numberOfSegments = ThreadLocalRandom.current().nextInt(2, 4);
                addHole(fromX, fromY, numberOfSegments);
            }

        // REMOVE OFF-SCREEN SEGMENT
        } else if (path.getMinX() < -400) {
            path.removeSegment(0);
        }
    }

    private void addSegment(PathSegment pathSegment) {
        nextLootSegment -= 1;
        nextObstacleSegment -= 1;

        // SPAVN LOOT
        if (nextLootSegment <= 0) {
            boolean b = spawnLoot(pathSegment);
            if (b) {
                nextLootSegment = ThreadLocalRandom.current().nextInt(LOOT_DISTANCE - LOOT_DISTANCE_DEVIATION, LOOT_DISTANCE + LOOT_DISTANCE_DEVIATION);
            }
        }

        // SPAWN OBSTACLE
        if (nextObstacleSegment <= 0) {
            boolean b = spawnObstacle(pathSegment);
            if (b) {
                nextObstacleSegment = ThreadLocalRandom.current().nextInt(OBSTACLE_DISTANCE - OBSTACLE_DISTANCE_DEVIATION, OBSTACLE_DISTANCE + OBSTACLE_DISTANCE_DEVIATION);
            }
        }

        path.addSegment(pathSegment);
    }

    private boolean spawnLoot(PathSegment pathSegment) {
        Entity e;
        // SPAWN BUTTERFLY
        if (ThreadLocalRandom.current().nextInt(20) > 17) {
            double x = pathSegment.getFromX() + ThreadLocalRandom.current().nextDouble(SEGMENT_LENGTH);
            double y = ThreadLocalRandom.current().nextDouble(150, 300);
            double offset = ThreadLocalRandom.current().nextDouble(6, 8);
            e = spawner.createFlyingChicken(x, y, offset);

        // SPAWN CHICKEN
        } else {
        double x = pathSegment.getFromX() + ThreadLocalRandom.current().nextDouble(SEGMENT_LENGTH);
        double y = pathSegment.getHeight(x);
        int spriteNumber = ThreadLocalRandom.current().nextInt(0,6);
        e = spawner.createChicken(x, y, spriteNumber);
        }
        spawner.getEntityHandler().spawnEntity(e);
        return true;
    }

    private boolean spawnObstacle(PathSegment pathSegment) {
        Entity e;
        if (pathSegment.getCoefficient() == 0) {
            double x = pathSegment.getFromX();
            double y = pathSegment.getHeight(x);
            e = spawner.createSpike(x, y);
        } else {
            return false;
        }
        spawner.getEntityHandler().spawnEntity(e);
        return true;
    }

    private void addDoubleLayerStructure(double fromX, double fromY, int numberOfSegments) {
        this.lastY = LOWER_GROUND_LEVEL;
        addSegment(new PathSegment(fromX - 1, fromY, fromX + SEGMENT_LENGTH, this.lastY, LINE_WIDTH));
        for (int i = 0; i < numberOfSegments; i++) {
            addSegment(new PathLineSegment(fromX + (i + 1) * SEGMENT_LENGTH - 1, this.lastY, SEGMENT_LENGTH + 1, LevelOneTerrain.LINE_WIDTH));
        }
        for (int i = 0; i < numberOfSegments; i++) {
            addSegment(new PathLineSegment(fromX + (i + 1) * SEGMENT_LENGTH - 1, fromY - 200, SEGMENT_LENGTH + 1, LevelOneTerrain.LINE_WIDTH / 2));
        }
        addSegment(new PathSegment(fromX + (numberOfSegments + 1) * SEGMENT_LENGTH - 1, this.lastY, fromX + (numberOfSegments + 2) * SEGMENT_LENGTH, fromY, LINE_WIDTH));
        this.lastY = fromY;
    }

    private void addHole(double fromX, double fromY, int numberOfSegments) {
        this.lastY = UPPER_GROUND_LEVEL;
        addSegment(new PathSegment(fromX - 1, fromY, fromX + SEGMENT_LENGTH, this.lastY, LINE_WIDTH));
        nextObstacleSegment += 1;
        addSegment(new PathLineSegment(fromX +  SEGMENT_LENGTH - 1, LOWER_GROUND_LEVEL + 110, numberOfSegments*SEGMENT_LENGTH, LINE_WIDTH));
        spawner.getEntityHandler().spawnEntity(new Spike(fromX + SEGMENT_LENGTH + 10, LOWER_GROUND_LEVEL + 90, numberOfSegments*SEGMENT_LENGTH - 20 , 150));
        addSegment(new PathSegment(fromX + (numberOfSegments+1)*SEGMENT_LENGTH - 1, this.lastY, fromX + (numberOfSegments+2)*SEGMENT_LENGTH, fromY, LINE_WIDTH));
        this.lastY = fromY;

    }
}
