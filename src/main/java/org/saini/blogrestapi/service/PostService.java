package org.saini.blogrestapi.service;

import org.saini.blogrestapi.payload.PostDto;
import org.saini.blogrestapi.payload.PostResponse;

import java.util.List;

public interface PostService {

    PostDto createPost(PostDto postDto);
    PostResponse findAll(int pageNumber, int pageSize,String sortBy,String sortDir);

    PostDto getPostById(long id);

    PostDto updatePostById(PostDto postDto,long id);

    void deletePostById(long id);
}
