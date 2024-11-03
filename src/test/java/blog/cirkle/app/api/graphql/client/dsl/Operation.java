package blog.cirkle.app.api.graphql.client.dsl;

import static blog.cirkle.app.api.graphql.client.dsl.DSL.*;

import blog.cirkle.app.api.graphql.client.dsl.operand.ObjectOperand;
import blog.cirkle.app.api.graphql.client.dsl.operand.Operand;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Operation {
	private final String type;
	private final String name;
	private final boolean allowArguments;

	private final List<Operand> agruments = new ArrayList<>();
	private final List<Operation> fields = new ArrayList<>();

	public Operation(String type, String name) {
		this(type, name, true);
	}

	public Operation(String type, String name, boolean allowArguments) {
		this.type = type;
		this.name = name;
		this.allowArguments = allowArguments;
	}

	public String build() {
		return this.build(Map.of());
	}

	public Operation withArgument(String name, Integer value) {
		return withArgument(intOp(name).withValue(value));
	}

	public Operation withArgument(String name, Long value) {
		return withArgument(longOp(name).withValue(value));
	}

	public Operation withArgument(String name, Float value) {
		return withArgument(floatOp(name).withValue(value));
	}

	public Operation withArgument(String name, Double value) {
		return withArgument(doubleOp(name).withValue(value));
	}

	public Operation withArgument(String name, String value) {
		return withArgument(stringOp(name).withValue(value));
	}

	public Operation withArgument(String name, Map<String, Operand> value) {
		return withArgument(objectOp(name).withValue(value));
	}

	public Operation withArguments(Operand... values) {
		for (Operand value : values) {
			withArgument(value);
		}
		return this;
	}

	public Operation withArgument(Operand value) {
		if (!allowArguments) {
			throw new IllegalArgumentException("Argument is not allowed here");
		}
		agruments.add(value);
		return this;
	}

	public Operation withFields(String... fields) {
		for (String field : fields) {
			this.fields.add(new Operation("", field));
		}
		return this;
	}

	public Operation withField(Operation op) {
		this.fields.add(op);
		return this;
	}

	public String build(Map<String, Object> values) {
		StringBuilder builder = new StringBuilder();
		if (type != null && !type.isEmpty()) {
			builder.append(type).append(" ");
		}
		builder.append(name);
		if (!agruments.isEmpty()) {
			builder.append("(\n");
			ObjectOperand args = objectOp("args");
			agruments.forEach(arg -> args.append(arg));
			String fValue = args.build(values);
			fValue = fValue.substring(fValue.indexOf('\n') + 1, fValue.lastIndexOf('}'));
			String[] lines = fValue.split("\n");
			for (int i = 0; i < lines.length; i++) {
				builder.append("  ").append(lines[i]);
				if (i != lines.length - 1) {
					builder.append("\n");
				}
			}
			builder.append("\n)");
		}

		if (!fields.isEmpty()) {
			builder.append("{\n");
			fields.forEach(field -> {
				String fValue = field.build(values);
				Arrays.stream(fValue.split("\n")).forEach(line -> builder.append("    ").append(line).append("\n"));
			});
			builder.append("}");
		}
		return builder.toString();
	}

	public static void main(String[] args) {
		Operation operation = mutation().withField(new Operation("", "resetPassword")
				.withArgument(objectOp("input").append(stringOp("passwordResetToken")).append(stringOp("password")))
				.withFields("token"));

		System.out.println(operation.build(Map.of("passwordResetToken", "12345", "password", "password")));
	}

	public static Operation field(String name) {
		return new Operation("", name);
	}
}
