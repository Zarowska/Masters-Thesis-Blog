package blog.cirkle.domain.facade.impl;

import blog.cirkle.domain.entity.resource.Post;
import blog.cirkle.domain.facade.PostFacade;
import blog.cirkle.domain.model.CreatePostDto;
import blog.cirkle.domain.model.PostDto;
import blog.cirkle.domain.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PostFacadeImpl implements PostFacade {

    private final PostService postService;

    @Override
    public Page findByUserId(UUID userId, Pageable pageable) {
        Page<Post> posts = postService.findByUserId(userId, pageable);

        return posts.map(post -> {
            PostDto postDto = new PostDto();
            return postDto;
        });

    }

    @Override
    public PostDto createOne(UUID userId, CreatePostDto request) {
        return null;
    }
}
