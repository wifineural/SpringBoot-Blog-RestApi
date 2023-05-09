package org.saini.blogrestapi.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.saini.blogrestapi.entity.Post;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostResponse {

    private List<PostDto> content;
    private int pageNumber;
    private  int pageSize;
    private  long totalElements;
    private  int totalPages;
    private  boolean last;
}
