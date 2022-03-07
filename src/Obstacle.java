import processing.core.PImage;

import java.util.*;

public class Obstacle extends AnimatableEntity
{
    public Obstacle(
            String id,
            Point position,
            List<PImage> images,
            int animationPeriod
        )
    {
        super(id, position, images, animationPeriod);
    }
}
