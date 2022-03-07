import processing.core.PImage;

import java.util.*;

public class DudeFull extends DudeEntity
{
    public DudeFull(
            String id,
            Point position,
            List<PImage> images,
            int resourceLimit,
            int actionPeriod,
            int animationPeriod
        )
    {
        super(id, position, images, resourceLimit, actionPeriod, animationPeriod);
    }

    @Override
    public void executeActivity(
            WorldModel world,
            ImageStore imageStore,
            EventScheduler scheduler)
    {
        Optional<Entity> fullTarget =
                world.findNearest(getPosition(), new ArrayList<>(Arrays.asList(House.class)));

        if (fullTarget.isPresent() && moveTo(world,
                fullTarget.get(), scheduler))
        {
            transformFull(world, scheduler, imageStore);
        }
        else {
            scheduler.scheduleEvent(this,
                    Factory.createActivityAction(this, world, imageStore),
                    getActionPeriod());
        }
    }

    protected void transformFull(
            WorldModel world,
            EventScheduler scheduler,
            ImageStore imageStore)
    {
        Entity miner = Factory.createDudeNotFull(getId(),
                getPosition(), getActionPeriod(),
                getAnimationPeriod(),
                getResourceLimit(),
                getImages());

        world.removeEntity(miner);
        scheduler.unscheduleAllEvents(miner);

        world.addEntity(miner);
        ((AnimatableEntity)miner).scheduleActions(scheduler, world, imageStore); // miner is also an acitivityEntity
    }

    @Override
    protected boolean moveTo(
            WorldModel world,
            Entity target,
            EventScheduler scheduler)
    {
        if (adjacent(target.getPosition())) {
            return true;
        }
        else {
            return super.moveTo(world, target, scheduler);
        }
    }
}
