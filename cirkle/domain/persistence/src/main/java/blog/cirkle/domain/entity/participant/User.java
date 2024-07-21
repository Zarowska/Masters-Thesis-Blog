package blog.cirkle.domain.entity.participant;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@Entity
@Table(name = "users")
@Accessors(chain = true)
@NoArgsConstructor
@SuperBuilder
@DiscriminatorValue("USER")
public class User extends Participant {

	@NotNull @Column(name = "email_validated", nullable = false)
	@Builder.Default
	private Boolean emailValidated = false;

	@Column(name = "auth_id", length = Integer.MAX_VALUE)
	private String authId;

	@NotNull @Column(name = "email", nullable = false, length = Integer.MAX_VALUE)
	private String email;

	@NotNull @Column(name = "first_name", nullable = false, length = Integer.MAX_VALUE)
	private String firstName;

	@NotNull @Column(name = "last_name", nullable = false, length = Integer.MAX_VALUE)
	private String lastName;

	@NotNull @Column(name = "password_hash", nullable = false, length = Integer.MAX_VALUE)
	private String passwordHash;

	@NotNull @Column(name = "role", nullable = false, length = Integer.MAX_VALUE)
	@Enumerated(EnumType.STRING)
	@Builder.Default
	private UserRole role = UserRole.USER;

	@Override
	public String getName() {
		return firstName + " " + lastName;
	}

	@Override
	public ParticipantType getType() {
		return ParticipantType.USER;
	}

	public enum UserRole {
		SYSTEM, ADMIN, USER
	}

}