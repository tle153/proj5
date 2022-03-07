import java.util.*;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class AStarPathingStrategy
        implements PathingStrategy
{


    public List<Point> computePath(Point start, Point end,
                                   Predicate<Point> canPassThrough,
                                   BiPredicate<Point, Point> withinReach,
                                   Function<Point, Stream<Point>> potentialNeighbors)
    {
        List<Point> path = new LinkedList<Point>();
        HashMap<Point, PathingNode> openList = new HashMap<>();
        Comparator<PathingNode> comp = Comparator.comparing(PathingNode::getFVal);
        PriorityQueue<PathingNode> openQueue = new PriorityQueue<PathingNode>(comp);
        HashMap<Point, PathingNode> closedList = new HashMap<>();
        PathingNode current = new PathingNode(start, null);
        openList.put(current.getPos(), current);
        openQueue.add(current);

        while(current != null) {
//            System.out.println(current);
            if(withinReach.test(current.getPos(), end)) { // found
                while(current.getPriorNode() != null) { // create path list
                    path.add(0, current.getPos());
                    current = current.getPriorNode();
                }
                return path;
            } else {
                List<Point> neighbors = potentialNeighbors.apply(current.getPos())
                        .filter(canPassThrough)
                        .filter(p -> !closedList.containsKey(p))
                        .collect(Collectors.toList());
                for (Point adj : neighbors) { // add adjacent to open list
                    PathingNode newNode = new PathingNode(adj, current);
                    if (!openList.containsKey(adj)) {
                        openList.put(adj, newNode);
                        newNode.setHVal(newNode.getPos().distance(end));
                        openQueue.add(newNode);
                    } else {
                        if(openList.get(adj).getGVal() > newNode.getGVal()) { // assume node already in openList
                            PathingNode oldNode = openList.get(adj);
                            oldNode.setPriorNode(current);
                            oldNode.setHVal(newNode.getPos().distance(end));
                            oldNode.setGVal(newNode.getGVal());
                        }
                    }
                }
                closedList.put(current.getPos(), current);
                current = openQueue.poll();
            }
        }

        return path;
    }
}
