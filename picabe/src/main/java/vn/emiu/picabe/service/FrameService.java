package vn.emiu.picabe.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import vn.emiu.picabe.dto.request.UserRequestDTO;
import vn.emiu.picabe.dto.response.UserResponseDTO;
import vn.emiu.picabe.entity.Frame;
import vn.emiu.picabe.entity.User;
import vn.emiu.picabe.mapper.UserMapper;
import vn.emiu.picabe.repository.FrameRepository;
import vn.emiu.picabe.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class FrameService {
    private final FrameRepository frameRepository;

    public Page<Frame> getAllFrame(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id"));
        return frameRepository.findAll(pageRequest);
    }

    public Frame creatFrame(Frame frame) {
        return frameRepository.save(frame);
    }


}
