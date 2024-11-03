package blog.cirkle.app.api.graphql.client.dsl;

import blog.cirkle.app.api.graphql.client.dsl.operand.ObjectOperand;
import blog.cirkle.app.api.graphql.client.dsl.operand.Operand;
import blog.cirkle.app.api.graphql.client.dsl.operand.PrimitiveOperand;
import blog.cirkle.app.api.graphql.client.dsl.operand.StringOperand;

public class DSL {
	public static Operation query() {
		return query("");
	}

	private static Operation query(String query) {
		return new Operation("query", query, false);
	}

	public static Operation mutation() {
		return mutation("");
	}

	private static Operation mutation(String query) {
		return new Operation("mutation", query, false);
	}

	public static PrimitiveOperand<Integer> intOp(String name) {
		return new PrimitiveOperand<>(name, Operand.OperandType.INT);
	}

	public static PrimitiveOperand<Long> longOp(String name) {
		return new PrimitiveOperand<Long>(name, Operand.OperandType.LONG);
	}

	public static PrimitiveOperand<Float> floatOp(String name) {
		return new PrimitiveOperand<>(name, Operand.OperandType.FLOAT);
	}

	public static PrimitiveOperand<Double> doubleOp(String name) {
		return new PrimitiveOperand<>(name, Operand.OperandType.DOUBLE);
	}

	public static StringOperand stringOp(String name) {
		return new StringOperand(name);
	}

	public static ObjectOperand objectOp(String name) {
		return new ObjectOperand(name);
	}

	public static Operation operation(String name) {
		return new Operation("", name, true);
	}
}
