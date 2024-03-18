package com.zarowska.cirkle.domain.model;

import lombok.Value;
import org.springframework.core.io.Resource;

@Value
public class ImageDto {
	private final String mediaType;
	private final Resource resource;
}
