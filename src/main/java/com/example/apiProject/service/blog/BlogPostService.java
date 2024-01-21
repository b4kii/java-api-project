package com.example.apiProject.service.blog;

import com.example.apiProject.dto.BlogPostDto;
import com.example.apiProject.dto.BlogPostUserDto;

import java.nio.file.AccessDeniedException;
import java.util.List;

public interface BlogPostService {
    void saveBlogPost(BlogPostDto blogPostDto, String userEmail);

    BlogPostUserDto findBlogPostById(Long id);

    List<BlogPostUserDto> findAllBlogPostsByUserId(Long id);

    List<BlogPostUserDto> findAllBlogPosts();

    void deletePost(Long id);

    void updateBlogPost(Long postId, BlogPostDto updatedPostDto, String userEmail) throws AccessDeniedException;

}
