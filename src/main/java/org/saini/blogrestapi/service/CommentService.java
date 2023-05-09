package org.saini.blogrestapi.service;

import org.saini.blogrestapi.payload.CommentDto;

import java.util.List;

public interface CommentService {
    CommentDto createComment(long postId, CommentDto commentDto);

    List<CommentDto> findAllComments(long postId);

    CommentDto findCommentById(long postId,long commentId);

    CommentDto updateCommentById(long postId,long commentid,CommentDto commentDto);

    String deleteCommentById(long postId,long commentId);
}
