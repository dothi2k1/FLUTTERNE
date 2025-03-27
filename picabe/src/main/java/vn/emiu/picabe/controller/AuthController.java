package vn.emiu.picabe.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.emiu.picabe.dto.ApiResponse;
import vn.emiu.picabe.dto.request.UserRequestDTO;
import vn.emiu.picabe.dto.request.AuthRequestDTO;
import vn.emiu.picabe.dto.response.AuthResponse;
import vn.emiu.picabe.dto.response.UserResponseDTO;
import vn.emiu.picabe.service.AuthService;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<String>> register(@RequestBody AuthRequestDTO authRequestDTO) {
        String result = authService.register(authRequestDTO);
        return ResponseEntity.ok(ApiResponse.<String>builder()
                .message("User registered successfully")
                .data(result)
                .build());
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponse>> login(@RequestBody AuthRequestDTO authRequestDTO) {
        AuthResponse response = authService.login(authRequestDTO);
        return ResponseEntity.ok(ApiResponse.<AuthResponse>builder()
                .message("Login successful")
                .data(response)
                .build());
    }

    @GetMapping("/profile")
    public ResponseEntity<ApiResponse<UserResponseDTO>> getProfileByToken() {
        UserResponseDTO profile = authService.getProfile();
        return ResponseEntity.ok(ApiResponse.<UserResponseDTO>builder()
                .message("User profile retrieved successfully")
                .data(profile)
                .build());
    }
}