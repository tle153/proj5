import processing.core.PImage;

import java.util.*;

public class DudeNotFull extends DudeEntity
{
    private int resourceCount;

    public DudeNotFull(
            String id,
            Point position,
            List<PImage> images,
            int resourceLimit,
            int actionPeriod,
            int animationPeriod
        )
    {
        super(id, position, images, resourceLimit, actionPeriod, animationPeriod);
        this.resourceCount = 0;
    }

    @Override
    public void executeActivity(
            WorldModel world,
            ImageStore imageStore,
            EventScheduler scheduler)
    {
        Optional<Entity> target =
                world.findNearest(getPosition(), new ArrayList<>(Arrays.asList(Tree.class, Sapling.class)));

        if (!target.isPresent() || !moveTo(world,
                target.get(),
                scheduler)
                || !transformNotFull(world, scheduler, imageStore))
        {
            scheduler.scheduleEvent(this,
                    Factory.createActivityAction(this, world, imageStore),
                    getActionPeriod());
        }
    }

    protected boolean transformNotFull(
            WorldModel world,
            EventScheduler scheduler,
            ImageStore imageStore)
    {
        if (resourceCount >= getResourceLimit()) {
            Entity miner = Factory.createDudeFull(getId(),
                    getPosition(), getActionPeriod(),
                    getAnimationPeriod(),
                    getResourceLimit(),
                    getImages());

            world.removeEntity(miner);
            scheduler.unscheduleAllEvents(miner);

            world.addEntity(miner);
            ((AnimatableEntity)miner).scheduleActions(scheduler, world, imageStore);

            return true;
        }

        return false;
    }

    @Override
    protected boolean moveTo(
            WorldModel world,
            Entity target,
            EventScheduler scheduler)
    {
        if (adjacent(target.getPosition())) {
            resourceCount += 1;
            ((Plant)target).decrementHealth(); // assume only plants are passed so no check needed
            return true;
        }
        else {
            return super.moveTo(world, target, scheduler);
        }
    }
}
