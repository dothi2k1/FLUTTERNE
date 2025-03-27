package vn.emiu.picabe.controller.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import vn.emiu.picabe.dto.request.UserRequestDTO;
import vn.emiu.picabe.dto.response.UserResponseDTO;
import vn.emiu.picabe.service.UserService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/user")
public class UserController {
    private final UserService userService;

    @GetMapping("/{id}")
    public UserResponseDTO getUser(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @GetMapping
    public Page<UserResponseDTO> getAllUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return userService.getAllUsers(page, size);
    }

    @PostMapping
    public UserResponseDTO createUser(@RequestBody UserRequestDTO userRequestDTO) {
        return userService.createUser(userRequestDTO);
    }
}
