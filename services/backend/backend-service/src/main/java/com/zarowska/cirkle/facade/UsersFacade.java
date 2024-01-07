package com.zarowska.cirkle.facade;

import com.zarowska.cirkle.api.model.Profile;
import java.util.UUID;
import org.springframework.transaction.annotation.Transactional;

public interface UsersFacade {

	@Transactional(readOnly = true)
	Profile getProfileById(UUID userId);
}
