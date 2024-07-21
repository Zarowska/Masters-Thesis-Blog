package blog.cirkle.domain.facade.impl;

import blog.cirkle.domain.entity.resource.ImageFile;
import blog.cirkle.domain.exception.ResourceNotFoundException;
import blog.cirkle.domain.facade.ImageFacade;
import blog.cirkle.domain.model.CacheEntry;
import blog.cirkle.domain.model.FileWrapper;
import blog.cirkle.domain.model.response.FileDto;
import blog.cirkle.domain.service.FileService;
import blog.cirkle.domain.service.impl.CacheHelper;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import javax.imageio.ImageIO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
@RequiredArgsConstructor
public class ImageFacadeImpl implements ImageFacade {
	private final FileService fileService;
	private final CacheHelper cacheHelper;

	@Override
	public FileDto uploadFile(MultipartFile file) {
		ImageFile imageFile = fileService.create(file);
		return FileDto.builder().id(imageFile.getId()).originalFilename(imageFile.getOriginalName())
				.mediaType(imageFile.getMediaType().toString()).size(imageFile.getContent().length).build();
	}

	@Override
	public FileWrapper download(UUID id, Integer width, Integer height) {
		return withCache(id, width, height, () -> resize(width, height, getImageFile(id)));
	}

	private CacheEntry resize(Integer width, Integer height, ImageFile imageFile) {
		try (ByteArrayInputStream bais = new ByteArrayInputStream(imageFile.getContent());
				ByteArrayOutputStream baos = new ByteArrayOutputStream();) {
			byte[] content = null;
			if (width == null && height == null) {
				content = imageFile.getContent();
			} else {
				content = scale(width, height, bais, baos);
			}
			return new CacheEntry(content, imageFile.getOriginalName(), content.length, MediaType.IMAGE_JPEG);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private static byte[] scale(Integer width, Integer height, ByteArrayInputStream bais, ByteArrayOutputStream baos)
			throws IOException {
		byte[] content;
		BufferedImage original = ImageIO.read(bais);
		BufferedImage resizedImage = null;

		if (width != null && height != null) {

			double imageRatio = (double) original.getWidth() / original.getHeight();
			double resizeRatio = (double) width / height;

			if (imageRatio > resizeRatio) {
				height = (int) (width / imageRatio);
			} else {
				width = (int) (height * imageRatio);
			}
		} else if (width != null) {
			height = (int) (width * (double) original.getHeight() / original.getWidth());
		} else if (height != null) {
			width = (int) (height * (double) original.getWidth() / original.getHeight());
		}

		if (width != null && height != null) {
			resizedImage = new BufferedImage(width, height, original.getType());
			Graphics2D g2d = resizedImage.createGraphics();
			g2d.drawImage(original, 0, 0, width, height, null);
			g2d.dispose();
		}
		ImageIO.write(resizedImage, "jpeg", baos);
		content = baos.toByteArray();
		return content;
	}

	private ImageFile getImageFile(UUID id) {
		return fileService.findById(id).orElseThrow(() -> new ResourceNotFoundException("Image", Map.of("id", id)));
	}

	FileWrapper withCache(UUID id, Integer width, Integer height, Supplier<CacheEntry> imageFileSupplier) {
		String key = buildKey(id, width, height);
		return cacheHelper.retrieveFromCache(key).map(it -> readFromStream(it)).orElseGet(() -> {
			log.info("From db");
			CacheEntry cacheEntry = imageFileSupplier.get();
			cacheHelper.storeInCache(key, writeToStream(cacheEntry));
			return toFileWrapper(cacheEntry);
		});
	}

	private byte[] writeToStream(CacheEntry cacheEntry) {
		try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
				ObjectOutputStream oos = new ObjectOutputStream(baos)) {
			oos.writeObject(cacheEntry);
			return baos.toByteArray();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private FileWrapper readFromStream(byte[] it) {
		try (ByteArrayInputStream bais = new ByteArrayInputStream(it);
				ObjectInputStream ois = new ObjectInputStream(bais)) {
			CacheEntry entry = (CacheEntry) ois.readObject();
			return toFileWrapper(entry);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private static FileWrapper toFileWrapper(CacheEntry entry) {
		return new FileWrapper(new ByteArrayResource(entry.getContent()), entry.getFileName(), entry.getSize(),
				entry.getMediaType());
	}

	private static String buildKey(UUID id, Integer height, Integer width) {
		Map<String, Object> parms = new LinkedHashMap<>();
		parms.put("id", id);
		parms.put("height", height);
		parms.put("width", width);
		return parms.entrySet().stream().filter(it -> it.getValue() != null)
				.map(it -> "%s=%s".formatted(it.getKey(), it.getValue())).collect(Collectors.joining("&"));
	}
}
