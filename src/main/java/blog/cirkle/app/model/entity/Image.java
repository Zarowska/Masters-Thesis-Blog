package blog.cirkle.app.model.entity;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.proxy.HibernateProxy;

@Getter
@Setter
@Entity
@Table(name = "images")
@NoArgsConstructor
public class Image implements Reactable {
	@Id
	@Column(name = "id", nullable = false)
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "owner_id", nullable = false)
	private Participant owner;

	@Column(name = "mime_type", nullable = false)
	private String mimeType;

	@Column(name = "size", nullable = false)
	private Integer size;

	@Column(name = "content")
	@Basic(fetch = FetchType.LAZY)
	private byte[] content;

	@CreationTimestamp
	private Instant createdAt;

	@OneToMany(orphanRemoval = true, fetch = FetchType.LAZY)
	@JoinTable(name = "image_reactions", joinColumns = @JoinColumn(name = "image_id"), inverseJoinColumns = @JoinColumn(name = "reaction_id"))
	private Set<Reaction> reactions = new LinkedHashSet<>();

	public Image(Participant owner, String mimeType, byte[] content) {
		this.owner = owner;
		this.mimeType = mimeType;
		this.content = content;
		this.size = content.length;
	}

	@Override
	public final boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null)
			return false;
		Class<?> oEffectiveClass = o instanceof HibernateProxy
				? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass()
				: o.getClass();
		Class<?> thisEffectiveClass = this instanceof HibernateProxy
				? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass()
				: this.getClass();
		if (thisEffectiveClass != oEffectiveClass)
			return false;
		Image image = (Image) o;
		return getId() != null && Objects.equals(getId(), image.getId());
	}

	@Override
	public final int hashCode() {
		return this instanceof HibernateProxy
				? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode()
				: getClass().hashCode();
	}
}