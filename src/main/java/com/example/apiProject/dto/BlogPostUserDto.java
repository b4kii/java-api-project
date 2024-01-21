package com.example.apiProject.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BlogPostUserDto {
    private Long userId;
    private String firstName;
    private String lastName;
    private String email;

    private Long blogPostId;
    private String title;
    private String content;
    private Instant createdAt;

    public String getFormattedCreatedAt() {
        LocalDateTime localDateTime = LocalDateTime.ofInstant(createdAt, ZoneId.systemDefault());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return localDateTime.format(formatter);
    }
}
