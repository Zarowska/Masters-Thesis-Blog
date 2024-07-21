package blog.cirkle.domain.entity;

import blog.cirkle.domain.security.BlogUserDetails;
import blog.cirkle.domain.security.UserContextHolder;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.time.Instant;
import java.util.UUID;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@Entity
@Table(name = "objects")
@Inheritance(strategy = InheritanceType.JOINED)
@NoArgsConstructor
@SuperBuilder
@Accessors(chain = true)
@DiscriminatorColumn(name = "entity_type", discriminatorType = DiscriminatorType.STRING)
public abstract class BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(name = "id", nullable = false)
	private UUID id = null;

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