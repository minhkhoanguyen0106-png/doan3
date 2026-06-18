package com.example.expensebackend.Config;

import com.example.expensebackend.Entity.Role;
import com.example.expensebackend.Entity.User;
import com.example.expensebackend.Repository.RoleRepository;
import com.example.expensebackend.Repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(RoleRepository roleRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        Role userRole = roleRepository.findByRoleName("USER").orElseGet(() -> {
            Role role = new Role();
            role.setRoleName("USER");
            return roleRepository.save(role);
        });

        Role adminRole = roleRepository.findByRoleName("ADMIN").orElseGet(() -> {
            Role role = new Role();
            role.setRoleName("ADMIN");
            return roleRepository.save(role);
        });

        if (!userRepository.findByEmail("admin@gmail.com").isPresent()) {
            User admin = new User();
            admin.setName("System Admin");
            admin.setEmail("admin@gmail.com");
            admin.setPassword(passwordEncoder.encode("123456"));
            admin.setRole(adminRole);
            admin.setPhoneNumber("0123456789");
            admin.setAddress("System");
            admin.setStatus("ACTIVE");
            userRepository.save(admin);
        }
    }
}