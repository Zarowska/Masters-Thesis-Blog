package blog.cirkle.app.model.entity;

import jakarta.persistence.*;
import java.util.UUID;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "password_change_requests")
@NoArgsConstructor
public class PasswordChangeRequest {

	@Id
	@Column(name = "id", nullable = false)
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	@Column(name = "request_id")
	private UUID requestId = UUID.randomUUID();

	@ManyToOne(optional = false)
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	public PasswordChangeRequest(User user) {
		this.user = user;
	}
}