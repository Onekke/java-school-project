package handlers;

import controllers.levels.LevelController;
import entity.AbstractEntity;
import entity.Entity;
import entity.KillMethod;
import entity.characters.hero.Hero;
import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.Node;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.URL;
import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;

class EntityHandlerTest {
    private static final int UPDATES = 1000;
    private EntityHandler entityHandler;

    @BeforeEach
    void setUp() {
        ScrollingHandler scrollingHandler = new ScrollingHandler();
        GravityHandler gravityHandler = new GravityHandler();
        entityHandler = new EntityHandler(scrollingHandler, gravityHandler);
        entityHandler.setRoot(new Group());
    }

    @Test
    void spawnAndRemoveEntity() {
        Entity entity = new CountOnUpdateEntity();
        entityHandler.spawnEntity(entity);
        assertEquals(1, entityHandler.getEntitiesCount());
        entity.die(KillMethod.UNSPECIFIED);     // Should remove entity on death with next update
        entityHandler.update(1);
        assertEquals(0, entityHandler.getEntitiesCount());
    }

    @Test
    void spawnHero() {
        Hero hero = new Hero(0, 0);
        entityHandler.spawnHero(hero);
        assertEquals(hero, entityHandler.getHero());
    }

    @Test
    void update() {
        CountOnUpdateEntity[] entities = new CountOnUpdateEntity[UPDATES];
        for (int i = 0; i < UPDATES; i++) {
            entities[i] = new CountOnUpdateEntity();
            entityHandler.spawnEntity(entities[i]);
        }
        for (int i = 0; i < UPDATES; i++) {
            entityHandler.update(1);
        }
        assertEquals(UPDATES, entityHandler.getEntitiesCount());
        for (int i = 0; i < UPDATES; i++) {
            assertEquals(UPDATES, entities[i].getUpdateCounter());
        }
    }

    private class CountOnUpdateEntity extends AbstractEntity {
        private int updateCounter;
        private Node node;

        public CountOnUpdateEntity() {
            updateCounter = 0;
            node = new Group();
            isAlive = true;
        }

        public int getUpdateCounter() {
            return updateCounter;
        }

        @Override
        public Node getNode() {
            return node;
        }

        @Override
        public Bounds getBounds() {
            return new BoundingBox(0, 0, 1, 1);
        }

        @Override
        public void update(double c) {
            updateCounter++;
        }

        @Override
        public void render() {

        }
    }
}