package org.saini.blogrestapi.controller;

import ch.qos.logback.core.boolex.EvaluationException;
import jakarta.validation.Valid;
import org.saini.blogrestapi.payload.CommentDto;
import org.saini.blogrestapi.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api")
@RestController
public class CommentController {

    private CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<CommentDto> createComment(
            @PathVariable(value = "postId") long postId,
           @Valid @RequestBody CommentDto commentDto
    ) {

        return new ResponseEntity<CommentDto>(commentService.createComment(postId, commentDto), HttpStatus.CREATED);

    }

    @GetMapping("/posts/{postId}/comments")
    public ResponseEntity<List<CommentDto>> findAllCommentsById(@PathVariable long postId) {
        List<CommentDto> commentDtos = commentService.findAllComments(postId);
        return new ResponseEntity<>(commentDtos, HttpStatus.OK);
    }

    @GetMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<CommentDto> getCommentById(@PathVariable(value = "postId") long postId,
                                                     @PathVariable(value = "commentId") long commentId) {
        CommentDto commentDto = commentService.findCommentById(postId, commentId);
        return new ResponseEntity<>(commentDto, HttpStatus.OK);

    }

    @PutMapping("posts/{postId}/comments/{commentId}")
    public ResponseEntity<CommentDto> updateCommentById(@PathVariable(value = "postId") long postId,
                                                        @PathVariable(value = "commentId") long commentId,
                                                        @RequestBody CommentDto commentDto
    ) {
        return new ResponseEntity<>(commentService.updateCommentById(postId, commentId, commentDto), HttpStatus.OK);
    }

    @DeleteMapping("posts/{postId}/comments/{commentId}")
    public ResponseEntity<String> deleteCommentById(@PathVariable(value = "postId") long postId,
                                                        @PathVariable(value = "commentId") long commentId
    ) {
        return new ResponseEntity<>(commentService.deleteCommentById(postId,commentId), HttpStatus.OK);
    }
}
