package com.enterprise.eams.usermodule.service;

import com.enterprise.eams.usermodule.dtos.ApiResponse;
import com.enterprise.eams.usermodule.dtos.LoginResponseDTO;
import com.enterprise.eams.usermodule.dtos.RegisterRequestDTO;
import com.enterprise.eams.usermodule.dtos.RegisterResponseDTO;
import com.enterprise.eams.usermodule.entity.User;
import com.enterprise.eams.usermodule.enums.UserRole;
import com.enterprise.eams.usermodule.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepo;
    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();


    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

    public User getUserById(Long id) {
        return userRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public User updateUser(Long id, User updatedUser) {
        User user = userRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setName(updatedUser.getName());
        user.setEmail(updatedUser.getEmail());
        user.setPassword(updatedUser.getPassword()); // user.setPassword(encoder.encode(request.getPassword()));
        user.setRole(updatedUser.getRole());

        return userRepo.save(user);
    }

    public void deleteUser(Long id) {
        if (!userRepo.existsById(id)) {
            throw new RuntimeException("User not found");
        }
        userRepo.deleteById(id);
    }

    public RegisterResponseDTO register(RegisterRequestDTO request) {
        if (userRepo.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists");
        }
        String email = request.getEmail().trim().toLowerCase();

        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(encoder.encode(request.getPassword()));
        if (email.endsWith("@chitkara.edu.in")) {
            user.setRole(UserRole.MANAGER);
        } else {
            user.setRole(UserRole.OPERATOR);
        }


        User savedUser = userRepo.save(user);

        RegisterResponseDTO response = new RegisterResponseDTO();
        response.setId(savedUser.getId());
        response.setName(savedUser.getName());
        response.setEmail(savedUser.getEmail());
        response.setRole(savedUser.getRole().name());

        return response;

    }

    public ApiResponse<LoginResponseDTO> login(String email, String password) {

        email=email.trim().toLowerCase();
        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!encoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        LoginResponseDTO data =new LoginResponseDTO(
                user.getId(),
                user.getName(),
                user.getRole().name()
        );
        return new ApiResponse<>("Login Successful",data);
    }
}