package com.zarowska.cirkle;

import static com.zarowska.cirkle.api.RestApiConstants.REST_API_ROOT_PATH;
import static org.assertj.core.api.Assertions.assertThat;

import com.zarowska.cirkle.config.MockJwtIssuer;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.util.function.Consumer;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@Slf4j
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class AbstractTest {

    @LocalServerPort
    protected Integer port;
    protected TestRestTemplate restTemplate = new TestRestTemplate();
    protected HttpHeaders headers;
    @Autowired
    MockJwtIssuer mockJwtIssuer;
    private Consumer<HttpHeaders> headerCustomizer = headers -> {
    };

    @BeforeEach
    protected void setupHeaders() {
        headers = new HttpHeaders();
        headers.setBearerAuth(mockJwtIssuer.issueToken());
    }

    protected URI localUrl(String path) {
        return URI.create(localUrlStr(path));
    }

    protected String localUrlStr(String path) {
        return "http://localhost:%d%s".formatted(port, path);
    }

    protected <OUT> ResponseEntity<OUT> get(String apiPath, Class<OUT> outClass, Object... params) {
        return doCall(apiPath, HttpMethod.GET, null, outClass, params);
    }

    protected <OUT> ResponseEntity<OUT> get(String apiPath, ParameterizedTypeReference<OUT> outClass,
                                            Object... params) {
        return doCall(apiPath, HttpMethod.GET, null, outClass, params);
    }

    protected <IN, OUT> ResponseEntity<OUT> post(String apiPath, IN body, ParameterizedTypeReference<OUT> outClass,
                                                 Object... params) {
        return doCall(apiPath, HttpMethod.POST, body, outClass, params);
    }

    protected <IN, OUT> ResponseEntity<OUT> post(String apiPath, IN body, Class<OUT> outClass, Object... params) {
        return doCall(apiPath, HttpMethod.POST, body, outClass, params);
    }

    protected <IN, OUT> ResponseEntity<OUT> put(String apiPath, IN body, Class<OUT> outClass, Object... params) {
        return doCall(apiPath, HttpMethod.PUT, body, outClass, params);
    }

    protected <IN, OUT> ResponseEntity<OUT> put(String apiPath, IN body, ParameterizedTypeReference<OUT> outClass,
                                                Object... params) {
        return doCall(apiPath, HttpMethod.PUT, body, outClass, params);
    }

    protected <IN, OUT> ResponseEntity<OUT> delete(String apiPath, IN body, ParameterizedTypeReference<OUT> outClass,
                                                   Object... params) {
        return doCall(apiPath, HttpMethod.DELETE, body, outClass, params);
    }

    protected <IN, OUT> ResponseEntity<OUT> delete(String apiPath, IN body, Class<OUT> outClass, Object... params) {
        return doCall(apiPath, HttpMethod.DELETE, body, outClass, params);
    }

    protected <IN, OUT> ResponseEntity<OUT> doCall(String apiPath, HttpMethod method, IN body,
                                                   ParameterizedTypeReference<OUT> outClass, Object... params) {
        headerCustomizer.accept(headers);
        HttpEntity<IN> httpEntity = new HttpEntity<>(body, headers);
        ResponseEntity<OUT> result = restTemplate.exchange(localUrlStr(apiPath), method, httpEntity, outClass, params);
        setupHeaders();
        return result;
    }

    protected <IN, OUT> ResponseEntity<OUT> doCall(String apiPath, HttpMethod method, IN body, Class<OUT> outClass,
                                                   Object... params) {
        String url = localUrlStr(apiPath);
        HttpEntity<IN> httpEntity = new HttpEntity<>(body, headers);
        log.info("HttpRequest: %s %s Body: %s / Headers: %s".formatted(method, url, httpEntity.getBody(), headers));
        headerCustomizer.accept(headers);
        ResponseEntity<OUT> result = restTemplate.exchange(url, method, httpEntity, outClass, params);
        setupHeaders();
        return result;
    }

    protected <T> ResponseEntity<T> uploadFile(String filename, Class<T> outClass) throws IOException {
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", getResourceFile(filename));
        return post(REST_API_ROOT_PATH + "/user/files", body, outClass);
    }

    protected ResponseEntity<byte[]> downloadFile(String path, Object... params) {
        ResponseEntity<byte[]> responseEntity = get(path, new ParameterizedTypeReference<byte[]>() {
        }, params);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        return responseEntity;
    }

    protected BufferedImage downloadImage(String path, Object... params) {
        ResponseEntity<byte[]> responseEntity = downloadFile(path, params);
        return ImageUtils.toBufferedImage(responseEntity.getBody());
    }

    protected FileSystemResource getResourceFile(String name) {
        URL resource = this.getClass().getResource(name);
        return new FileSystemResource(new File(resource.getFile()));
    }
}
