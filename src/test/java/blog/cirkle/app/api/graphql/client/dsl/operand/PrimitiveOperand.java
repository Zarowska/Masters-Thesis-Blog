package blog.cirkle.app.api.graphql.client.dsl.operand;

public class PrimitiveOperand<T> extends Operand<T> {
	public PrimitiveOperand(String name, OperandType type) {
		super(name, type);
	}
}
