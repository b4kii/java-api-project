package com.example.apiProject.repository;

import com.example.apiProject.entity.BlogPost;
import com.example.apiProject.dto.BlogPostUserDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BlogPostRepository extends JpaRepository<BlogPost, Long> {

    @Query("SELECT new com.example.apiProject.dto.BlogPostUserDto(" +
            "u.id, u.firstName, u.lastName, u.email, " +
            "bp.id, bp.title, bp.content, bp.createdAt) " +
            "FROM User u JOIN u.blogPosts bp " +
            "ORDER BY bp.createdAt DESC")
    List<BlogPostUserDto> findAllByOrderByCreatedAtDesc();

    @Query("SELECT new com.example.apiProject.dto.BlogPostUserDto(" +
            "u.id, u.firstName, u.lastName, u.email, " +
            "bp.id, bp.title, bp.content, bp.createdAt) " +
            "FROM User u JOIN u.blogPosts bp " +
            "WHERE u.id = :userId")
    List<BlogPostUserDto> findByUserId(@Param("userId") Long userId);

    @Query("SELECT new com.example.apiProject.dto.BlogPostUserDto(" +
            "u.id, u.firstName, u.lastName, u.email, " +
            "bp.id, bp.title, bp.content, bp.createdAt) " +
            "FROM User u JOIN u.blogPosts bp " +
            "WHERE bp.id = :postId")
    BlogPostUserDto findPostById(@Param("postId") Long postId);
}
