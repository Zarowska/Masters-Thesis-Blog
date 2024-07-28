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
@Table(name = "comments")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Comment implements Authored, Media, Reactable {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "id", nullable = false)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "author_id")
	private User author;

	@CreationTimestamp
	private Instant createdAt;

	@UpdateTimestamp
	private Instant updatedAt;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "comment_images", joinColumns = @JoinColumn(name = "comment_id"), inverseJoinColumns = @JoinColumn(name = "image_id"))
	@Builder.Default
	private Set<Image> images = new LinkedHashSet<>();

	@Column(name = "text_content", nullable = false)
	private String textContent;

	@OneToMany(orphanRemoval = true, fetch = FetchType.LAZY)
	@JoinTable(name = "comment_reactions", joinColumns = @JoinColumn(name = "comment_id"), inverseJoinColumns = @JoinColumn(name = "reaction_id"))
	@Builder.Default
	private Set<Reaction> reactions = new LinkedHashSet<>();

	@ManyToOne(optional = false)
	@JoinColumn(name = "post_id", nullable = false)
	private Post post;

	@ManyToOne(optional = true)
	@JoinColumn(name = "parent_comment_id")
	private Comment parentComment;

}