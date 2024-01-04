package com.zarowska.cirkle.domain.service.impl;

import com.zarowska.cirkle.domain.entity.FileInfoEntity;
import com.zarowska.cirkle.domain.entity.UserEntity;
import com.zarowska.cirkle.domain.service.FileService;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {
	@Override
	public Optional<FileInfoEntity> findById(UUID id) {
		return Optional.empty();
	}

	@Override
	public List<FileInfoEntity> findByOwner(UUID ownerId) {
		return null;
	}

	@Override
	public void deleteById(UUID id) {

	}

	@Override
	public FileInfoEntity upload(UserEntity principal, MultipartFile file) {
		return null;
	}

	// private final FileInfoEntityRepository fileInfoEntityRepository;
	// private final FileContentEntityRepository fileContentEntityRepository;
	// private final Tika tika;
	//
	// @PersistenceContext
	// private final EntityManager entityManager;
	//
	// @Override
	// public Optional<FileInfoEntity> findById(UUID id) {
	// return fileInfoEntityRepository.findById(id);
	// }
	//
	// @Override
	// public List<FileInfoEntity> findByOwner(UUID ownerId) {
	// return fileInfoEntityRepository.findByOwnerId(ownerId);
	// }
	//
	// @Override
	// public void deleteById(UUID id) {
	// fileInfoEntityRepository.deleteById(id);
	// }
	//
	// @Override
	// public FileInfoEntity upload(UserEntity principal, MultipartFile file) {
	// FileContentEntity contentEntity = parseFile(principal, file);
	// contentEntity.setInfo(fileInfoEntityRepository.save(contentEntity.getInfo()));
	// fileContentEntityRepository.save(contentEntity);
	// return contentEntity.getInfo();
	// }
	//
	// private FileContentEntity parseFile(UserEntity principal, MultipartFile file)
	// {
	// FileContentEntity fileContentEntity = new FileContentEntity();
	// FileInfoEntity fileInfoEntity = new FileInfoEntity();
	// fileContentEntity.setInfo(fileInfoEntity);
	// UserEntity user = SecurityUtils.getCurrentUser().getPrincipal();
	// fileInfoEntity.setOwner(entityManager.merge(user));
	// fileInfoEntity.setOriginalName(file.getOriginalFilename());
	// readFile(file, fileContentEntity);
	// guessContent(fileContentEntity);
	// return fileContentEntity;
	// }
	//
	// private void guessContent(FileContentEntity fileEntity) {
	// String detected = tika.detect(fileEntity.getContent(),
	// fileEntity.getInfo().getOriginalName());
	// MediaType mediaType = MediaType.valueOf(detected);
	// fileEntity.getInfo().setMediaType(mediaType);
	// if (mediaType.getType().equals("image")) {
	// getImageSize(fileEntity);
	// }
	// }
	//
	// private void getImageSize(FileContentEntity entity) {
	// try (ByteArrayInputStream bis = new
	// ByteArrayInputStream(entity.getContent())) {
	// BufferedImage image = ImageIO.read(bis);
	// entity.getInfo().setHeight(image.getHeight());
	// entity.getInfo().setWidth(image.getWidth());
	// } catch (IOException e) {
	// }
	// }
	//
	// private static void readFile(MultipartFile file, FileContentEntity
	// fileEntity) {
	// try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
	// InputStream inputStream = file.getInputStream();
	// byte[] buff = new byte[1024];
	// int read;
	// while (true) {
	// if (!((read = inputStream.read(buff)) != -1))
	// break;
	// baos.write(buff, 0, read);
	// }
	// fileEntity.setContent(baos.toByteArray());
	// fileEntity.getInfo().setSize(fileEntity.getContent().length);
	// } catch (IOException e) {
	// throw new BadRequestException("Unable parse file due " + e.getMessage());
	// }
	// }

}
