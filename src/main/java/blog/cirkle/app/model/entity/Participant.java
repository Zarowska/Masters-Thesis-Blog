package blog.cirkle.app.model.entity;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Getter
@Setter
@Entity
@Table(name = "participants")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Participant {
	@Id
	@Column(name = "id", nullable = false)
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "followers", joinColumns = @JoinColumn(name = "participant_id"), inverseJoinColumns = @JoinColumn(name = "users_id"))
	private Set<User> followers = new LinkedHashSet<>();

	@OneToMany(mappedBy = "receiver", orphanRemoval = true, fetch = FetchType.LAZY)
	private Set<ParticipantRequest> receivedRequests = new LinkedHashSet<>();

	@CreationTimestamp
	private Instant createdAt;

	@UpdateTimestamp
	private Instant updatedAt;

	public abstract Profile getProfile();
}