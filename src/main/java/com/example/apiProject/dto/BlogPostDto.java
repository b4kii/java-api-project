package com.example.apiProject.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BlogPostDto {
    private Long id;

    @NotEmpty(message = "Title should not be empty")
    private String title;

    @NotEmpty(message = "Content should not be empty")
    @Size(min = 20, message = "Content should have at last 20 characters")
    private String content;
}
