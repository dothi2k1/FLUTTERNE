package vn.emiu.picabe.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.emiu.picabe.config.CustomJwtDecoder;
import vn.emiu.picabe.config.SecurityHelper;
import vn.emiu.picabe.dto.request.UserRequestDTO;
import vn.emiu.picabe.dto.request.AuthRequestDTO;
import vn.emiu.picabe.dto.response.AuthResponse;
import vn.emiu.picabe.dto.response.UserResponseDTO;
import vn.emiu.picabe.entity.User;
import vn.emiu.picabe.entity.enums.RoleType;
import vn.emiu.picabe.exception.BadRequestException;
import vn.emiu.picabe.mapper.UserMapper;
import vn.emiu.picabe.repository.UserRepository;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final CustomJwtDecoder jwtDecoder;
    private final UserMapper userMapper;
    private final SecurityHelper securityHelper;


    @Transactional
    public String register(AuthRequestDTO authRequestDTO) {
        Optional<User> existingUser = userRepository.findByUsername(authRequestDTO.getUsername());
        if (existingUser.isPresent()) {
            throw new BadRequestException("Username already exists");
        }

        User user = new User();
        user.setUsername(authRequestDTO.getUsername());
        user.setPassword(passwordEncoder.encode(authRequestDTO.getPassword()));
        user.setRole(RoleType.USER);
        userRepository.save(user);
        return "User registered successfully";
    }

    public AuthResponse login(AuthRequestDTO authRequestDTO) {
        User user = userRepository.findByUsername(authRequestDTO.getUsername())
                .orElseThrow(() -> {
                    return new BadRequestException("Incorrect username");
                });

        if (!passwordEncoder.matches(authRequestDTO.getPassword(), user.getPassword())) {
            throw new BadRequestException("Incorrect password");
        }

        String accessToken = jwtDecoder.generateToken(user, true);
        String refreshToken = jwtDecoder.generateToken(user, false);

        return new AuthResponse(accessToken, refreshToken);
    }

    public UserResponseDTO getProfile() {
        User user = securityHelper.getCurrentAccount();
        UserResponseDTO userResponseDTO = userMapper.toResponseDto(user);
        return userResponseDTO;
    }
}
