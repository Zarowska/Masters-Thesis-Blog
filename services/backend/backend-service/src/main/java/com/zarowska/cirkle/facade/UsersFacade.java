package com.zarowska.cirkle.facade;

import com.zarowska.cirkle.api.model.Profile;
import com.zarowska.cirkle.api.model.User;
import com.zarowska.cirkle.api.model.UserPage;
import java.util.UUID;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

public interface UsersFacade {

	@Transactional(readOnly = true)
	Profile getProfileById(UUID userId);

	@Transactional(readOnly = true)
	UserPage listUsers(PageRequest pageRequest);

	@Transactional(readOnly = true)
	User getUserById(UUID userId);

	@Transactional
	Profile updateProfileById(UUID userId, Profile profile);
}
