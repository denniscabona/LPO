package projectLabo.visitors.typechecking;

import static java.util.Objects.requireNonNull;

public record SetType(Type type) implements Type {

    public static final String TYPE_NAME = "SET";

    public SetType{
        requireNonNull(type);
    }

	@Override
	public String toString() {
		return String.format("%s set", type);
	}

	@Override
	public SetType toSetType() {
		return this;
	}
}