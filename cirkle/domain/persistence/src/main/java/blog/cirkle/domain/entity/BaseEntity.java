package blog.cirkle.domain.entity;

import blog.cirkle.domain.security.BlogUserDetails;
import blog.cirkle.domain.security.UserContextHolder;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.time.Instant;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.ColumnDefault;

@Getter
@Setter
@Entity
@Table(name = "objects")
@Inheritance(strategy = InheritanceType.JOINED)
@NoArgsConstructor
@SuperBuilder
public abstract class BaseEntity {
	@Id
	@ColumnDefault("uuid_generate_v4()")
	@Column(name = "id", nullable = false)
	@Builder.Default
	private UUID id = UUID.randomUUID();

	@NotNull @Column(name = "created_at", nullable = false)
	private Instant createdAt;

	@NotNull @Column(name = "updated_at", nullable = false)
	private Instant updatedAt;

	@NotNull @Column(name = "created_by", nullable = false)
	private UUID createdBy;

	@NotNull @Column(name = "updated_by", nullable = false)
	private UUID updatedBy;

	@PrePersist
	protected void onCreate() {
		createdAt = UserContextHolder.getNow().orElseGet(Instant::now);
		createdBy = UserContextHolder.getCurrentUser().map(BlogUserDetails::getId).orElse(null);
		updatedAt = createdAt;
		updatedBy = createdBy;
	}

	@PreUpdate
	protected void onUpdate() {
		updatedAt = UserContextHolder.getNow().orElseGet(Instant::now);
		updatedBy = UserContextHolder.getCurrentUser().map(BlogUserDetails::getId).orElse(null);
	}
}