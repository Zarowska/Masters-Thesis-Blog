package blog.cirkle.api.rest.client.utils;

import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Supplier;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Lazy<T> implements Supplier<T> {
	private final AtomicReference<T> valueRef = new AtomicReference<>();
	private final Supplier<T> builder;

	@Override
	public T get() {
		if (valueRef.get() == null) {
			synchronized (this) {
				if (valueRef.get() == null) {
					valueRef.set(builder.get());
				}
			}
		}
		return valueRef.get();
	}
}