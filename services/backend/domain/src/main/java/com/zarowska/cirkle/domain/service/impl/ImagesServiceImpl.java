package com.zarowska.cirkle.domain.service.impl;

import com.zarowska.cirkle.domain.entity.FileInfoEntity;
import com.zarowska.cirkle.domain.entity.UserEntity;
import com.zarowska.cirkle.domain.model.ImageDto;
import com.zarowska.cirkle.domain.repository.PostImageRepository;
import com.zarowska.cirkle.domain.service.FileService;
import com.zarowska.cirkle.domain.service.ImagesService;
import com.zarowska.cirkle.domain.service.impl.helper.CacheHelper;
import com.zarowska.cirkle.domain.service.impl.helper.ImageResizeHelper;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class ImagesServiceImpl implements ImagesService {

	private final FileService fileService;
	private final CacheHelper cacheHelper;
	private final ImageResizeHelper imageResizeHelper;
	private final PostImageRepository postImageRepository;
	@Override
	public Optional<ImageDto> findById(UUID imageId, Integer width, Integer height) {
		return fileService.findById(imageId).map(fileInfo -> {
			String imageKey = buildKey(imageId, width, height);

			byte[] content = cacheHelper.retrieveFromCache(imageKey)
					.orElseGet(() -> resizeIfNeeded(width, height, fileInfo, imageKey));

			return new ImageDto(fileInfo.getMediaType().toString(), new ByteArrayResource(content) {
				@Override
				public String getFilename() {
					return fileInfo.getOriginalName();
				}
			});
		});

	}

	@Override
	public FileInfoEntity save(UserEntity user, MultipartFile image) {
		return fileService.upload(user, image);
	}

	@Override
	public Optional<FileInfoEntity> getImageInfoById(UUID imageId) {
		return fileService.findById(imageId);
	}

	@Override
	public void deleteById(UserEntity user, UUID imageId) {
		fileService.findById(imageId).ifPresent(fileInfo -> {
			if (fileInfo.getOwner().equals(user)) {
				postImageRepository.deleteByImage_Id(imageId);
				fileService.deleteById(imageId);
			}
		});
	}

	@Override
	public Page<FileInfoEntity> listByOwner(UserEntity user, Pageable pageable) {
		return fileService.findAllByOwner(user, pageable);
	}

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
