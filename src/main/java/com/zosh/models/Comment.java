package com.zosh.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String content;

    // One User Can do Multiple comments on a particular post so we use @ManyToOne (One user do Many Comments)
    @ManyToOne
    private User user;

    // Multiple user can like multiple comments
    @ManyToMany
    private List<User> liked = new ArrayList<>();

    private LocalDateTime createdAt;
}
