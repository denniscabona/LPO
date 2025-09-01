package lab04;

public class Test {
	private static void checkAllShapes(Shape[] shapes, double totalArea, Shape max) {
		assert Shapes.area(shapes) == totalArea;
		assert Shapes.max(new AreaComparator(), shapes) == max;
	}

	private static void checkAllMovables(Movable[] movables, PointInterface[] anchors) {
		for (int i = 0; i < movables.length; i++)
			assert movables[i].getAnchorCopy().overlaps(anchors[i]);
	}

	public static void main(String[] args) {
		Circle c1 = new Circle(2, new Point(1, 1));
		Circle c2 = new Circle();
		Rectangle r = Rectangle.ofWidthHeightAnchor(1, 2, new Point(2, 2));
		Shape[] shapes = { c1, c2, r };
		Movable[] movables = { c1, c2, r };
		PointInterface[] anchorCopies = new Point[shapes.length];
		double totalArea = Shapes.area(shapes);
		Shapes.move(movables, -1, -1);
		for (int i = 0; i < anchorCopies.length; i++)
			anchorCopies[i] = movables[i].getAnchorCopy();
		Test.checkAllShapes(shapes, totalArea, c1);
		Test.checkAllMovables(movables, anchorCopies);
		Shapes.resize(shapes, 2.); // non deve spostare i centri delle figure!
		Test.checkAllShapes(shapes, totalArea * 4.0, c1);
		Test.checkAllMovables(movables, anchorCopies);
		for (Movable m : movables)
			m.getAnchorCopy().move(1, 1); // non deve spostare i punti 'anchor' delle figure!
		Test.checkAllShapes(shapes, totalArea * 4., c1);
		Test.checkAllMovables(movables, anchorCopies);
	}
}
