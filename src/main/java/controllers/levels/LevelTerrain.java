package controllers.levels;

import level.Spawner;
import level.path.PathBuilder;

import java.util.Map;

/** LevelTerrain
 *
 * Helper class for LevelController
 * Creates path and entities, sets rewards for defeating an entity
 */
public abstract class LevelTerrain {
    protected PathBuilder path;
    protected Spawner spawner;

    public PathBuilder getPath() {
        return path;
    }

    public abstract Map<String, Long> getRewardsMap();

    public void update(double c) {
        path.update();
    }
}
