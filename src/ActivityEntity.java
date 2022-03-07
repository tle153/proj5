import processing.core.PImage;

import java.util.List;

public abstract class ActivityEntity extends AnimatableEntity
{
    private final int actionPeriod;

    public ActivityEntity(String id, Point position, List<PImage> images, int actionPeriod, int animationPeriod) {
        super(id, position, images, animationPeriod);
        this.actionPeriod = actionPeriod;
    }

    abstract public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler);

    protected int getActionPeriod()
    {
        return actionPeriod;
    }

    @Override
    public void scheduleActions(
            EventScheduler scheduler,
            WorldModel world,
            ImageStore imageStore)
    {
        scheduler.scheduleEvent(this,
                Factory.createActivityAction(this, world, imageStore),
                getActionPeriod());
        super.scheduleActions(scheduler, world, imageStore);
    }
}