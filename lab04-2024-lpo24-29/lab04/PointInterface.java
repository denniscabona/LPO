package lab04;

// points on the Cartesian plane

public interface PointInterface {
    public double getX(); // standard getter

    public double getY(); // standard getter

    public void move(double dx, double dy); // moves 'this' along the vector (dx,dy)

    public boolean overlaps(PointInterface p); // checks if 'this' and 'p' overlap
}
