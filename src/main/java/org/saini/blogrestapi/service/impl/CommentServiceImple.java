package org.saini.blogrestapi.service.impl;

import org.modelmapper.ModelMapper;
import org.saini.blogrestapi.entity.Comment;
import org.saini.blogrestapi.entity.Post;
import org.saini.blogrestapi.exception.BlogApiException;
import org.saini.blogrestapi.exception.ResourceNotFoundException;
import org.saini.blogrestapi.payload.CommentDto;
import org.saini.blogrestapi.repository.CommentRepository;
import org.saini.blogrestapi.repository.PostRepository;
import org.saini.blogrestapi.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommentServiceImple implements CommentService {


    private CommentRepository commentRepository;
    private PostRepository postRepository;
    private ModelMapper modelMapper;

    public  CommentServiceImple(CommentRepository commentRepository,PostRepository postRepository,ModelMapper modelMapper){
        this.commentRepository=commentRepository;
        this.postRepository=postRepository;
        this.modelMapper=modelMapper;
    }


    @Override
    public CommentDto createComment(long postId, CommentDto commentDto) {
        Comment comment=this.mapToEntity(commentDto);
        Post post=postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post","id", postId));

        comment.setPost(post);
      Comment commentResponse=  commentRepository.save(comment);

      return  this.mapToDTO(commentResponse);

    }

    @Override
    public List<CommentDto> findAllComments(long postId) {

        List<Comment> commentList=commentRepository.findByPostId(postId);
        List<CommentDto> commentDtos=new ArrayList<>();
        commentList.forEach((comment) -> {
            commentDtos.add(mapToDTO(comment));
                });
        return commentDtos;
    }

    @Override
    public CommentDto findCommentById(long postId, long commentId) {
       // return commentRepository.findById();
        Post post=postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post","id", postId));
        Comment comment=commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment","id",commentId) );
        if(!comment.getPost().getId().equals(post.getId()))
            throw new BlogApiException(HttpStatus.BAD_REQUEST,"Comment does not belong to any post");

        return mapToDTO(comment);
    }

    @Override
    public CommentDto updateCommentById(long postId, long commentid,CommentDto commentDto) {
        Post post=postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post","id", postId));
        Comment comment=commentRepository.findById(commentid).orElseThrow(() -> new ResourceNotFoundException("Comment","id",commentid) );
        if(!comment.getPost().getId().equals(post.getId()))
            throw new BlogApiException(HttpStatus.BAD_REQUEST,"Comment does not belong to any post");

        comment.setName(commentDto.getName());
        comment.setEmail(commentDto.getEmail());
        comment.setBody(commentDto.getBody());
        Comment commentResponse=commentRepository.save(comment);

        return mapToDTO(commentResponse);
    }

    @Override
    public String deleteCommentById(long postId, long commentId) {

        Post post=postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post","id", postId));
        Comment comment=commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment","id",commentId) );
        if(!comment.getPost().getId().equals(post.getId()))
            throw new BlogApiException(HttpStatus.BAD_REQUEST,"Comment does not belong to any post");

        commentRepository.delete(comment);

        return "Comment deleted Successfully";
    }

    public CommentDto mapToDTO(Comment comment){
    //    return new CommentDto(comment.getId(), comment.getName(), comment.getEmail(), comment.getBody());
        CommentDto commentDto=modelMapper.map(comment,CommentDto.class);
        return  commentDto;
    }

    public Comment mapToEntity(CommentDto commentDto){

        Comment comment= new Comment();
        comment=modelMapper.map(commentDto,Comment.class);
       /* comment.setId(commentDto.getId());
        comment.setName(commentDto.getName());
        comment.setEmail(commentDto.getEmail());
        comment.setBody(commentDto.getBody());*/
        return comment;
    }
}
