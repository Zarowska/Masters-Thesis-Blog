package blog.cirkle.app.api.graphql.client.dsl.operand;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.jetbrains.annotations.Nullable;

@RequiredArgsConstructor
public abstract class Operand<T> {
	@Getter
	protected final String name;

	@Getter
	protected final OperandType type;

	@Getter
	@Setter
	protected T value;

	@Getter
	protected boolean nullable = false;

	protected String alias;

	public enum OperandType {
		INT, LONG, FLOAT, DOUBLE, STRING, OBJECT
	}

	public Operand<T> nullable() {
		this.nullable = true;
		return this;
	}

	public Operand<T> withValue(T value) {
		this.value = value;
		return this;
	}

	public Operand<T> withAlias(String alias) {
		this.alias = alias;
		return this;
	}

	public String getAlias() {
		return alias == null ? name : alias;
	}

	public String build() {
		return build(null);
	}

	public String build(Object value) {
		String finalValue = prepareValue(value);
		if (finalValue == null && !nullable) {
			throw new IllegalArgumentException("Operand %s must have non-null %s value".formatted(name, type));
		}
		return String.format("%s: %s", name, finalValue);
	}

	protected @Nullable String prepareValue(Object value) {
		Object finalValue = this.value;
		if (value != null) {
			finalValue = value;
		}
		return finalValue == null ? null : finalValue.toString();
	}
}
