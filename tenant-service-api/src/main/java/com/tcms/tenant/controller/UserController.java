package com.tcms.tenant.controller;


import com.tcms.tenant.exception.ResourceNotFoundException;
import com.tcms.tenant.model.User;
import com.tcms.tenant.payload.UserProfile;
import com.tcms.tenant.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/{id}/profile")
    public UserProfile getUserProfile(@PathVariable(value = "id") Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));

        UserProfile userProfile = new UserProfile(user.getId(), user.getEmailAddress(), user.getFirstName());

        return userProfile;
    }

    @GetMapping("/hello")
    public String hello() {

        return "Hello";
    }
}
