package blog.cirkle.app.model.entity;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Getter
@Setter
@Entity
@Table(name = "post")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Post implements Authored, Media, Reactable {
	@Id
	@Column(name = "id", nullable = false)
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	@ManyToOne
	@JoinColumn(name = "author_id")
	private User author;

	@ManyToOne(optional = false)
	@JoinColumn(name = "space_id", nullable = false)
	private Participant space;

	@CreationTimestamp
	private Instant createdAt;

	@UpdateTimestamp
	private Instant updatedAt;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "post_images", joinColumns = @JoinColumn(name = "post_id"), inverseJoinColumns = @JoinColumn(name = "image_id"))
	@Builder.Default
	private Set<Image> images = new LinkedHashSet<>();

	@Column(name = "text_content", nullable = false)
	private String textContent;

	@OneToMany(orphanRemoval = true, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "post_reactions", joinColumns = @JoinColumn(name = "post_id"), inverseJoinColumns = @JoinColumn(name = "reaction_id"))
	@Builder.Default
	private Set<Reaction> reactions = new LinkedHashSet<>();

	@OneToMany(mappedBy = "post", orphanRemoval = true)
	@Builder.Default
	private Set<Comment> comments = new LinkedHashSet<>();
}