package com.zarowska.cirkle.domain.model;

import java.io.Serializable;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class FileSnapshot implements Serializable {
	private String originalName;
	private String mediaType;
	private byte[] content;

	public FileSnapshot() {
	}

	public FileSnapshot(String originalName, String mediaType, byte[] content) {
		this.originalName = originalName;
		this.mediaType = mediaType;
		this.content = content;
	}
}
