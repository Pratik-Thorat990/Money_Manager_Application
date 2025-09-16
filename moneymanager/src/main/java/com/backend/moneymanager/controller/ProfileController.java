package com.backend.moneymanager.controller;

import com.backend.moneymanager.dto.AuthDTO;
import com.backend.moneymanager.dto.ProfileDTO;
import com.backend.moneymanager.service.ProfileService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping
//@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;

    // Constructor injection (no Lombok needed)
    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @PostMapping("/register")
    public ResponseEntity<ProfileDTO> registerProfile(@RequestBody ProfileDTO profileDTO)
    {
        ProfileDTO registeredProfile = profileService.registerProfile(profileDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(registeredProfile);
    }

    @GetMapping("/activate")
    public ResponseEntity<String> activateProfile(@RequestParam("token") String token)
    {
        boolean isActivated = profileService.activateProfile(token);
        if(isActivated)
        {
            return ResponseEntity.ok("Profile Activated Successfully");
        }
        else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Activation token not fount or already used");
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody AuthDTO authDTO)
    {
        try{
            if(profileService.isAccountActive(authDTO.getEmail()))
            {

                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of("message", "Account is not active. Please activate your account first."));
            }
            Map <String, Object> response = profileService.authenticateAndGenerateToken(authDTO);
            return ResponseEntity.ok(response);
        }catch(Exception e)
        {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", e.getMessage()));
        }
    }
}
