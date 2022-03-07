public class Activity extends Action
{
    private final WorldModel world;
    private final ImageStore imageStore;

    public Activity(
            Entity entity,
            WorldModel world,
            ImageStore imageStore
    )
    {
        super(entity);
        this.world = world;
        this.imageStore = imageStore;
    }

    public void executeAction(EventScheduler scheduler) {
        ActivityEntity activityEntity = ((ActivityEntity) getEntity());
        activityEntity.executeActivity(world,
                    imageStore, scheduler);
    }
}
