package com.zarowska.cirkle.domain.model.mutators;

import com.zarowska.cirkle.domain.entity.FileInfoEntity;
import java.util.function.Function;

public abstract class FileContentMutator implements Function<FileInfoEntity, byte[]> {

	public abstract boolean canApply(FileInfoEntity source);

	public abstract String getSuffix();

}
