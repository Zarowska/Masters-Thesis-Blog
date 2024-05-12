package blog.cirkle.domain.model;

import lombok.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;

@Value
public class FileWrapper {
	private Resource resource;
	private String fileName;
	private int size;
	private MediaType mediaType;

	public FileWrapper(Resource resource, String fileName, int size, MediaType mediaType) {
		this.resource = resource;
		this.fileName = fileName;
		this.size = size;
		this.mediaType = mediaType;
	}
}
