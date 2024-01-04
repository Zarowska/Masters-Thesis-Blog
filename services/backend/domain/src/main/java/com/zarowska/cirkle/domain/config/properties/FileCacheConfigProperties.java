package com.zarowska.cirkle.domain.config.properties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Configuration
@ConfigurationProperties(prefix = "application.cache.file")
public class FileCacheConfigProperties {
	private int heapSize = 100;
	private int size = 500;
	private String location = "cache";
	private long expiration = 100L;
}
