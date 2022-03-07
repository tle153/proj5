public class Animation extends Action
{
    private final int repeatCount;

    public Animation(
            Entity entity,
            int repeatCount)
    {
        super(entity);
        this.repeatCount = repeatCount;
    }

    public void executeAction(EventScheduler scheduler) {
        AnimatableEntity animatableEntity = ((AnimatableEntity) getEntity());
        animatableEntity.nextImage();

        if (repeatCount != 1) {
            scheduler.scheduleEvent(animatableEntity,
                    Factory.createAnimationAction(animatableEntity, Math.max(repeatCount - 1,
                            0)),
                    animatableEntity.getAnimationPeriod());
        }
    }
}
