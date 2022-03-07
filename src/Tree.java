import processing.core.PImage;

import java.util.*;

public class Tree extends Plant
{
    public Tree(
            String id,
            Point position,
            List<PImage> images,
            int actionPeriod,
            int animationPeriod,
            int health
        )
    {
        super(id, position, images, actionPeriod, animationPeriod, health);
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

        return false;
    }
}
