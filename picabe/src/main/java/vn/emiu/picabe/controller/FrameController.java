package vn.emiu.picabe.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import vn.emiu.picabe.dto.request.UserRequestDTO;
import vn.emiu.picabe.dto.response.UserResponseDTO;
import vn.emiu.picabe.entity.Frame;
import vn.emiu.picabe.service.FrameService;
import vn.emiu.picabe.service.UserService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/frames")
public class FrameController {
    private final FrameService frameService;

    @GetMapping
    public Page<Frame> getAllFrame(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return frameService.getAllFrame(page, size);
    }


    @PostMapping()
    public Frame createFrame(
           Frame frame) {
        return frameService.creatFrame(frame);
    }
}
