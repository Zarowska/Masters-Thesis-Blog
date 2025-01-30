package blog.cirkle.app.repository;

import blog.cirkle.app.model.entity.ai.PersonDescriptionEntity;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PersonDescriptionEntityRepository extends JpaRepository<PersonDescriptionEntity, UUID> {
	@Query(value = """
			      select * from ai_person_description where email in (select email
			                                                          from users
			                                                          where profile_image_id is not null
			                                                            and users.id not in (select author_id from post));
			""", nativeQuery = true)
	List<PersonDescriptionEntity> findPersonsWithoutPosts();
}