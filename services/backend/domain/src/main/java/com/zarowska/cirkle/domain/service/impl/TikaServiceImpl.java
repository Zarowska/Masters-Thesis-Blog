package com.zarowska.cirkle.domain.service.impl;

import com.zarowska.cirkle.domain.service.TikaService;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import lombok.RequiredArgsConstructor;
import org.apache.tika.Tika;
import org.apache.tika.mime.MediaType;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TikaServiceImpl implements TikaService {

	private final Tika tika;

	@Override
	public String detectMediaType(byte[] content) {
		try (InputStream inputStream = new ByteArrayInputStream(content)) {
			return tika.detect(inputStream);
		} catch (Exception e) {
			return MediaType.OCTET_STREAM.toString();
		}
	}
}
