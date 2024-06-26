package com.portalevent.repository;

import com.portalevent.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author SonPT
 */

@Repository(CommentRepository.NAME)
public interface CommentRepository extends JpaRepository<Comment, String> {

    public static final String NAME = "BaseCommentRepository";

}
