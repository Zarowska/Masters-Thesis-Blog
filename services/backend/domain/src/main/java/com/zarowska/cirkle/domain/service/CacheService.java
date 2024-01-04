package com.zarowska.cirkle.domain.service;

import com.zarowska.cirkle.domain.entity.FileInfoEntity;
import com.zarowska.cirkle.domain.model.FileSnapshot;
import com.zarowska.cirkle.domain.model.mutators.FileContentMutator;
import java.util.Optional;
import java.util.UUID;

public interface CacheService {

	Optional<FileSnapshot> get(UUID fileId, FileContentMutator mutator);

	FileSnapshot store(FileInfoEntity fileEntity, FileContentMutator mutator);

	void delete(UUID fileId, FileContentMutator mutator);

}
