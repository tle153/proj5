import processing.core.PImage;

import java.util.List;

public abstract class DudeEntity extends MovableEntity
{
    private final int resourceLimit;

    public DudeEntity(
            String id,
            Point position,
            List<PImage> images,
            int resourceLimit,
            int actionPeriod,
            int animationPeriod
    )
    {
        super(id, position, images, actionPeriod, animationPeriod);
        this.resourceLimit = resourceLimit;
    }

    protected int getResourceLimit()
    {
        return resourceLimit;
    }

    @Override
    protected Point nextPosition(
            WorldModel world, Point destPos)
    {
//        int horiz = Integer.signum(destPos.getX() - getPosition().getX());
//        Point newPos = new Point(getPosition().getX() + horiz, getPosition().getY());
//
//        if (horiz == 0 || world.isOccupied(newPos) && world.getOccupancyCell(newPos).getClass() != Stump.class) { // double sus
//            int vert = Integer.signum(destPos.getY() - getPosition().getY());
//            newPos = new Point(getPosition().getX(), getPosition().getY() + vert);
//
//            if (vert == 0 || world.isOccupied(newPos) && world.getOccupancyCell(newPos).getClass() != Stump.class) {
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
                p -> (!world.isOccupied(p) || world.getOccupancyCell(p).getClass() == Stump.class) && world.withinBounds(p),
                (p1, p2) -> adjacent(p2, p1),
                PathingStrategy.CARDINAL_NEIGHBORS);
        return path.size() != 0 ? path.get(0) : getPosition();
    }
}
