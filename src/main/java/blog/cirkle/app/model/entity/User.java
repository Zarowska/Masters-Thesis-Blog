package blog.cirkle.app.model.entity;

import jakarta.persistence.*;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.proxy.HibernateProxy;

@Getter
@Setter
@Entity
@Table(name = "users")
@NoArgsConstructor
public class User extends Participant {

	@Embedded
	private UserProfile profile = new UserProfile();

	@Column(name = "email", nullable = false, unique = true)
	private String email;
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "friends", joinColumns = @JoinColumn(name = "user_1_id"), inverseJoinColumns = @JoinColumn(name = "users_2_id"))

	private Set<User> friends = new LinkedHashSet<>();

	@OneToMany(mappedBy = "sender", orphanRemoval = true, fetch = FetchType.LAZY)
	private Set<ParticipantRequest> sentRequests = new LinkedHashSet<>();

	@Column(name = "password_hash")
	private String passwordHash = UUID.randomUUID().toString();

	public User(String email) {
		this.email = email;
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
		User user = (User) o;
		return getId() != null && Objects.equals(getId(), user.getId());
	}

	@Override
	public final int hashCode() {
		return this instanceof HibernateProxy
				? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode()
				: getClass().hashCode();
	}
}