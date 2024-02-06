package com.zarowska.cirkle.facade.mapper;

import com.zarowska.cirkle.api.model.Comment;
import com.zarowska.cirkle.domain.entity.CommentEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@Component
@RequiredArgsConstructor
public class CommentEntityMapper {

    private final ZoneOffset currentZoneOffset = OffsetDateTime.now().getOffset();

    private final UserEntityMapper userEntityMapper;

    public Comment toDto(CommentEntity entity) {
        Comment comment = new Comment();
        comment.setId(entity.getId());
        comment.setText(entity.getText());
        comment.setImages(entity.getImages().stream()
                .map(postImage -> postImage.getImage())
                .map(it -> "/api/v1/images/%s".formatted(it.getId()))
                .map(URI::create)
                .toList());
        return comment;
    }
}
