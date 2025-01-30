package blog.cirkle.app.model.entity;

import java.time.Instant;

public interface TimeStamped {

	Instant getCreatedAt();

	void setCreatedAt(Instant createdAt);

	Instant getUpdatedAt();

	void setUpdatedAt(Instant updatedAt);

}
