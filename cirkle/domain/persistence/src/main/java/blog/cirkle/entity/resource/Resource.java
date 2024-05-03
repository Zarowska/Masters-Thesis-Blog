package blog.cirkle.entity.resource;

import blog.cirkle.entity.BaseEntity;
import blog.cirkle.entity.participant.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.util.LinkedHashSet;
import java.util.Set;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Getter
@Setter
@Entity
@Table(name = "resources")
public abstract class Resource extends BaseEntity {

	@NotNull @ManyToOne(fetch = FetchType.LAZY, optional = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JoinColumn(name = "author_id", nullable = false)
	private User author;

	@ManyToMany(mappedBy = "referredBy", fetch = FetchType.LAZY)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@Setter(AccessLevel.PRIVATE)
	protected Set<Resource> content = new LinkedHashSet<>();

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "resources_resources", joinColumns = @JoinColumn(name = "resource_id"), inverseJoinColumns = @JoinColumn(name = "referred_resources_id"))
	@OnDelete(action = OnDeleteAction.CASCADE)
	@Setter(AccessLevel.PRIVATE)
	protected Set<Resource> referredBy = new LinkedHashSet<>();
}