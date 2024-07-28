package blog.cirkle.app.utils;

public interface UnsafeConsumer<T> {
	void accept(T t) throws Exception;
}
