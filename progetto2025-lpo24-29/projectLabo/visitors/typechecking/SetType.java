package projectLabo.visitors.typechecking;

import static java.util.Objects.requireNonNull;

public record SetType(Type type) implements Type {

    public static final String TYPE_NAME = "SET";

	public Type getElemType(){
		return type;
	}

	@Override
	public String toString() {
		return String.format("Set<%s>", type);
	}

	@Override
	public SetType toSetType() {
		return this;
	}
}