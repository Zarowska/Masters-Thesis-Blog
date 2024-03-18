package com.zarowska.cirkle.facade.mapper;

import com.zarowska.cirkle.api.model.FileDto;
import com.zarowska.cirkle.api.model.FilePage;
import com.zarowska.cirkle.domain.entity.FileInfoEntity;
import java.net.URI;
import java.time.ZoneOffset;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FileInfoEntityMapper {

	private final UserEntityMapper mapper;

	public FileDto toDto(FileInfoEntity entity) {
		return new FileDto().id(entity.getId()).url(URI.create("/images/%s".formatted(entity.getId())))
				.size(entity.getSize()).owner(mapper.toDto(entity.getOwner()))
				.mediaType(entity.getMediaType().toString())
				.uploadedAt(entity.getUploadedAt().atOffset(ZoneOffset.UTC));
	}

	public FilePage toDto(Page<FileInfoEntity> page) {
		List<FileInfoEntity> images = page.getContent().stream().toList();
		return FilePage.builder().first(page.isFirst()).empty(page.isEmpty()).size(page.getSize()).last(page.isLast())
				.number(page.getNumber()).numberOfElements(page.getNumberOfElements())
				.content(images.stream().map(this::toDto).toList()).build();
	}
}
