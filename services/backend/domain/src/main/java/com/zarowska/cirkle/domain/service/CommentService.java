package com.zarowska.cirkle.domain.service;

import com.zarowska.cirkle.domain.entity.CommentEntity;
import com.zarowska.cirkle.domain.entity.PostEntity;

public interface CommentService {

    CommentEntity save(CommentEntity commentEntity);
}
