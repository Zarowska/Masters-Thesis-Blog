package blog.cirkle.domain.entity.participant;

import blog.cirkle.domain.entity.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Getter
@Setter
@Entity
@Table(name = "email_validations")
@NoArgsConstructor
public class EmailValidation extends BaseEntity {
	@NotNull @Column(name = "code", nullable = false)
	private UUID code = UUID.randomUUID();

	@OneToOne(fetch = FetchType.LAZY)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JoinColumn(name = "user_ref")
	private User userRef;

	public EmailValidation(User userRef) {
		this.userRef = userRef;
	}
}