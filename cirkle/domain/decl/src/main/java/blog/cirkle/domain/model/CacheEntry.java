package blog.cirkle.domain.model;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.MediaType;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class CacheEntry implements Serializable {
	private byte[] content;
	private String fileName;
	private int size;
	private MediaType mediaType;
}
