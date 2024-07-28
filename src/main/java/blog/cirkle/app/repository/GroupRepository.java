package blog.cirkle.app.repository;

import blog.cirkle.app.model.entity.Group;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupRepository extends JpaRepository<Group, UUID> {
	Page<Group> findByProfile_NameLikeIgnoreCase(String name, Pageable pageable);
}