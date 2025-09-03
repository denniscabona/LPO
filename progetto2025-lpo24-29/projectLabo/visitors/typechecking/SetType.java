package projectLabo.visitors.typechecking;

public record SetType(Type type) implements Type {

    public static final String TYPE_NAME = "SET";

	public Type ElemType(){
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