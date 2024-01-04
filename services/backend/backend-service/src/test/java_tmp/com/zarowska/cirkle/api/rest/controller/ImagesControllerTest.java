package com.zarowska.cirkle.api.rest.controller;

import static org.assertj.core.api.Assertions.assertThat;

import com.zarowska.cirkle.AbstractTest;
import com.zarowska.cirkle.api.rest.model.response.files.FileInfo;

import java.awt.image.BufferedImage;
import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

class ImagesControllerTest extends AbstractTest {

    @Test
    void shouldDownloadImage() throws IOException {
        ResponseEntity<FileInfo> info = uploadFile("/files/max_payne.png", FileInfo.class);
        BufferedImage image = downloadImage("/images/{fileId}", info.getBody().getId());
        assertThat(image.getWidth()).isEqualTo(220);
        assertThat(image.getHeight()).isEqualTo(271);

        // download again
        BufferedImage image2 = downloadImage("/images/{fileId}", info.getBody().getId());
        assertThat(image2.getWidth()).isEqualTo(220);
        assertThat(image2.getHeight()).isEqualTo(271);
    }

    @Test
    void shouldDownloadScaledImage() throws IOException {
        FileInfo info = uploadFile("/files/max_payne.png", FileInfo.class).getBody();
        BufferedImage image = downloadImage("/images/{fileId}/{scale}", info.getId(), "100x100");
        assertThat(image.getWidth()).isEqualTo(100);
        assertThat(image.getHeight()).isEqualTo(100);
    }
}