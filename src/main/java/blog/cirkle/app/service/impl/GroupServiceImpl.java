package blog.cirkle.app.service.impl;

import blog.cirkle.app.model.entity.Group;
import blog.cirkle.app.repository.GroupRepository;
import blog.cirkle.app.service.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GroupServiceImpl implements GroupService {

	private final GroupRepository groupRepository;

	@Override
	public Page<Group> findAll(String query, Pageable pageable) {
		if (query == null || query.isEmpty()) {
			return groupRepository.findAll(pageable);
		} else {
			return groupRepository.findByProfile_NameLikeIgnoreCase("%" + query + "%", pageable);
		}
	}
}
