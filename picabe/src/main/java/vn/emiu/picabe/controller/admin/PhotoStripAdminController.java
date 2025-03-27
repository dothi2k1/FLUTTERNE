package vn.emiu.picabe.controller.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import vn.emiu.picabe.dto.ApiResponse;
import vn.emiu.picabe.dto.response.PhotoStripResponse;
import vn.emiu.picabe.service.PhotoStripService;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/admin/photo-strips")
@RequiredArgsConstructor
public class PhotoStripAdminController {
    private final PhotoStripService photoStripService;

    @GetMapping
    public ResponseEntity<ApiResponse<Page<PhotoStripResponse>>> getAllPhotoStrips(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
            ) {

        Pageable pageable = PageRequest.of(page, size);
        Page<PhotoStripResponse> photoStrips = photoStripService.getAllPhotoStrips(pageable);

        return ResponseEntity.ok(ApiResponse.<Page<PhotoStripResponse>>builder()
                .message("User photo strips retrieved successfully")
                .data(photoStrips)
                .build());
    }



}