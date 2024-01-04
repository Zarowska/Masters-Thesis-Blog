package com.zarowska.cirkle.domain.model.mutators.impl;

import com.zarowska.cirkle.domain.entity.FileInfoEntity;
import com.zarowska.cirkle.domain.model.mutators.FileContentMutator;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import javax.imageio.ImageIO;

public class ImageScale extends FileContentMutator {
	private final int width;
	private final int height;

	public ImageScale(int width, int height) {
		this.width = width;
		this.height = height;
	}

	@Override
	public boolean canApply(FileInfoEntity source) {
		return source.getMediaType().getType().equals("image");
	}

	@Override
	public byte[] apply(FileInfoEntity fileInfoEntity) {
		try (ByteArrayInputStream bais = new ByteArrayInputStream(fileInfoEntity.getContent().getContent())) {
			BufferedImage originalImage = ImageIO.read(bais);
			Image scaledImage = originalImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
			BufferedImage bufferedScaledImage = new BufferedImage(scaledImage.getWidth(null),
					scaledImage.getHeight(null), originalImage.getType());
			bufferedScaledImage.getGraphics().drawImage(scaledImage, 0, 0, null);
			try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
				ImageIO.write(bufferedScaledImage, fileInfoEntity.getMediaType().getSubtype(), baos);
				baos.flush();
				return baos.toByteArray();
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public String getSuffix() {
		return "scaled_%dx%d";
	}
}
