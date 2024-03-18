package com.zarowska.cirkle.facade;

import com.zarowska.cirkle.api.model.User;
import org.springframework.transaction.annotation.Transactional;

public interface UserFacade {

	@Transactional
	User getCurrentUser();
}
