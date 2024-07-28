package blog.cirkle.app.service.impl;

import blog.cirkle.app.exception.BadRequestException;
import blog.cirkle.app.model.entity.Image;
import blog.cirkle.app.service.ImageCache;
import blog.cirkle.app.service.ImageService;
import blog.cirkle.app.service.SystemConfigService;
import jakarta.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.FileTime;
import java.time.Duration;
import java.time.Instant;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ImageCacheImpl implements ImageCache {

	private static final Logger log = LoggerFactory.getLogger(ImageCacheImpl.class);
	private final SystemConfigService systemConfigService;
	private final ImageService imageService;
	private File fileCacheFolder;

	@PostConstruct
	void init() {
		String cacheFolder = systemConfigService.getSystemProperties().getProperty("cache.image.folder",
				"./downloads/");
		fileCacheFolder = new File(cacheFolder);
		fileCacheFolder.mkdirs();
	}

	@Override
	@Transactional(readOnly = true)
	public Resource findByIdAsResource(UUID imageId) {
		Image image = imageService.findById(imageId);
		File imageFile = new File(fileCacheFolder, imageId.toString());
		try {
			if (!imageFile.exists()) {
				Files.write(imageFile.toPath(), image.getContent());
			} else {
				try {
					FileTime now = FileTime.fromMillis(System.currentTimeMillis());
					Files.setLastModifiedTime(imageFile.toPath(), now);
				} catch (IOException e) {
					log.warn("Could not set the last modified time for the image {} due: {}", imageId, e.getMessage());
				}
			}
			return new FileSystemResource(imageFile);
		} catch (IOException e) {
			throw new BadRequestException("Unable to save file to cache: " + e.getMessage(), e);
		}
	}

	@Scheduled(fixedDelay = 60000)
	void cleanUpCache() {
		log.debug("Cleaning up cache folder: {}", fileCacheFolder.getAbsolutePath());
		try {
			deleteOldFiles(Duration.ofMinutes(2));
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}
	}

	public void deleteOldFiles(Duration maxAge) throws IOException {
		Path directory = fileCacheFolder.toPath();
		Instant cutoff = Instant.now().minus(maxAge);
		AtomicLong cacheRemain = new AtomicLong();
		AtomicLong cacheDeleted = new AtomicLong();

		Files.walk(directory).filter(Files::isRegularFile).filter(path -> {
			try {
				boolean shouldBeDeleted = Files.getLastModifiedTime(path).toInstant().isBefore(cutoff);
				if (shouldBeDeleted) {
					cacheDeleted.addAndGet(Files.size(path));
				} else {
					cacheRemain.addAndGet(Files.size(path));
				}
				return shouldBeDeleted;
			} catch (IOException e) {
				return false;
			}
		}).forEach(path -> {
			try {
				Files.delete(path);
			} catch (IOException e) {
				log.error("Unable to delete file: {}, due: {}", path, e.getMessage());
			}
		});
		log.info("Cache size: {}, deleted {}", cacheRemain.get(), cacheDeleted.get());
	}
}
