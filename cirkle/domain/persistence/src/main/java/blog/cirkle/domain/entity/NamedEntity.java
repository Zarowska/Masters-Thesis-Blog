package blog.cirkle.domain.entity;

import java.util.UUID;

public interface NamedEntity<T> {

	UUID getId();

	String getName();
}
