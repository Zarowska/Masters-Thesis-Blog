package blog.cirkle.app.model.entity;

import jakarta.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "groups")
public class Group extends Participant {

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "groups_members", joinColumns = @JoinColumn(name = "groups_id"), inverseJoinColumns = @JoinColumn(name = "members_id"))
	private Set<User> members = new LinkedHashSet<>();

	@Column(name = "is_private", nullable = false)
	private Boolean isPrivate = true;

	@Embedded
	private GroupProfile profile;
}