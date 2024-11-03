package blog.cirkle.app.api.graphql.client.dsl.operand;

import org.jetbrains.annotations.Nullable;

public class StringOperand extends Operand<String> {
	public StringOperand(String name) {
		super(name, OperandType.STRING);
	}

	@Override
	protected @Nullable String prepareValue(Object value) {
		if (value instanceof String) {
			String preFinalValue = super.prepareValue(value);
			return preFinalValue == null ? (String) value : ("\"" + preFinalValue + "\"");
		}
		throw new IllegalArgumentException("Value is not a string: " + value);
	}
}
