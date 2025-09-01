package lab06;

import java.util.Iterator;

import lab06.ranges.*;

public class RangeUtility {

	public static int scalar(RangeInterface r1, RangeInterface r2) {
		if(r1.size() != r2.size()) 
			throw new IllegalArgumentException("Le dimensioni dei vettori devono coincidere");
		int scalar = 0;
		Iterator<Integer> it1 = r1.iterator();
		Iterator<Integer> it2 = r2.iterator();
		while(it1.hasNext() && it2.hasNext()){
			var t1 = it1.next();
			var t2 = it2.next();
			scalar += t1*t2;
			System.out.println("\n" + t1 + "\t" + scalar + "\t" + t2 + "\n");
		}
		return scalar;
	}

	private static void testRange() {
		RangeInterface r0 = Range.withEnd(-1);
		assert r0.size() == 0;
		RangeInterface r1 = Range.withStartEnd(1, 4);
		assert r1.size() == 3;
		assert scalar(r1, r1) == 14;
		assert scalar(Range.withEnd(0), r0) == 0;
		assert scalar(r1, Range.withStartEnd(2, 5)) == 20;
	}

	private static void testStepRange() {
		RangeInterface r0 = StepRange.withStartEndStep(1, -1, 3);
		assert r0.size() == 0;
		RangeInterface r1 = StepRange.withStartEnd(1, 4);
		assert r1.size() == 3;
		assert scalar(r1, StepRange.withStartEndStep(4, 1, -1)) == 16;
		assert scalar(r1, StepRange.withStartEndStep(2, 7, 2)) == 28;
		assert scalar(StepRange.withStartEndStep(1, -11, -3), StepRange.withStartEndStep(1, 13, 3)) == -122;
		assert scalar(StepRange.withStartEndStep(1, 11, -3), r0) == 0;
	}

	public static void main(String[] args) {
		testRange();
		testStepRange();
	}
}
