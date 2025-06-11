package com.example.springboot.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Article {
    private Integer id;
    private String title;
    private String img;
    private String description;
    private String content;
    private String time;
}
