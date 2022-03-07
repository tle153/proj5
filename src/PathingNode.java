import java.nio.file.Path;
import java.util.Objects;

public class PathingNode
{
    private Point pos;
    private double gVal;
    private double hVal;
    private double fVal;
    private PathingNode priorNode;

    public PathingNode(Point p, PathingNode prior)
    {
        pos = p;
        priorNode = prior;
        gVal = calcGVal();
    }

    public Point getPos() {
        return pos;
    }

    private double calcGVal() {
        if(priorNode == null) return 1;
        return 1 + priorNode.gVal;
    }

    public double getGVal()
    {
        return gVal;
    }

    public void setGVal(double gVal) {
        this.gVal = gVal;
    }

    public void setHVal(double hVal) {
        this.hVal = hVal;
    }

    public double getFVal() {
        return gVal+hVal;
    }

    public PathingNode getPriorNode() {
        return priorNode;
    }

    public void setPriorNode(PathingNode node)
    {
        priorNode = node;
    }

    @Override
    public boolean equals(Object o)
    {
        if(o != null && o.getClass() == this.getClass()) {
            PathingNode node = (PathingNode) o;
            return Objects.equals(pos, node.pos) &&
                    Objects.equals(gVal, node.gVal) &&
                    Objects.equals(hVal, node.hVal) &&
                    Objects.equals(fVal, node.fVal) &&
                    Objects.equals(priorNode, node.priorNode);
        }
        return false;
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(pos, gVal, hVal, fVal, priorNode);
    }

    @Override
    public String toString()
    {
        return getPos().toString();
    }
}