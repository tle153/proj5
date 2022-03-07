import java.util.*;

import processing.core.PImage;

/**
 * An entity that exists in the world. See EntityKind for the
 * different kinds of entities that exist.
 */
public abstract class Entity
{
    private final String id;
    private Point position;
    private final List<PImage> images;
    private int imageIndex;

    public Entity(
            String id,
            Point position,
            List<PImage> images)
    {
        this.id = id;
        this.position = position;
        this.images = images;
        this.imageIndex = 0;
    }

    public String getId()
    {
        return id;
    }

    public Point getPosition()
    {
        return position;
    }

    public void setPosition(Point p)
    {
        position = p;
    }

    public PImage getCurrentImage()
    {
        return getImages().get(imageIndex);
    }

    protected List<PImage> getImages()
    {
        return images;
    }

    protected int getImageIndex()
    {
        return imageIndex;
    }

    protected void setImageIndex(int i)
    {
        imageIndex = i;
    }
}
