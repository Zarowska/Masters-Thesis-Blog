package blog.cirkle.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.springframework.http.MediaType;

@Converter(autoApply = true)
public class MediaTypeConverter implements AttributeConverter<MediaType, String> {

	@Override
	public String convertToDatabaseColumn(MediaType mediaType) {
		return mediaType.toString();
	}

	@Override
	public MediaType convertToEntityAttribute(String dbMediaType) {
		return MediaType.parseMediaType(dbMediaType);
	}
}