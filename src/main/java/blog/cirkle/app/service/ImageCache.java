package blog.cirkle.app.service;

import java.util.UUID;
import org.springframework.core.io.Resource;

public interface ImageCache {
	Resource findByIdAsResource(UUID imageId);
}
