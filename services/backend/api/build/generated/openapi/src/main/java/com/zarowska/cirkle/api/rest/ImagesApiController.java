package com.zarowska.cirkle.api.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;
import jakarta.annotation.Generated;

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2024-01-06T20:37:36.502088400+01:00[Europe/Warsaw]")
@Controller
@RequestMapping("${openapi.cirkle.base-path:/api/v1}")
public class ImagesApiController implements ImagesApi {

    private final ImagesApiDelegate delegate;

    public ImagesApiController(@Autowired(required = false) ImagesApiDelegate delegate) {
        this.delegate = Optional.ofNullable(delegate).orElse(new ImagesApiDelegate() {});
    }

    @Override
    public ImagesApiDelegate getDelegate() {
        return delegate;
    }

}
