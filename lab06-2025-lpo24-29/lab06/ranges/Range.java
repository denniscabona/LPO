package lab06.ranges;

public class Range implements RangeInterface {

	// completare
	protected final int start, end;

	// iniziallizza una sequenza da start (incluso) a end (escluso)
	protected Range(int start, int end) {
		this.start = start;
		this.end = end;
	}

	// restituisce una nuova sequenza da start (incluso) a end (escluso)
	public static Range withStartEnd(int start, int end) {
		return new Range(start, end);
	}

	// restituisce una nuova sequenza da 0 (incluso) a end (escluso)
	public static Range withEnd(int end) {
		return new Range(0, end);
	}

	// restituisce il numero di elementi della sequenza
	@Override
	public int size() {
		return end > start ? end - start : 0;
	}

	// restituisce un nuovo iteratore dell'oggetto this
	@Override
	public RangeIterator iterator() {
		return new RangeIterator(start, end);
	}

}
