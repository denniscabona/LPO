package lab04;

public interface ShapeComparator {
	
	/*
	 * compares shape1 with shape2
	 * returns 
	 * - a positive number if 'shape1' > 'shape2' 
	 * - a negative number if 'shape1' < 'shape2' 
	 * - 0 otherwise 
	 */
	
	int compare(Shape shape1, Shape shape2);
}
