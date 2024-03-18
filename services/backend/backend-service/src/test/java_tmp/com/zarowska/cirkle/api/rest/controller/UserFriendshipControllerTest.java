package com.zarowska.cirkle.api.rest.controller;

import static com.zarowska.cirkle.api.RestApiConstants.REST_API_ROOT_PATH;

import com.zarowska.cirkle.AbstractTest;
import com.zarowska.cirkle.api.rest.model.response.friendship.FriendRequestDto;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class UserFriendshipControllerTest extends AbstractTest {

    @Test
    @Disabled
    void testFriendRequestDto() {
        ResponseEntity<FriendRequestDto> response = get(REST_API_ROOT_PATH + "/user/requests", FriendRequestDto.class);
        AssertionsForClassTypes.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    // @Test
    // void testFriendshipDto() {
    // ResponseEntity<FriendshipEntity> response = get(REST_API_ROOT_PATH +
    // "/user/requests", FriendshipEntity.class);
    // AssertionsForClassTypes.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    // }

    // @Test
    // void testFriendshipDtoList() {
    // ResponseEntity<FriendshipList> response = get(REST_API_ROOT_PATH +
    // "/user/requests", FriendshipDtoList.class);
    // AssertionsForClassTypes.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    // }

    // @Test
    // void shouldAcceptRequest() {
    // }
    //
    //// @Test
    // void shouldDeleteById() {
    // }
    //
    // @Test
    // void shouldSendFriendRequest() {
    // }
    //
    // @Test
    // void shouldRemoveFriend() {
    // }
    //
    // @Test
    // void shouldGetUserFriends() {
    // }
    //
    // @Test
    // void shouldGetUserFriendshipWith() {
    // }

}
