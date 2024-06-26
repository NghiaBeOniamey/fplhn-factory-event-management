package com.portalevent.core.organizer.eventDetail.repository;

import com.portalevent.core.approver.eventdetail.model.response.AedCommentEventDetailResponse;
import com.portalevent.core.common.TokenFindRequest;
import com.portalevent.core.organizer.eventDetail.model.response.OedCommentEventDetailResponse;
import com.portalevent.core.organizer.eventDetail.model.response.OedCommentResponse;
import com.portalevent.entity.Comment;
import com.portalevent.repository.CommentRepository;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface OedCommentRepository extends CommentRepository {
    @Query(value = """
            SELECT c.id AS id, p.email AS name, c.comment AS content, p.avatar AS avatar, c.created_date AS created_date, 
                        CASE WHEN NOT EXISTS (SELECT * FROM comment AS c2 WHERE c2.reply_id  = c.id) THEN 0 ELSE 1 END AS is_reply  
                        FROM comment AS c  
                        JOIN participant AS p ON c.participant_id = p.id  
                        WHERE c.reply_id IS NULL   
                        AND c.event_id = :idEvent  
                         ORDER BY c.created_date DESC 
            """,
            countQuery = "SELECT COUNT(c.id) FROM comment AS c WHERE reply_id IS NULL",
            nativeQuery = true)
    Page<OedCommentResponse> getCommentsByIdEvent(@Param("idEvent") String id, Pageable pageable );

    @Query(value = """
            SELECT c.id AS id, p.email AS name, c.comment AS content, p.avatar AS avatar, c.created_date AS created_date 
                        FROM comment AS c  
                        JOIN participant AS p ON c.participant_id = p.id  
                        WHERE c.reply_id = :idComment   
                         ORDER BY c.created_date DESC
            """,
            countQuery = "SELECT COUNT(c.id) FROM comment AS c WHERE reply_id IS NULL",
            nativeQuery = true)
    Page<OedCommentResponse> getReplyByIdComment(@Param("idComment") String id, Pageable pageable );

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM comment AS c " +
            "WHERE c.id = :id " +
            "OR c.reply_id = :id", nativeQuery = true)
    void deleteCommentById(@Param("id") String id);

    @Query(value = """
            SELECT a.id AS id, a.user_id as user_id,
            a.last_modified_date AS last_modified_date, a.comment, a.reply_id 
            FROM comment a 
            WHERE a.reply_id IS NULL AND a.event_id = :eventId 
            ORDER BY a.last_modified_date DESC
            """, countQuery = """
            SELECT count(*)
            FROM comment a 
            WHERE a.reply_id IS NULL AND a.event_id = :eventId 
            ORDER BY a.last_modified_date DESC
            """, nativeQuery = true)
    Page<OedCommentEventDetailResponse> getAllCommentByEventId(@Param("eventId") String eventId, Pageable pageable );

    @Query(value = """
            SELECT a.id AS id, a.user_id as user_id, 
            a.last_modified_date AS last_modified_date, a.comment, a.reply_id
            FROM comment a  
            WHERE a.reply_id IS NOT NULL AND a.event_id = :eventId AND a.reply_id = :replyId 
            ORDER BY a.last_modified_date ASC; 
            """, nativeQuery = true)
    List<OedCommentEventDetailResponse> getReplyCommentByReplyIdAndEventId(@Param("eventId") String eventId, @Param("replyId") String replyId);

    @Query(value = """
            SELECT * FROM comment WHERE reply_id = :replyId
            """, nativeQuery = true)
    List<Comment> getReplyCommentByReplyId(@Param("replyId") String replyId);

    @Query(value = """
            SELECT a.id AS id, a.user_id as user_id, 
            a.last_modified_date AS last_modified_date, a.comment, a.reply_id
            FROM comment a  
            WHERE a.reply_id IS NOT NULL AND a.event_id = :eventId AND a.reply_id IN :listId 
            ORDER BY a.last_modified_date ASC; 
            """, nativeQuery = true)
    List<OedCommentResponse> getReplyCommentByReplyIdAndEventId(@Param("eventId") String eventId, @Param("listId") List<String> listReplyId );

    Page<Comment> findByEventIdAndReplyIdIsNullOrderByLastModifiedDateDesc(String eventId, Pageable pageable);

}
