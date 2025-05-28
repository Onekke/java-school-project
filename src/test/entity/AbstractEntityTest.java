package entity;

import javafx.geometry.Bounds;
import javafx.scene.Node;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AbstractEntityTest {
    private AbstractEntity entity;

    @BeforeEach
    void setUp() {
        entity = new AbstractEntityImpl();
    }

    @Test
    void setIsAlive() {
        entity.setIsAlive(false);
        assertFalse(entity.isAlive());
        entity.setIsAlive(true);
        assertTrue(entity.isAlive());
    }

    @Test
    void killAndDie() {
        entity.setIsAlive(true);
        AbstractEntity entity2 = new AbstractEntityImpl();
        entity2.kill(entity);
        assertFalse(entity.isAlive());
    }

    private class AbstractEntityImpl extends AbstractEntity {
        @Override
        public Node getNode() {
            return null;
        }

        @Override
        public Bounds getBounds() {
            return null;
        }

        @Override
        public void update(double c) {

        }

        @Override
        public void render() {

        }
    }
}