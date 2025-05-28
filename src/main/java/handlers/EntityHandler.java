package handlers;

import entity.Entity;
import entity.KillMethod;
import entity.characters.hero.Hero;
import javafx.geometry.Bounds;
import javafx.scene.Group;

import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * EntityHandler
 * Creates, removes, updates entities
 * Handles collisions
 */

public class EntityHandler {

    // CREATE A LOGGER FOR PRINTING INFORMATION ON ENTITIES BEING DESTROIED BY/COLLIDED WITH A PLAYER
    private Logger logger = Logger.getLogger(EntityHandler.class.getName());

    private Set<Entity> entities, deadEntities;
    private ScrollingHandler scrollingHandler;
    private GravityHandler gravityHandler;
    private Group root;
    private Hero hero = null;
//    private StatisticsHandler statistics;

    public EntityHandler(ScrollingHandler scrollingHandler,
                         GravityHandler gravityHandler) {

        this.scrollingHandler = scrollingHandler;
        this.gravityHandler = gravityHandler;
        this.entities = new HashSet<>();
        this.deadEntities = new HashSet<>();;
    }

    public void setRoot(Group root) {
        this.root = root;
    }

    public void spawnEntity(Entity e) {
        spawnEntity(e, root);
    }

    private void spawnEntity(Entity e, Group group) {

        // ADD AN ENTITY TO A HASH SET
        entities.add(e);

        // SET SCROLL
        if (e instanceof Scrolling) {
            scrollingHandler.add((Scrolling) e);
        }
        // SET GRAVITY
        if (e instanceof Gravity) {
            gravityHandler.add((Gravity) e);
        }
        group.getChildren().add(e.getNode());
    }

    private void removeEntity(Entity e) {
        removeEntity(e, root);
    }

    private void removeEntity(Entity e, Group group) {

        group.getChildren().remove(e.getNode());

        if (e instanceof Scrolling) {
            scrollingHandler.remove((Scrolling) e);
        }
        if (e instanceof Gravity) {
            gravityHandler.remove((Gravity) e);
        }
    }

    public void removeAllEntities() {
        removeAllEntities(root);
    }

    private void removeAllEntities(Group group) {
        entities.forEach(this::removeEntity);
    }

    public void spawnHero(Hero hero) {
        spawnHero(hero, root);
    }

    private void spawnHero(Hero hero, Group group) {
        this.hero = hero;
        entities.add(hero);
        gravityHandler.add(hero);
        group.getChildren().add(hero.getNode());
    }

    Hero getHero() {
        return hero;
    }

    Set<Entity> getDeadEntities() {
        return deadEntities;
    }

    public int getEntitiesCount() {
        return entities.size();
    }

    public void setAllAnimations(boolean status) {
        if (status) {
            entities.forEach(entity -> {
                if(entity.getAnimation() != null) entity.getAnimation().play();
            });
        } else {
            entities.forEach(entity -> {
                if (entity.getAnimation() != null) entity.getAnimation().pause();
            });
        }

    }

    public void update(double c) {

        for (Entity e : entities) {

            // KILL IF OFF-SCREEN
            Bounds bounds = e.getBounds();
            if (bounds.getMaxX() < 0) {
                logger.log(Level.FINEST, "ENTITY IS OUT OF BOUNDS: " + e.getClass().getName());
                e.die(KillMethod.OUTOFBOUNDS);
            } else if (hero != null) {

                // CONTROL HERO'S COLLISIONS
                if (hero.isAlive() && e != hero && bounds.intersects(hero.getBounds())) {

                    // KILL ENTITY IF HERO'S STRONGER, ELSE KILL THE HERO
                    if (hero.getStrength() >= e.getStrength()) {
                        hero.kill(e);
                    } else {
                        e.kill(hero);
                    }
                }
            }

            // REMOVE AN ENTITY IF IT'S DEAD
            if (!e.isAlive()) {
                deadEntities.add(e);
                removeEntity(e);
                continue;
            }
            // UPDATE AN ENTITY WITH ITS OWN MOVEMENT
            e.update(c);
            e.render();
        }
        entities.removeAll(deadEntities);
    }

    public void render() {
        for (Entity e : entities) {
            e.render();
        }
    }
}
