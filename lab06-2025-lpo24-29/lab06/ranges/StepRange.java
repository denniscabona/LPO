package lab06.ranges;

public class StepRange extends Range {

	private final int step;

	// iniziallizza una sequenza da start (incluso) a end (escluso) con step != 0
	protected StepRange(int start, int end, int step) {
		super(start, end);
		if(step == 0)
			throw new IllegalArgumentException("Step non deve essere 0");
		this.step = step;
	}

	// restituisce una nuova sequenza da start (incluso) a end (escluso) con step !=
	// 0
	public static StepRange withStartEndStep(int start, int end, int step) {
		return new StepRange(start, end, step);
	}

	// restituisce una nuova sequenza da start (incluso) a end (escluso) con step ==
	// 1
	public static StepRange withStartEnd(int start, int end){
		return new StepRange(start, end, 1);
	}

	// restituisce una nuova sequenza da 0 (incluso) a end (escluso) con step == 1
	public static Range withEnd(int end) {
		return new StepRange(0, end, 1);
	}

	// restituisce il numero di elementi della sequenza
	@Override
	public int size() {
		if((end - start) * Math.signum(step) <= 0) // non posso avere size e step di segni opposti  es. 1 3 5 7 9 va bene solo con step = 2, non con step = -2
			return 0;
		return ((end - start)/step + ((end - start) % step == 0 ? 0 : 1)); // se size è divisibile per step allora nessun problema  es. 1 3 5 7 9 step = 2 ottengo 8/2 = 4  (start = 1 incl, end = 9 escl)
		// se size non è divisibile per step allora aggiungo 1  es. 1 3 5 7 9 step = 2 ottengo 9/2 = 4 + 1 (da return) (start = 1 incl, end = 10 escl, ma il 9 è presente)
	}

	// restituisce un nuovo iteratore dell'oggetto this
	@Override
	public StepRangeIterator iterator() {
		return new StepRangeIterator(start, end, step);
	}

}
