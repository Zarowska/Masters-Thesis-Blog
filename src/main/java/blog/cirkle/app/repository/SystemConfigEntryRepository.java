package blog.cirkle.app.repository;

import blog.cirkle.app.model.entity.SystemConfigEntry;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SystemConfigEntryRepository extends JpaRepository<SystemConfigEntry, UUID> {
	Optional<SystemConfigEntry> findByKey(String key);
}