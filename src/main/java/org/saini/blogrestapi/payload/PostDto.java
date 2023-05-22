package org.saini.blogrestapi.payload;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Set;

//@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostDto {
    private long id;

   // @Min(value = 2,message = "Title should have at two characters")
    private String title;
    @NotEmpty(message = "Description can not be empty")
    private String description;
    private  String content;
    private Set<CommentDto> comments;
}
