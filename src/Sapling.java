import processing.core.PImage;

import java.util.*;

public class Sapling extends Plant
{
    private final int healthLimit;

    public Sapling(
            String id,
            Point position,
            List<PImage> images
        )
    {
        super(id, position, images, Factory.SAPLING_ACTION_ANIMATION_PERIOD, Factory.SAPLING_ACTION_ANIMATION_PERIOD, 0);
        healthLimit = Functions.SAPLING_HEALTH_LIMIT;
    }

    public Sapling(
            String id,
            Point position,
            List<PImage> images,
            int health
    )
    {
        super(id, position, images, Factory.SAPLING_ACTION_ANIMATION_PERIOD, Factory.SAPLING_ACTION_ANIMATION_PERIOD, health);
        healthLimit = Functions.SAPLING_HEALTH_LIMIT;
    }

    @Override
    public void executeActivity(
            WorldModel world,
            ImageStore imageStore,
            EventScheduler scheduler)
    {
        incrementHealth();
        super.executeActivity(world, imageStore, scheduler);
    }

    @Override
    protected boolean transformPlant(
            WorldModel world,
            EventScheduler scheduler,
            ImageStore imageStore)
    {
        if (getHealth() <= 0) {
            Entity stump = Factory.createStump(getId(),
                    getPosition(),
                    imageStore.getImageList(Functions.STUMP_KEY));

            world.removeEntity(this);
            scheduler.unscheduleAllEvents(this);

            world.addEntity(stump);

            return true;
        }
        else if (getHealth() >= healthLimit)
        {
            Entity tree = Factory.createTree("tree_" + getId(),
                    getPosition(),
                    getNumFromRange(Functions.TREE_ACTION_MAX, Functions.TREE_ACTION_MIN),
                    getNumFromRange(Functions.TREE_ANIMATION_MAX, Functions.TREE_ANIMATION_MIN),
                    getNumFromRange(Functions.TREE_HEALTH_MAX, Functions.TREE_HEALTH_MIN),
                    imageStore.getImageList(Functions.TREE_KEY));

            world.removeEntity(this);
            scheduler.unscheduleAllEvents(this);

            world.addEntity(tree);
            ((AnimatableEntity)tree).scheduleActions(scheduler, world, imageStore);

            return true;
        }

        return false;
    }

    public static int getNumFromRange(int max, int min)
    {
        Random rand = new Random();
        return min + rand.nextInt(
                max
                        - min);
    }
}
