package projectLabo.visitors.execution;

import static java.util.Objects.requireNonNull;

public record SetValue(Value value) implements Value {

	public SetValue {
		requireNonNull(value);
	}

	@Override
	public SetValue toSet() {
		return this;
	}

	@Override
	public String toString() {
		return String.format("Set<%s>", value);
	}

}
