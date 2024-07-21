package blog.cirkle.domain.service.impl;

import blog.cirkle.domain.entity.BaseEntity;
import blog.cirkle.domain.entity.participant.User;
import blog.cirkle.domain.entity.resource.Image;
import blog.cirkle.domain.entity.resource.Resource;
import blog.cirkle.domain.entity.resource.Text;
import blog.cirkle.domain.exception.ResourceNotFoundException;
import blog.cirkle.domain.model.request.CreateResourceRequest;
import blog.cirkle.domain.repository.participant.UserRepository;
import blog.cirkle.domain.repository.resource.ResourceRepository;
import blog.cirkle.domain.security.UserContextHolder;
import blog.cirkle.domain.service.FileService;
import blog.cirkle.domain.service.ResourceService;
import java.util.*;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ResourceServiceImpl implements ResourceService {

	private final UserRepository userRepository;
	private final ResourceRepository resourceRepository;
	private final FileService fileService;

	@Override
	public <T extends Resource> T save(T resource) {

		UUID userId = UserContextHolder.getCurrentUser().get().getId();
		User currentUser = userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", Map.of("id", userId)));

		Set<Resource> content = new HashSet<>(resource.getContent());
		resource.setAuthor(currentUser);
		final T savedpost = resourceRepository.save(resource);

		content.forEach(it -> {
			it.setAuthor(currentUser);
			it.getReferredBy().add(savedpost);
		});

		resourceRepository.saveAll(content);
		resource.getContent().addAll(content);
		resourceRepository.save(savedpost);
		resource.getContent().forEach(it -> it.getReferredBy().add(savedpost));
		return savedpost;
	}

	@Override
	public <T extends Resource> T prepareResource(T resource, CreateResourceRequest request) {
		resource.getContent().add(new Text(request.getText()));
		List<Image> imageList = request.getImages().stream().map(fileService::findById).filter(Optional::isPresent)
				.map(Optional::get).map(Image::new).toList();
		resource.getContent().addAll(imageList);
		return resource;
	}

	@Override
	public <T extends Resource> T updateResource(T resource, T update) {
		List<Resource> toDelete = new ArrayList<>();
		resource.getContent().forEach(it -> {

			// TODO!!

		});
		return resource;
	}

	private List<UUID> getImages(Resource resource) {
		return filterContent(Image.class, resource.getContent()).map(BaseEntity::getId).toList();
	}

	private String getTextPart(Resource resource) {
		return filterContent(Text.class, resource.getContent()).findFirst().map(Text::getText).orElse("");

	}

	private <T> Stream<T> filterContent(Class<T> clazz, Collection objects) {
		return objects.stream().filter(it -> it.getClass().isAssignableFrom(clazz)).map(it -> (T) it);
	}

}
