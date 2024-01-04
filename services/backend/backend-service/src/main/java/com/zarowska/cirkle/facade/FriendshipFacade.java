package com.zarowska.cirkle.facade;

import org.springframework.transaction.annotation.Transactional;

public interface FriendshipFacade {
    @Transactional
    void sendFriendshipRequest(String userId);
}
