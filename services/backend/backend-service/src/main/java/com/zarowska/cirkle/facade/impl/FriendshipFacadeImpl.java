package com.zarowska.cirkle.facade.impl;

import com.zarowska.cirkle.domain.entity.FriendshipEntity;
import com.zarowska.cirkle.domain.entity.UserEntity;
import com.zarowska.cirkle.domain.service.FriendshipService;
import com.zarowska.cirkle.domain.service.UserEntityService;
import com.zarowska.cirkle.exception.ResourceNotFoundException;
import com.zarowska.cirkle.facade.FriendshipFacade;
import com.zarowska.cirkle.security.BlogUser;
import com.zarowska.cirkle.security.SecurityUtils;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class FriendshipFacadeImpl implements FriendshipFacade {

    private final UserEntityService userEntityService;
    private final FriendshipService friendshipService;

    @PersistenceContext
    private final EntityManager em;

    @Override
    public void sendFriendshipRequest(String userId) {
        userEntityService.findById(UUID.fromString(userId))
                .map(targetUser -> {
                    UserEntity currentUser = em.merge(SecurityUtils.getCurrentUser().getPrincipal());
                    FriendshipEntity request = new FriendshipEntity(currentUser, targetUser);
                    return friendshipService.save(request);
                }).orElseThrow(() -> new ResourceNotFoundException("user", "id=%s".formatted(userId)));
    }
}
