package blog.cirkle.domain.model.request;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class CreatePostDto {
	String text;
	List<UUID> images = new ArrayList<>();
}
