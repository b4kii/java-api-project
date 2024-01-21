package com.example.apiProject.service.blog;

import com.example.apiProject.dto.BlogPostDto;
import com.example.apiProject.entity.BlogPost;
import com.example.apiProject.dto.BlogPostUserDto;
import com.example.apiProject.entity.User;
import com.example.apiProject.repository.BlogPostRepository;
import com.example.apiProject.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.util.*;

@Service
public class BlogPostSerivceImpl implements BlogPostService {
    @Autowired
    private BlogPostRepository blogPostRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public void saveBlogPost(BlogPostDto blogPostDto, String userEmail) {
        BlogPost blogPost = new BlogPost();
        blogPost.setTitle(blogPostDto.getTitle());
        blogPost.setContent(blogPostDto.getContent());

        User user = userRepository.findByEmail(userEmail);

        blogPost.setUser(user);

        blogPostRepository.save(blogPost);
    }

    @Override
    public BlogPostUserDto findBlogPostById(Long id) {
        return blogPostRepository.findPostById(id);
    }

//    @Override
//    public List<BlogPost> findAllBlogPostsByUserId(Long id) {
//        return null;
//    }

    @Override
    public List<BlogPostUserDto> findAllBlogPosts() {
        return blogPostRepository.findAllByOrderByCreatedAtDesc();
    }

    @Override
    public void deletePost(Long id) {
        blogPostRepository.deleteById(id);
    }

    @Override
    public void updateBlogPost(Long postId, BlogPostDto updatedPostDto, String userEmail) throws AccessDeniedException {
        Optional<BlogPost> optionalBlogPost = blogPostRepository.findById(postId);

        if (optionalBlogPost.isPresent()) {
            BlogPost existingPost = optionalBlogPost.get();

            if (!existingPost.getUser().getEmail().equals(userEmail)) {
                throw new AccessDeniedException("You are not authorized to update this post");
            }

            existingPost.setTitle(updatedPostDto.getTitle());
            existingPost.setContent(updatedPostDto.getContent());

            blogPostRepository.save(existingPost);
        } else {
            throw new EntityNotFoundException("Post not found with id: " + postId);
        }
    }

    @Override
    public List<BlogPostUserDto> findAllBlogPostsByUserId(Long id) {
        return blogPostRepository.findByUserId(id);
    }
}
