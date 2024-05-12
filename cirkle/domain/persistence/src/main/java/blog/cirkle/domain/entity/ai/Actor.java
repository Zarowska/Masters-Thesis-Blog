package blog.cirkle.domain.entity.ai;

import blog.cirkle.domain.entity.BaseEntity;
import blog.cirkle.domain.entity.participant.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Getter
@Setter
@Entity
@Table(name = "actor")
public class Actor extends BaseEntity {

	@NotNull @Column(name = "accept_friendship_probability", nullable = false)
	private Integer acceptFriendshipProbability;

	@NotNull @Column(name = "comment_probability", nullable = false)
	private Integer commentProbability;

	@NotNull @Column(name = "follow_probability", nullable = false)
	private Integer followProbability;

	@NotNull @Column(name = "friend_probability", nullable = false)
	private Integer friendProbability;

	@NotNull @Column(name = "like_probability", nullable = false)
	private Integer likeProbability;

	@NotNull @Column(name = "\"new post_probability\"", nullable = false)
	private Integer newPostProbability;

	@NotNull @Column(name = "repost_probability", nullable = false)
	private Integer repostProbability;

	@Column(name = "last_active")
	private Instant lastActive;

	@NotNull @Column(name = "registration_timestamp", nullable = false)
	private Instant registrationTimestamp;

	@OneToOne(fetch = FetchType.LAZY)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JoinColumn(name = "user_id")
	private User user;

	@NotNull @Column(name = "avatar_url", nullable = false, length = Integer.MAX_VALUE)
	private String avatarUrl;

	@NotNull @Column(name = "email", nullable = false, length = Integer.MAX_VALUE)
	private String email;

	@NotNull @Column(name = "first_name", nullable = false, length = Integer.MAX_VALUE)
	private String firstName;

	@NotNull @Column(name = "last_name", nullable = false, length = Integer.MAX_VALUE)
	private String lastName;

	@NotNull @Column(name = "password", nullable = false, length = Integer.MAX_VALUE)
	private String password;

	@NotNull @Column(name = "time_zone", nullable = false, length = Integer.MAX_VALUE)
	private String timeZone;

	@ElementCollection
	@Column(name = "topic")
	@CollectionTable(name = "actor_topics", joinColumns = @JoinColumn(name = "owner_id"))
	private Set<String> topics = new LinkedHashSet<>();

}