package level;

import entity.Entity;
import entity.characters.npc.Chicken;
import entity.characters.npc.Butterfly;
import entity.characters.npc.Spike;
import handlers.EntityHandler;

import java.util.concurrent.ThreadLocalRandom;

/* Spawner
 *
 * Spawns entities to the game scene
 */

public class Spawner {

    private static final double MAX_SPIKES_WIDTH = 200;
    private static final double MAX_SPIKES_HEIGHT = 100;

    private EntityHandler entityHandler;

    public Spawner(EntityHandler entityHandler) {
        this.entityHandler = entityHandler;
    }

    public EntityHandler getEntityHandler() {
        return entityHandler;
    }

    // SPAWN CHICKEN
    public Entity createChicken(double x, double y, int spriteNumber) {
        return new Chicken(x, y, spriteNumber);
    }

    // SPAWN FLYING NPS
    public Entity createFlyingChicken(double x, double y, double offset) {
        return new Butterfly(x, y, offset);
    }

    // SPAWN OBSTACLE
    public Entity createSpike(double x, double y) {
        double spikeWidth = ThreadLocalRandom.current().nextDouble(60, MAX_SPIKES_WIDTH);
        double heightBound = MAX_SPIKES_WIDTH + 60 - spikeWidth;
        if (heightBound > MAX_SPIKES_HEIGHT) heightBound = MAX_SPIKES_HEIGHT;
        double spikeHeight = ThreadLocalRandom.current().nextDouble(60, heightBound);
        return new Spike(x, y, spikeWidth, spikeHeight);
    }
}
