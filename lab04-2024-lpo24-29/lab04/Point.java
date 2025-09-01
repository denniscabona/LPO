package lab04;

public class Point implements PointInterface {
	private double x;
	private double y;

	public Point() {

	}

	public Point(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public Point(PointInterface p) {
		this(p.getX(), p.getY());
	}

	public double getX() {
		return this.x;
	}

	public double getY() {
		return this.y;
	}

	public void move(double dx, double dy) {
		this.x += dx;
		this.y += dy;
	}

	public boolean overlaps(PointInterface p) {
		return this.x == p.getX() && this.y == p.getY();
	}
}