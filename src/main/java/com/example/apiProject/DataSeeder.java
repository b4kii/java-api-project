package com.example.apiProject;

import com.example.apiProject.entity.BlogPost;
import com.example.apiProject.entity.Role;
import com.example.apiProject.entity.User;
import com.example.apiProject.repository.BlogPostRepository;
import com.example.apiProject.repository.RoleRepository;
import com.example.apiProject.repository.UserRepository;
import com.example.apiProject.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Arrays;

@Component
public class DataSeeder implements CommandLineRunner {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private BlogPostRepository blogPostRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private UserService userService;

    @Override
    public void run(String ...args) throws Exception {
        Role adminRole = new Role();
        adminRole.setName("ROLE_ADMIN");
        Role userRole = new Role();
        userRole.setName("ROLE_USER");

        if (roleRepository.count() == 0) {
            roleRepository.saveAll(Arrays.asList(adminRole, userRole));
        }

        User admin = new User();
        admin.setFirstName("Admin");
        admin.setLastName("Admin");
        admin.setEmail("admin@gmail.com");
        admin.setPassword(passwordEncoder.encode("admin123#"));
        admin.setCreatedAt(Instant.now());
        admin.setApiKey(userService.generateApiKey());
        Role existingRole1 = roleRepository.findByName("ROLE_ADMIN");
        admin.setRoles(Arrays.asList(existingRole1));

        User user = new User();
        user.setFirstName("User");
        user.setLastName("User");
        user.setEmail("user@gmail.com");
        user.setPassword(passwordEncoder.encode("admin123#"));
        user.setCreatedAt(Instant.now());
        user.setApiKey(userService.generateApiKey());
        Role existingRole2 = roleRepository.findByName("ROLE_USER");
        user.setRoles(Arrays.asList(existingRole2));

        if (userRepository.count() == 0) {
            userRepository.saveAll(Arrays.asList(admin, user));
        }

        BlogPost post1 = new BlogPost();
        post1.setTitle("Admin post");
        post1.setContent("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed porttitor eros odio, id consequat lorem bibendum quis. Suspendisse eget erat tincidunt, interdum ipsum eget, imperdiet odio. Curabitur consequat turpis mi, in congue nunc vestibulum ut. Sed lobortis nisl accumsan nisi dapibus auctor. Vestibulum nec venenatis nisl, id porttitor eros. Nam est lorem, blandit ut scelerisque id, fringilla non mi. Integer facilisis cursus augue id convallis. Vestibulum vel augue luctus, venenatis ante sed, ornare elit. Cras sagittis malesuada orci, in dictum diam. In eu nisi sed dui sagittis rutrum. Morbi molestie, diam id pulvinar dictum, justo urna bibendum neque, id accumsan purus arcu id enim. Fusce fringilla sollicitudin nunc ac consectetur. Nulla facilisi. In a metus et nisl lacinia mattis laoreet id nibh.");
        post1.setCreatedAt(Instant.now());
        post1.setUser(admin);

        BlogPost post2 = new BlogPost();
        post2.setTitle("User post");
        post2.setContent("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed porttitor eros odio, id consequat lorem bibendum quis. Suspendisse eget erat tincidunt, interdum ipsum eget, imperdiet odio. Curabitur consequat turpis mi, in congue nunc vestibulum ut. Sed lobortis nisl accumsan nisi dapibus auctor. Vestibulum nec venenatis nisl, id porttitor eros. Nam est lorem, blandit ut scelerisque id, fringilla non mi. Integer facilisis cursus augue id convallis. Vestibulum vel augue luctus, venenatis ante sed, ornare elit. Cras sagittis malesuada orci, in dictum diam. In eu nisi sed dui sagittis rutrum. Morbi molestie, diam id pulvinar dictum, justo urna bibendum neque, id accumsan purus arcu id enim. Fusce fringilla sollicitudin nunc ac consectetur. Nulla facilisi. In a metus et nisl lacinia mattis laoreet id nibh.");
        post2.setCreatedAt(Instant.now());
        post2.setUser(user);

        if (blogPostRepository.count() == 0) {
            blogPostRepository.saveAll(Arrays.asList(post1, post2));
        }
    }
}
