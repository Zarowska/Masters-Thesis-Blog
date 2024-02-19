package com.zarowska.cirkle.domain.service.impl;

import com.zarowska.cirkle.domain.entity.FileInfoEntity;
import com.zarowska.cirkle.domain.entity.UserEntity;
import com.zarowska.cirkle.domain.model.ImageDto;
import com.zarowska.cirkle.domain.service.FileService;
import com.zarowska.cirkle.domain.service.ImagesService;
import com.zarowska.cirkle.domain.service.impl.helper.CacheHelper;
import com.zarowska.cirkle.domain.service.impl.helper.ImageResizeHelper;
import com.zarowska.cirkle.exception.ResourceNotFoundException;
import java.util.Map;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class ImagesServiceImpl implements ImagesService {

	private final FileService fileService;
	private final CacheHelper cacheHelper;
	private final ImageResizeHelper imageResizeHelper;

	@Override
	public ImageDto findById(UserEntity user, UUID imageId, Integer width, Integer height) {
		FileInfoEntity fileInfo = fileService.findById(imageId)
				.orElseThrow(() -> new ResourceNotFoundException("image", Map.of("id", imageId)));

		String imageKey = buildKey(imageId, width, height);

		byte[] content = cacheHelper.retrieveFromCache(imageKey)
				.orElseGet(() -> resizeIfNeeded(width, height, fileInfo, imageKey));

		return new ImageDto(fileInfo.getMediaType().toString(), new ByteArrayResource(content) {
			@Override
			public String getFilename() {
				return fileInfo.getOriginalName();
			}
		});
	}

	@Override
	public FileInfoEntity save(UserEntity user, MultipartFile image) {

		return fileService.upload(user, image);
	}

	// @Override
	// public Page<FileInfoEntity> getImageInfoList(Integer page, Integer size) {
	// return fileService.getImageInfoList(page, size);
	// }
	//
	// @Override
	// public FileInfoEntity getImageInfoById(UUID imageId) {
	// return fileService.getImageInfoById(imageId);
	// }

	private byte[] resizeIfNeeded(Integer width, Integer height, FileInfoEntity fileInfo, String imageKey) {
		byte[] newContent = fileInfo.getContent().getContent();
		try {
			if (width != null || height != null) {
				newContent = imageResizeHelper.resizeImage(fileInfo.getOriginalName(),
						fileInfo.getContent().getContent(), width, height);
			}
		} catch (Exception e) {
			// ignore
			e.printStackTrace();
		}
		cacheHelper.storeInCache(imageKey, newContent);
		return newContent;
	}

	public String buildKey(UUID imageId, Integer width, Integer height) {
		StringBuilder stringBuilder = new StringBuilder();

		// imageId will always have value
		stringBuilder.append("imageId=");
		stringBuilder.append(imageId.toString());

		if (width != null) {
			stringBuilder.append(", width=");
			stringBuilder.append(width);
		}

		if (height != null) {
			stringBuilder.append(", height=");
			stringBuilder.append(height);
		}

		return stringBuilder.toString();
	}
}
