package lab06.ranges;

import java.util.NoSuchElementException;

class StepRangeIterator extends RangeIterator {

	private final int step;

	StepRangeIterator(int next, int end, int step) {
		super(next, end);
		this.step = step;
	}

	@Override
	public boolean hasNext() {
		return step > 0 ? next < end : next > end;
	}

	@Override
	public Integer next() {
		if(!hasNext())
			throw new NoSuchElementException("Non esiste un elemento successivo");
		int temp  = next;
		next += step;
		return temp;
	}
}
