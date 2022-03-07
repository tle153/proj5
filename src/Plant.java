import processing.core.PImage;

import java.util.List;

public abstract class Plant extends ActivityEntity {
    private int health;

    public Plant(String id, Point position, List<PImage> images, int actionPeriod, int animationPeriod, int health) {
        super(id, position, images, actionPeriod, animationPeriod);
        this.health = health;
    }

    public void decrementHealth() {
        health--;
    }

    protected void incrementHealth() {
        health++;
    }

    protected int getHealth() {
        return health;
    }

    @Override
    public void executeActivity(
            WorldModel world,
            ImageStore imageStore,
            EventScheduler scheduler) {

        if (!transformPlant(world, scheduler, imageStore)) {

            scheduler.scheduleEvent(this,
                    Factory.createActivityAction(this, world, imageStore),
                    getActionPeriod());
        }
    }

    protected abstract boolean transformPlant(WorldModel world,
                                  EventScheduler scheduler,
                                  ImageStore imageStore);
}
