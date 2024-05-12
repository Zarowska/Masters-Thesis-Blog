package blog.cirkle.domain.entity.resource;

import blog.cirkle.domain.converter.MediaTypeConverter;
import blog.cirkle.domain.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.io.IOException;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@Entity
@Table(name = "image_files")
@NoArgsConstructor
public class ImageFile extends BaseEntity {

	@Column(name = "original_name")
	private String originalName;

	@Column(name = "media_type")
	@Convert(converter = MediaTypeConverter.class)
	private MediaType mediaType;

	@Column(name = "content", columnDefinition = "bytea")
	private byte[] content;

	public ImageFile(String originalName, MediaType media_type) {
		this.originalName = originalName;
		this.mediaType = media_type;
	}

	public ImageFile(MultipartFile file) throws IOException {
		this.originalName = file.getOriginalFilename();
		this.mediaType = MediaType.valueOf(file.getContentType());
		this.content = file.getBytes();
	}
}
