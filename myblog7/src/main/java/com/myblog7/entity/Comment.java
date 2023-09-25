package com.myblog7.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.persistence.Id;
import javax.persistence.Table;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="comment")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String body;

    // Many Comments can belong to One Post
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "post_id",nullable = false)
    private Post post;

    // Constructors, getters, and setters (omitted for brevity)

}

