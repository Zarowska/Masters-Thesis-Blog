package blog.cirkle.domain.service;

import blog.cirkle.domain.entity.resource.Resource;
import blog.cirkle.domain.model.request.CreateResourceRequest;

public interface ResourceService {

	<T extends Resource> T save(T resource);

	<T extends Resource> T prepareResource(T resource, CreateResourceRequest request);

	<T extends Resource> T updateResource(T resource, T update);
}
