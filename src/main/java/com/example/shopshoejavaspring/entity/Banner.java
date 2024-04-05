package com.example.shopshoejavaspring.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@RequiredArgsConstructor
@Getter
@Setter
@Table(name = "banner")
public class Banner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name ="title")
    private String title;

    @Column(name ="image")
    private String image;

    @Column(name ="content")
    private String content;

    @Column(name ="status")
    private Long status;

    @Column(name ="link")
    private String link;

    @Column(name ="position")
    private String position;

    @Column(name ="created_at")
    private String created_at;

    @Column(name ="updated_at")
    private String updated_at;

}
