package com.zarowska.cirkle.domain.service.impl;

import com.zarowska.cirkle.domain.entity.FileContentEntity;
import com.zarowska.cirkle.domain.entity.FileInfoEntity;
import com.zarowska.cirkle.domain.entity.UserEntity;
import com.zarowska.cirkle.domain.repository.FileInfoEntityRepository;
import com.zarowska.cirkle.domain.service.FileService;
import com.zarowska.cirkle.domain.service.TikaService;
import com.zarowska.cirkle.exception.BadRequestException;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import javax.imageio.ImageIO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {
	private final FileInfoEntityRepository fileInfoEntityRepository;
	private final TikaService tikaService;

	@Override
	public Optional<FileInfoEntity> findById(UUID id) {
		return fileInfoEntityRepository.findById(id);
	}

	@Override
	public List<FileInfoEntity> findByOwner(UserEntity owner) {
		return fileInfoEntityRepository.findByOwnerId(owner);
	}

	@Override
	public void deleteById(UUID id) {
		fileInfoEntityRepository.deleteById(id);
	}

	@Override
	public FileInfoEntity upload(UserEntity owner, MultipartFile file) {
		try {
			byte[] content = file.getBytes();
			String mediaType = tikaService.detectMediaType(content);
			String[] types = mediaType.split("/");
			FileInfoEntity entity = null;
			FileContentEntity fileContent = new FileContentEntity(content);

			entity = new FileInfoEntity(owner, MediaType.valueOf(mediaType), content.length, file.getOriginalFilename(),
					fileContent);
			entity.getContent().setInfo(entity);

			if (types.length > 0 && types[0].equals("image")) {
				parseImage(entity, content);
			}
			return fileInfoEntityRepository.save(entity);
		} catch (Exception e) {
			throw new BadRequestException("Unable to parse uploaded file");
		}
	}

	@Override
	public Page<FileInfoEntity> findAllByOwner(UserEntity owner, Pageable pageable) {
		return fileInfoEntityRepository.findAllByOwner(owner, pageable);
	}

	private void parseImage(FileInfoEntity entity, byte[] content) {
		try (InputStream inputStream = new ByteArrayInputStream(content)) {
			BufferedImage image = ImageIO.read(inputStream);
			entity.setHeight(image.getHeight());
			entity.setWidth(image.getWidth());
		} catch (IOException e) {
			throw new RuntimeException("Unable to parse image");
		}
	}
}
