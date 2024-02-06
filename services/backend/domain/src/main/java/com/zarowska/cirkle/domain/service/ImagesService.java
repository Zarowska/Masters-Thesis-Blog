package com.zarowska.cirkle.domain.service;

import com.zarowska.cirkle.domain.entity.FileInfoEntity;
import com.zarowska.cirkle.domain.entity.UserEntity;
import com.zarowska.cirkle.domain.model.ImageDto;
import java.util.UUID;
import org.springframework.web.multipart.MultipartFile;

public interface ImagesService {
	ImageDto findById(UserEntity user, UUID imageId, Integer width, Integer height);

	FileInfoEntity save(UserEntity user, MultipartFile image);
}
