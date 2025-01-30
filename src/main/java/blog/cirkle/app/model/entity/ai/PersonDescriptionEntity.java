package blog.cirkle.app.model.entity.ai;

import blog.cirkle.app.ai.generator.api.PersonDescription;
import jakarta.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "ai_person_description")
public class PersonDescriptionEntity {
	@Id
	@Builder.Default
	private UUID id = UUID.randomUUID();

	private String fullName;
	private Integer age;
	private String gender;
	private String nationality;
	private String origin;
	private String liveCity;
	private String liveCountry;
	private String email;
	private String bio;
	private String appearance;
	@Column(name = "last_feed", nullable = true)
	private UUID lastFeed;

	@OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
	@JoinColumn(name = "person_description_entity_id")
	@Builder.Default
	private Set<PersonInterestEntity> personInterestEntities = new LinkedHashSet<>();

	public PersonDescriptionEntity(PersonDescription description) {
		this.id = UUID.randomUUID();
		this.fullName = description.getFullName();
		this.age = description.getAge();
		this.gender = description.getGender();
		this.nationality = description.getNationality();
		this.origin = description.getOrigin();
		this.liveCity = description.getLiveCity();
		this.liveCountry = description.getLiveCountry();
		this.email = description.getEmail();
		this.bio = description.getBio();
		this.appearance = description.getAppearance();
		if (description.getInterests() != null) {
			this.personInterestEntities = description.getInterests().stream().map(PersonInterestEntity::fromDto)
					.collect(Collectors.toCollection(LinkedHashSet::new));
		}
	}

	// Method to convert Entity to DTO
	public PersonDescription toDto() {
		PersonDescription build = PersonDescription.builder().fullName(this.fullName).age(this.age).gender(this.gender)
				.nationality(this.nationality).origin(this.origin).liveCity(this.liveCity).liveCountry(this.liveCountry)
				.email(this.email).bio(this.bio).appearance(this.appearance).build();
		if (this.personInterestEntities != null && !this.personInterestEntities.isEmpty()) {
			build.setInterests(this.personInterestEntities.stream().map(PersonInterestEntity::toDto).toList());
		}
		return build;
	}
}