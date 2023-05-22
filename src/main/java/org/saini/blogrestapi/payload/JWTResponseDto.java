package org.saini.blogrestapi.payload;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JWTResponseDto {

    String accessToken;
    String tokenType="Bearer";
}
