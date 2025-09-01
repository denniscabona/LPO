package lab04;

// objects movable on the Cartesian plane through an anchor point

public interface Movable {

	PointInterface getAnchorCopy(); // returns a copy of the anchor point of 'this'

	void move(double dx, double dy); // moves 'this' along the vector (dx,dy)

}
