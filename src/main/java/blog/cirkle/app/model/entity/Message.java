package blog.cirkle.app.model.entity;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.Set;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Getter
@Setter
@Entity
@Table(name = "messages")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Message {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "id", nullable = false)
	private Long id;

	@ManyToOne(optional = false)
	@JoinColumn(name = "sender_id", nullable = false)
	private User sender;

	@ManyToOne(optional = false)
	@JoinColumn(name = "receiver_id", nullable = false)
	private User receiver;

	@CreationTimestamp
	private Instant createdAt;

	@UpdateTimestamp
	private Instant updatedAt;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "message_images", joinColumns = @JoinColumn(name = "message_id"), inverseJoinColumns = @JoinColumn(name = "image_id"))
	@Builder.Default
	private Set<Image> images = new LinkedHashSet<>();

	@Column(name = "text_content", nullable = false)
	private String textContent;

	@OneToMany(orphanRemoval = true, fetch = FetchType.LAZY)
	@JoinTable(name = "message_reactions", joinColumns = @JoinColumn(name = "message_id"), inverseJoinColumns = @JoinColumn(name = "reaction_id"))
	@Builder.Default
	private Set<Reaction> reactions = new LinkedHashSet<>();
}