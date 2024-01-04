package com.zarowska.cirkle.domain.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class CacheConfig {

	// @Bean
	// CacheManager cacheManager(FileCacheConfigProperties config) {
	// File cacheFolder = new File(config.getLocation());
	// cacheFolder.mkdirs();
	//
	// PersistentCacheManager manager = CacheManagerBuilder.newCacheManagerBuilder()
	// .with(CacheManagerBuilder.persistence(cacheFolder)).build();
	// manager.init();
	//
	// return manager;
	// }
	//
	// @Bean("file-cache")
	// Cache<String, FileSnapshot> fileCache(CacheManager cacheManager,
	// FileCacheConfigProperties config) {
	// return cacheManager.createCache("file-persistent-cache",
	// CacheConfigurationBuilder
	// .newCacheConfigurationBuilder(String.class, FileSnapshot.class,
	// ResourcePoolsBuilder.newResourcePoolsBuilder().heap(config.getHeapSize(),
	// MemoryUnit.MB)
	// .disk(config.getSize(), MemoryUnit.MB, true))
	// .withExpiry(Expirations.timeToLiveExpiration(Duration.of(config.getExpiration(),
	// TimeUnit.SECONDS))));
	//
	// }
}
