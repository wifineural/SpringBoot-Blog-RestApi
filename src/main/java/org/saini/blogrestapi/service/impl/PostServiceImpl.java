package org.saini.blogrestapi.service.impl;

import org.modelmapper.ModelMapper;
import org.saini.blogrestapi.entity.Post;
import org.saini.blogrestapi.exception.ResourceNotFoundException;
import org.saini.blogrestapi.payload.PostDto;
import org.saini.blogrestapi.payload.PostResponse;
import org.saini.blogrestapi.repository.PostRepository;
import org.saini.blogrestapi.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    private PostRepository postRepository;
    private ModelMapper modelMapper;

    @Autowired
    public PostServiceImpl(PostRepository postRepository,ModelMapper modelMapper) {

        this.postRepository = postRepository;
        this.modelMapper=modelMapper;
    }

    @Override
    public PostDto createPost(PostDto postDto) {
        Post post = mapToEntity(postDto);
        Post newPost = postRepository.save(post);
        return mapToDto(newPost);
    }


    @Override
    public PostResponse findAll(int pageNumber, int pageSize, String sortBy,String sortDir) {

        Sort sort=sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() :
                Sort.by(sortBy).descending();
        Pageable pageable=PageRequest.of(pageNumber,pageSize, sort);
        Page<Post> postList = postRepository.findAll((org.springframework.data.domain.Pageable) pageable);
        List<Post> listOfPost=postList.getContent();

        List<PostDto> content= listOfPost.stream().map(this::mapToDto).collect(Collectors.toList());
        PostResponse postResponse=new PostResponse();

        postResponse.setContent(content);
        postResponse.setPageNumber(postList.getNumber());
        postResponse.setPageSize(postList.getSize());
        postResponse.setTotalElements(postList.getTotalElements());
        postResponse.setTotalPages(postList.getTotalPages());
        postResponse.setLast(postList.isLast());
      return postResponse;
    }

    @Override
    public PostDto getPostById(long id) {
        Post post=postRepository.findById(id).orElseThrow(() ->new ResourceNotFoundException("Post","Id",id));
        return mapToDto(post);
    }

    @Override
    public PostDto updatePostById(PostDto postDto,long id) {
        Post post=postRepository.findById(id).orElseThrow(() ->new ResourceNotFoundException("Post","Id",id));
        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());

        Post updatedPost=postRepository.save(post);

        return  mapToDto(updatedPost);
    }

    @Override
    public void deletePostById(long id) {

        Post post=postRepository.findById(id).orElseThrow(() ->new ResourceNotFoundException("Post","Id",id));
        postRepository.delete(post);

    }


    // convert entity to Dto
    private PostDto mapToDto(Post post) {
        PostDto postDto = new PostDto();
        postDto=modelMapper.map(post,PostDto.class);
      /*  postDto.setId(post.getId());
        postDto.setTitle(post.getTitle());
        postDto.setDescription(post.getDescription());
        postDto.setContent(post.getContent());*/
        return postDto;
    }

    private Post mapToEntity(PostDto postDto) {
        Post post = new Post();
       /* post.setId(postDto.getId());
        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());*/
        post=modelMapper.map(postDto,Post.class);
        return post;
    }

}