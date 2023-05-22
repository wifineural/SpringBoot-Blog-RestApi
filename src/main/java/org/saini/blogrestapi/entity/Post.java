package org.saini.blogrestapi.entity;


import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(
        name="post",
        uniqueConstraints = {
                @UniqueConstraint(columnNames ={"title"})
        }
      )
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="title",nullable = false,length = 255)
    private String title;
    @Column(name="Description", nullable = false)
    private String description;
    @Column(name="content",nullable = false)
    private  String content;
@OneToMany(mappedBy = "post", cascade = CascadeType.ALL,orphanRemoval = true)
    private Set<Comment> comments;
}
