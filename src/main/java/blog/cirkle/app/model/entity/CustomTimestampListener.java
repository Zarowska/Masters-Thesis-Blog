package blog.cirkle.app.model.entity;

import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import java.time.Duration;
import java.time.Instant;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import org.springframework.stereotype.Component;

@Component
public class CustomTimestampListener {

	private static AtomicReference<Instant> currentTime = new AtomicReference<>();

	public static void setCurrentTime(Instant currentTime) {
		CustomTimestampListener.currentTime.set(currentTime);
	}

	public static Instant getCurrentTime() {
		return currentTime.get();
	}

	public static void advance(Duration duration) {
		if (currentTime.get() != null) {
			currentTime.set(currentTime.get().plus(duration));
		}
	}

	private Instant currentTime() {
		return Optional.ofNullable(currentTime.get()).orElseGet(Instant::now);
	}

	@PrePersist
	public void setCreationTimestamp(Object entity) {
		if (entity instanceof TimeStamped timestampedEntity) {
			timestampedEntity.setCreatedAt(currentTime()); // Customize this logic as needed
		}
	}

	@PreUpdate
	public void setUpdateTimestamp(Object entity) {
		if (entity instanceof TimeStamped timestampedEntity) {
			timestampedEntity.setUpdatedAt(currentTime()); // Customize this logic as needed
		}
	}
}