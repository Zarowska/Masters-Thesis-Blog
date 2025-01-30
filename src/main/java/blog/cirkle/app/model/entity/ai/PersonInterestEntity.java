package blog.cirkle.app.model.entity.ai;

import blog.cirkle.app.ai.generator.api.PersonDescription;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.Objects;
import java.util.UUID;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Table(name = "person_interests")
public class PersonInterestEntity {
	@Id
	@Builder.Default
	private UUID id = UUID.randomUUID();

	private String title;
	private String description;

	// Method to convert DTO to Entity
	public static PersonInterestEntity fromDto(PersonDescription.PersonInterest dto) {
		return PersonInterestEntity.builder().title(dto.getTitle()).description(dto.getDescription()).build();
	}

	// Method to convert Entity to DTO
	public PersonDescription.PersonInterest toDto() {
		return PersonDescription.PersonInterest.builder().title(this.title).description(this.description).build();
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
		PersonInterestEntity that = (PersonInterestEntity) o;
		return getId() != null && Objects.equals(getId(), that.getId());
	}

	@Override
	public final int hashCode() {
		return this instanceof HibernateProxy
				? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode()
				: getClass().hashCode();
	}
}