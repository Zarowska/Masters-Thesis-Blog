package blog.cirkle.app.service;

import blog.cirkle.app.model.entity.Group;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface GroupService {
	Page<Group> findAll(String query, Pageable pageable);
}
