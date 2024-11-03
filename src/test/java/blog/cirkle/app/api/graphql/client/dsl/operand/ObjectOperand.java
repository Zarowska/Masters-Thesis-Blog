package blog.cirkle.app.api.graphql.client.dsl.operand;

import static blog.cirkle.app.api.graphql.client.dsl.DSL.*;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import org.jetbrains.annotations.Nullable;

public class ObjectOperand extends Operand<Map<String, Operand>> {
	public ObjectOperand(String name) {
		super(name, OperandType.OBJECT);
	}

	@Override
	protected @Nullable String prepareValue(Object values) {
		if (value == null && this.value == null) {
			return null;
		}
		if (!(value instanceof Map)) {
			throw new ClassCastException(String.format("Value %s is not a map", value));
		}
		Map<String, Object> defaults = (Map<String, Object>) values;

		StringBuilder sb = new StringBuilder();
		sb.append("{\n");
		sb.append(value.entrySet().stream().map(entry -> {
			StringBuilder local = new StringBuilder();
			Object dValue;
			if (entry.getValue().getType() == OperandType.OBJECT) {
				dValue = defaults.getOrDefault(entry.getValue().getAlias(), defaults);
			} else {
				dValue = defaults.getOrDefault(entry.getKey(), entry.getValue().getValue());
			}
			String fValue = entry.getValue().build(dValue);
			String[] lines = fValue.split("\n");
			for (int i = 0; i < lines.length; i++) {
				if (i == 0 || i == lines.length - 1) {
					local.append("  ");
				} else {
					local.append("  ");
					local.append("  ");
				}
				local.append(lines[i]);
				if (i != lines.length - 1) {
					local.append("\n");
				}
			}
			return local.toString();
		}).collect(Collectors.joining(",\n")));
		sb.append("\n}");
		return sb.toString();
	}

	public ObjectOperand append(Operand operand) {
		if (this.value == null) {
			this.value = new HashMap<>();
		}
		this.value.put(operand.getName(), operand);
		return this;
	}

	public ObjectOperand append(String key, int value) {
		return append(intOp(key).withValue(value));
	}

	public ObjectOperand append(String key, long value) {
		return append(longOp(key).withValue(value));
	}

	public ObjectOperand append(String key, double value) {
		return append(doubleOp(key).withValue(value));
	}

	public ObjectOperand append(String key, float value) {
		return append(floatOp(key).withValue(value));
	}

	public ObjectOperand append(String key, String value) {
		if (value == null) {
			throw new IllegalArgumentException("Value cannot be null");
		}
		return append(stringOp(key).withValue(value));
	}
}
