package blog.cirkle.domain.service;

import blog.cirkle.domain.model.PostContent;

public interface AiGenerationService {

	PostContent cratePost(String blogTopic, String authorName, String postTopic, int imgCount);

}
