package blog.cirkle.domain.service.impl;

import blog.cirkle.domain.entity.resource.Post;
import blog.cirkle.domain.repository.resource.PostRepository;
import blog.cirkle.domain.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    @Override
    public Page<Post> findByUserId(UUID userId, Pageable pageable) {
        return postRepository.findByAuthor_Id(userId, pageable);
    }
}
