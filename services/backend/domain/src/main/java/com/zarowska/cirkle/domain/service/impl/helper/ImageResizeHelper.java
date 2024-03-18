package com.zarowska.cirkle.domain.service.impl.helper;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import javax.imageio.ImageIO;
import org.springframework.stereotype.Component;

@Component
public class ImageResizeHelper {
	public byte[] resizeImage(String filename, byte[] content, Integer width, Integer height) throws IOException {
		// Read the image from byte[]
		BufferedImage originalImage = ImageIO.read(new ByteArrayInputStream(content));

		int type = originalImage.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : originalImage.getType();

		// If no dimensions provided, return original content
		if (width == null && height == null) {
			return content;
		}

		// If only width is provided
		if (height == null) {
			double ratio = (double) originalImage.getHeight() / originalImage.getWidth();
			height = (int) (width * ratio);
		}

		// If only height is provided
		if (width == null) {
			double ratio = (double) originalImage.getWidth() / originalImage.getHeight();
			width = (int) (height * ratio);
		}

		// Create output image
		BufferedImage resizedImage = new BufferedImage(width, height, type);
		Graphics2D g = resizedImage.createGraphics();
		g.drawImage(originalImage, 0, 0, width, height, null);
		g.dispose();

		// Write the output image to byte[]
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write(resizedImage, getExtension(filename), baos);
		baos.flush();
		byte[] imageInByte = baos.toByteArray();
		baos.close();

		return imageInByte;
	}

	private String getExtension(String filename) {
		String[] tokens = filename.split("\\.(?=[^.]+$)");
		return tokens[tokens.length - 1];
	}
}