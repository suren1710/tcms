package com.tcms.tenant.controller;

import com.tcms.tenant.utils.Constants;
import com.tcms.tenant.exception.TenantServiceException;
import com.tcms.tenant.model.Role;
import com.tcms.tenant.model.RoleName;
import com.tcms.tenant.model.Tenant;
import com.tcms.tenant.model.User;
import com.tcms.tenant.payload.ApiResponse;
import com.tcms.tenant.payload.JwtAuthenticationResponse;
import com.tcms.tenant.payload.LoginRequest;
import com.tcms.tenant.payload.SignUpRequest;
import com.tcms.tenant.repository.RoleRepository;
import com.tcms.tenant.repository.TenantRepository;
import com.tcms.tenant.repository.UserRepository;
import com.tcms.tenant.security.JwtTokenProvider;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Collections;
import java.util.Date;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    TenantRepository tenantRepository;


    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtTokenProvider tokenProvider;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        //TODO: We have to add password cryptography here. Not sure yet
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsernameOrEmail(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.generateToken(authentication);
        return ResponseEntity.ok(new JwtAuthenticationResponse(jwt));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {

        if(tenantRepository.existsByDomainName(signUpRequest.getDomainName())) {
            return new ResponseEntity(new ApiResponse(false, "Username is already taken!"),
                    HttpStatus.BAD_REQUEST);
        }

        if(userRepository.existsByEmailAddress(signUpRequest.getEmail())) {
            return new ResponseEntity(new ApiResponse(false, "Email Address already in use!"),
                    HttpStatus.BAD_REQUEST);
        }

        // Creating tenant's account
        Date thirtyDaysFromNow = DateUtils.addDays(new Date(), 30);

        Tenant tenantData = new Tenant(signUpRequest.getFirstName(), signUpRequest.getDomainName(),
                signUpRequest.getSubscriptionId(), thirtyDaysFromNow);
        tenantRepository.save(tenantData);

        Tenant tenant = tenantRepository.findByDomainName(
                signUpRequest.getDomainName()).orElseThrow(() -> new TenantServiceException("Something went wrong!!"));

        // Creating user for the tenant with ADMIN role
        User user = new User(signUpRequest.getEmail(),
                signUpRequest.getFirstName(),
                signUpRequest.getLastName(),
                passwordEncoder.encode(signUpRequest.getPassword()),
                tenant.getId(),
                Constants.ACTIVE_STATUS,
                Constants.LOCAL_LOGIN_TYPE);

        Role userRole = roleRepository.findByName(RoleName.ADMIN)
                .orElseThrow(() -> new TenantServiceException("User Role not set."));

        user.setRoles(Collections.singleton(userRole));
        User result = userRepository.save(user);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/users/{id}")
                .buildAndExpand(result.getId()).toUri();

        return ResponseEntity.created(location).body(new ApiResponse(true, "User registered successfully"));


    }
}
