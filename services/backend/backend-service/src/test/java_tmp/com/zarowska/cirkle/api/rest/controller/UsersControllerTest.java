package com.zarowska.cirkle.api.rest.controller;

import static com.zarowska.cirkle.api.RestApiConstants.REST_API_ROOT_PATH;
import static org.assertj.core.api.Assertions.assertThat;

import com.zarowska.cirkle.AbstractTest;
import com.zarowska.cirkle.api.rest.model.response.ApiErrorResponse;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

class UsersControllerTest extends AbstractTest {

    @Test
    void shouldReturnProperError() {
        UUID id = UUID.randomUUID();
        ResponseEntity<ApiErrorResponse> response = get(REST_API_ROOT_PATH + "/users" + "/" + id,
                ApiErrorResponse.class);
        ApiErrorResponse body = response.getBody();
        assertThat(body.getError()).isNotNull();
        assertThat(body.getError().getTitle()).isEqualTo("User (id=%s) not found".formatted(id));
    }
}