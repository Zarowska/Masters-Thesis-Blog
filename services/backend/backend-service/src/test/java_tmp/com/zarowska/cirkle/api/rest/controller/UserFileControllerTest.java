package com.zarowska.cirkle.api.rest.controller;

import static com.zarowska.cirkle.api.RestApiConstants.REST_API_ROOT_PATH;
import static org.assertj.core.api.Assertions.assertThat;

import com.zarowska.cirkle.AbstractTest;
import com.zarowska.cirkle.api.rest.model.response.ApiErrorResponse;
import com.zarowska.cirkle.api.rest.model.response.files.FileInfo;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

class UserFileControllerTest extends AbstractTest {

    @Test
    void shouldNotFound() {
        UUID uuid = UUID.randomUUID();
        ResponseEntity<FileInfo> getInfoResponse = get(REST_API_ROOT_PATH + "/user/files" + "/" + uuid + "/info",
                FileInfo.class);

        assertThat(getInfoResponse.getBody().getError().getTitle()).contains(uuid.toString());
    }

    @Test
    void shouldUploadImage() {
        // upload
        FileInfo expected = new FileInfo().setMediaType("image/png").setOriginalName("max_payne.png").setSize(109942)
                .setWidth(220).setHeight(271);

        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", getResourceFile("/files/max_payne.png"));
        ResponseEntity<FileInfo> response = post(REST_API_ROOT_PATH + "/user/files", body, FileInfo.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        FileInfo actual = response.getBody();

        expected.setId(actual.getId()).setOwnerId(actual.getOwnerId()).setUploadedAt(actual.getUploadedAt());

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void shouldGetInfo() throws IOException {
        FileInfo expected = new FileInfo().setMediaType("image/png").setOriginalName("max_payne.png").setSize(109942)
                .setWidth(220).setHeight(271);

        FileInfo response = uploadFile("/files/max_payne.png", FileInfo.class).getBody();
        expected.setId(response.getId()).setOwnerId(response.getOwnerId()).setUploadedAt(response.getUploadedAt());

        ResponseEntity<FileInfo> getInfoResponse = get(
                REST_API_ROOT_PATH + "/user/files" + "/" + expected.getId() + "/info", FileInfo.class);
        assertThat(getInfoResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(getInfoResponse.getBody()).isEqualTo(expected);
    }

    @Test
    void shouldDownloadFile() throws IOException {
        FileInfo response = uploadFile("/files/max_payne.png", FileInfo.class).getBody();

        // download
        BufferedImage image = downloadImage(REST_API_ROOT_PATH + "/user/files/{fileId}", response.getId());
        assertThat(image.getWidth()).isEqualTo(220);
        assertThat(image.getHeight()).isEqualTo(271);

        // download again
        BufferedImage image2 = downloadImage(REST_API_ROOT_PATH + "/user/files/{fileId}", response.getId());
        assertThat(image2.getWidth()).isEqualTo(220);
        assertThat(image2.getHeight()).isEqualTo(271);
    }

    @Test
    void shouldDeleteFile() throws IOException {
        FileInfo response = uploadFile("/files/max_payne.png", FileInfo.class).getBody();
        BufferedImage image = downloadImage(REST_API_ROOT_PATH + "/user/files/{fileId}", response.getId());
        delete(REST_API_ROOT_PATH + "/user/files/{fileId}", null, Void.class, response.getId());
        ResponseEntity<ApiErrorResponse> afterDelete = get(REST_API_ROOT_PATH + "/user/files/{fileId}",
                ApiErrorResponse.class, response.getId());
        assertThat(afterDelete.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);

    }
}