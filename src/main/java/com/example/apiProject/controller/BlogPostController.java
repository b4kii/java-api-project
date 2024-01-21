package com.example.apiProject.controller;

import com.example.apiProject.dto.BlogPostDto;
import com.example.apiProject.dto.BlogPostUserDto;
import com.example.apiProject.service.blog.BlogPostService;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.security.Principal;
import java.util.List;

@Controller
@Hidden
public class BlogPostController {

    @Autowired
    BlogPostService blogPostService;

    @GetMapping("/")
    public String showHome(Model model) {
        List<BlogPostUserDto> allBlogPosts = blogPostService.findAllBlogPosts();
        model.addAttribute("blogPosts", allBlogPosts);
        return "index";
    }

    @GetMapping("/posts/create")
    public String createTicket(Model model) {
        BlogPostDto blogPost = new BlogPostDto();
        model.addAttribute("blogPost", blogPost);
        return "post/create";
    }

    @PostMapping("/posts")
    public String storePost(@Valid @ModelAttribute("blogPost") BlogPostDto blogPostDto,
                            BindingResult result,
                            Model model,
                            Principal principal) {
        if (result.hasErrors()) {
            model.addAttribute("blogPost", blogPostDto);
            return "post/create";
        }

        String userEmail = principal.getName();

        blogPostService.saveBlogPost(blogPostDto, userEmail);

        return "redirect:/";
    }

    @GetMapping("/posts/{id}")
    public String showSinglePost(@PathVariable Long id, Model model) {
        BlogPostUserDto blogPost = blogPostService.findBlogPostById(id);

        model.addAttribute("blogPost", blogPost);

        return "post/single-post";
    }

    @PostMapping("/posts/delete/{id}")
    public String deletePost(@PathVariable Long id) {
        blogPostService.deletePost(id);
        return "redirect:/";
    }

    @PostMapping("/posts/update/{id}")
    public String updatePost(@Valid @ModelAttribute("blogPost") BlogPostDto blogPostDto,
                             @PathVariable Long id,
                             BindingResult result,
                             Model model,
                             Principal principal
    ) throws AccessDeniedException {
        if (result.hasErrors()) {
            return "posts/update";
        }

        blogPostService.updateBlogPost(id, blogPostDto, principal.getName());
        return "redirect:/posts/" + id;
    }

    @GetMapping("/posts/update/{id}")
    public String getPostToUpdate(@PathVariable Long id, Model model) {
        BlogPostUserDto blogPost = blogPostService.findBlogPostById(id);

        model.addAttribute("blogPost", blogPost);
        return "post/update";
    }

    @GetMapping("/posts/users/{id}")
    public String showAllPosts(@PathVariable Long id, Model model) {
        List<BlogPostUserDto> blogPosts = blogPostService.findAllBlogPostsByUserId(id);
        model.addAttribute("blogPosts", blogPosts);

        return "post/user-posts";
    }
}
