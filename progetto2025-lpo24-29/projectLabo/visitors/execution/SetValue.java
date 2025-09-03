package projectLabo.visitors.execution;

import static java.util.Objects.requireNonNull;

import java.util.HashSet;
import java.util.Set;

public record SetValue(Set<Value> values) implements Value {

	public SetValue {
		requireNonNull(values);
		values = new HashSet<>(values);
	}

	@Override
	public SetValue toSet() {
		return this;
	}

	@Override
	public String toString() {
		return String.format("{%s}", values);
	}

}
