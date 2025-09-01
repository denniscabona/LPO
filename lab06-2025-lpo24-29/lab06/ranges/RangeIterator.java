package lab06.ranges;

import java.util.Iterator;
import java.util.NoSuchElementException;

class RangeIterator implements Iterator<Integer> {

	protected int next;
	protected final int end;

	RangeIterator(int next, int end) {
		this.next = next;
		this.end = end;
	}

	@Override
	public boolean hasNext() {
		return next < end;
	}

	@Override
	public Integer next() {
		if(!hasNext())
			throw new NoSuchElementException("Non esiste un elemento successivo");
		return next++;
	}

}
