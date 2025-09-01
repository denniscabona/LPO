package projectLabo.visitors.typechecking;

public interface Type {
	default void checkEqual(Type found) {
		if (!equals(found))
			throw new TypecheckerException(found.toString(), toString());
	}

	default PairType toPairType() {
		throw new TypecheckerException(toString(), PairType.TYPE_NAME);
	}

}
