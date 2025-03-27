package vn.emiu.picabe.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import vn.emiu.picabe.dto.request.UserRequestDTO;
import vn.emiu.picabe.dto.response.UserResponseDTO;
import vn.emiu.picabe.entity.User;
import vn.emiu.picabe.mapper.UserMapper;
import vn.emiu.picabe.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;


    public UserResponseDTO getUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        return userMapper.toResponseDto(user);
    }

    public Page<UserResponseDTO> getAllUsers(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id"));
        return userRepository.findAll(pageRequest).map(userMapper::toResponseDto);
    }

    public UserResponseDTO createUser(UserRequestDTO userRequestDTO) {
        User user = userMapper.toEntity(userRequestDTO);
        return userMapper.toResponseDto(userRepository.save(user));
    }
}
