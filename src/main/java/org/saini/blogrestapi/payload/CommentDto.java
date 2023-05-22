package org.saini.blogrestapi.payload;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentDto {
    private int id;
    @NotBlank(message = "Name can not be blank")
    private  String name;
    @NotEmpty(message = "Email can not be empty")
    private  String email;
    private String body;
}
