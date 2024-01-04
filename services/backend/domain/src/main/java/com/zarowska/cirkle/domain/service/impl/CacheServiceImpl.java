package com.zarowska.cirkle.domain.service.impl;

import com.zarowska.cirkle.domain.entity.FileInfoEntity;
import com.zarowska.cirkle.domain.model.FileSnapshot;
import com.zarowska.cirkle.domain.model.mutators.FileContentMutator;
import com.zarowska.cirkle.domain.service.CacheService;
import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class CacheServiceImpl implements CacheService {

	// @Qualifier("file-cache") private final Cache<String, FileSnapshot> cache;

	private static String constructBaseFilename(UUID fileId, FileContentMutator mutator) {
		String suffix = mutator == null ? "" : mutator.getSuffix();
		return fileId + "_" + suffix;
	}

	@Override
	public Optional<FileSnapshot> get(UUID fileId, FileContentMutator mutator) {
		// String filename = constructBaseFilename(fileId, mutator);
		// if (cache.containsKey(filename)) {
		// return Optional.of(cache.get(filename));
		// } else {
		// return Optional.empty();
		// }
		return Optional.empty();
	}

	@Override
	public FileSnapshot store(FileInfoEntity fileEntity, FileContentMutator mutator) {
		log.info(fileEntity.getId() + " storing to cache");
		String filename = constructBaseFilename(fileEntity.getId(), mutator);

		byte[] content = (mutator == null || !mutator.canApply(fileEntity))
				? copyOf(fileEntity.getContent().getContent())
				: mutator.apply(fileEntity);

		FileSnapshot snapshot = new FileSnapshot().setOriginalName(fileEntity.getOriginalName())
				.setMediaType(fileEntity.getMediaType().toString()).setContent(content);
		// cache.put(filename, snapshot);
		return snapshot;
	}

	private byte[] copyOf(byte[] content) {
		return Arrays.copyOf(content, content.length);
	}

	@Override
	public void delete(UUID fileId, FileContentMutator mutator) {
		log.info(fileId + " deleting from cache");
		String filename = constructBaseFilename(fileId, mutator);
		// cache.remove(filename);
	}
}
