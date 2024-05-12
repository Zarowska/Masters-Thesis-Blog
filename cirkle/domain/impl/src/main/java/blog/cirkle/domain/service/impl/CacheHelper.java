package blog.cirkle.domain.service.impl;

import java.nio.ByteBuffer;
import java.util.Optional;
import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.config.CacheConfiguration;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.config.units.MemoryUnit;
import org.springframework.stereotype.Component;

@Component
public class CacheHelper {

	private CacheManager cacheManager;
	private Cache<String, ByteBuffer> byteArrayCache;

	public CacheHelper() {
		CacheConfiguration<String, ByteBuffer> cacheConfiguration = CacheConfigurationBuilder
				.newCacheConfigurationBuilder(String.class, ByteBuffer.class,
						ResourcePoolsBuilder.newResourcePoolsBuilder().disk(200, MemoryUnit.MB).heap(10))
				.build();

		CacheManagerBuilder<CacheManager> cacheManagerCacheManagerBuilder = CacheManagerBuilder.newCacheManagerBuilder()
				.withCache("byteArrayCache", cacheConfiguration);

		cacheManager = cacheManagerCacheManagerBuilder.build(true);

		byteArrayCache = cacheManager.getCache("byteArrayCache", String.class, ByteBuffer.class);
	}

	public void storeInCache(String key, byte[] content) {
		byteArrayCache.put(key, ByteBuffer.wrap(content));
	}

	public Optional<byte[]> retrieveFromCache(String key) {
		if (byteArrayCache.containsKey(key)) {
			return Optional.of(byteArrayCache.get(key)).map(ByteBuffer::array);
		} else {
			return Optional.empty();
		}
	}

	public void deleteFromCache(String key) {
		byteArrayCache.remove(key);
	}

	public void closeCacheManager() {
		cacheManager.close();
	}

}