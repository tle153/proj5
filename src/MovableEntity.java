import processing.core.PImage;

import java.util.List;
import java.util.Optional;

public abstract class MovableEntity extends ActivityEntity
{
    public MovableEntity(
            String id,
            Point position,
            List<PImage> images,
            int actionPeriod,
            int animationPeriod)
    {
        super(id, position, images, actionPeriod, animationPeriod);
    }

    protected boolean adjacent(Point p)
    {
        return (getPosition().getX() == p.getX() && Math.abs(getPosition().getY() - p.getY()) == 1) || (getPosition().getY() == p.getY()
                && Math.abs(getPosition().getX() - p.getX()) == 1);
    }

    protected boolean adjacent(Point p1, Point p2)
    {
        return (p1.getX() == p2.getX() && Math.abs(p1.getY() - p2.getY()) == 1) || (p1.getY() == p2.getY()
                && Math.abs(p1.getX() - p2.getX()) == 1);
    }

    protected abstract Point nextPosition(
            WorldModel world, Point destPos);

    protected boolean moveTo(
            WorldModel world,
            Entity target,
            EventScheduler scheduler) {
        Point nextPos = nextPosition(world, target.getPosition());

        if (!getPosition().equals(nextPos)) {
            Optional<Entity> occupant = world.getOccupant(nextPos);
            if (occupant.isPresent()) {
                scheduler.unscheduleAllEvents(occupant.get());
            }

            world.moveEntity(this, nextPos);
        }
        return false;
    }
}
