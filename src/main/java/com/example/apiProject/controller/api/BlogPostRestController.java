package com.example.apiProject.controller.api;

import com.example.apiProject.dto.BlogPostDto;
import com.example.apiProject.dto.BlogPostUserDto;
import com.example.apiProject.entity.User;
import com.example.apiProject.service.blog.BlogPostService;
import com.example.apiProject.service.user.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class BlogPostRestController {

    @Autowired
    private BlogPostService blogPostService;

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<BlogPostUserDto>> getAllBlogPosts() {
        List<BlogPostUserDto> blogPosts = blogPostService.findAllBlogPosts();
        return ResponseEntity.ok(blogPosts);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BlogPostUserDto> getBlogPostById(@PathVariable Long id) {
        BlogPostUserDto blogPost = blogPostService.findBlogPostById(id);
        if (blogPost == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(blogPost);
    }

    @PostMapping
    public ResponseEntity<Void> createBlogPost(@Valid @RequestBody BlogPostDto blogPostDto, Principal principal, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }

        User user = userService.findUserByApiKey(principal.getName());
        blogPostService.saveBlogPost(blogPostDto, user.getEmail());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBlogPost(@PathVariable Long id) {
        blogPostService.deletePost(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateBlogPost(@PathVariable Long id,
                                               @Valid @RequestBody BlogPostDto blogPostDto,
                                               BindingResult bindingResult,
                                               Principal principal) throws AccessDeniedException {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }
        User user = userService.findUserByApiKey(principal.getName());
        blogPostService.updateBlogPost(id, blogPostDto, user.getEmail());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<List<BlogPostUserDto>> getAllPostsByUserId(@PathVariable Long id) {
        List<BlogPostUserDto> blogPosts = blogPostService.findAllBlogPostsByUserId(id);
        return ResponseEntity.ok(blogPosts);
    }

}
