import processing.core.PImage;

import java.util.*;

public class Fairy extends MovableEntity
{
    public Fairy(
            String id,
            Point position,
            List<PImage> images,
            int actionPeriod,
            int animationPeriod)
    {
        super(id, position, images, actionPeriod, animationPeriod);
    }

    @Override
    public void executeActivity(
            WorldModel world,
            ImageStore imageStore,
            EventScheduler scheduler)
    {
        Optional<Entity> fairyTarget =
                world.findNearest(getPosition(), new ArrayList<>(Arrays.asList(Stump.class)));


        if (fairyTarget.isPresent()) {
            Point tgtPos = fairyTarget.get().getPosition();

            if (moveTo(world, fairyTarget.get(), scheduler)) {
                Entity sapling = Factory.createSapling("sapling_" + getId(), tgtPos,
                        imageStore.getImageList(Functions.SAPLING_KEY));

                world.addEntity(sapling);
                ((AnimatableEntity)sapling).scheduleActions(scheduler, world, imageStore);
            }
        }

        scheduler.scheduleEvent(this,
                Factory.createActivityAction(this, world, imageStore),
                getActionPeriod());
    }

    @Override
    protected Point nextPosition(
            WorldModel world, Point destPos)
    {
//        int horiz = Integer.signum(destPos.getX() - getPosition().getX());
//        Point newPos = new Point(getPosition().getX() + horiz, getPosition().getY());
//
//        if (horiz == 0 || world.isOccupied(newPos)) {
//            int vert = Integer.signum(destPos.getY() - getPosition().getY());
//            newPos = new Point(getPosition().getX(), getPosition().getY() + vert);
//
//            if (vert == 0 || world.isOccupied(newPos)) {
//                newPos = getPosition();
//            }
//        }
//
//        return newPos;
//        PathingStrategy pathingStrategy = new SingleStepPathingStrategy();
        PathingStrategy pathingStrategy = new AStarPathingStrategy();
        List<Point> path = pathingStrategy.computePath(
                getPosition(),
                destPos,
                p -> !world.isOccupied(p) && world.withinBounds(p),
                (p1, p2) -> adjacent(p2, p1),
                PathingStrategy.CARDINAL_NEIGHBORS);
        return path.size() != 0 ? path.get(0) : getPosition();
    }

    @Override
    protected boolean moveTo(
            WorldModel world,
            Entity target,
            EventScheduler scheduler)
    {
        if (adjacent(target.getPosition())) {
            world.removeEntity(target);
            scheduler.unscheduleAllEvents(target);
            return true;
        }
        else {
            return super.moveTo(world, target, scheduler);
        }
    }
}
