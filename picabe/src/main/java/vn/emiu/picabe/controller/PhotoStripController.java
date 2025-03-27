package vn.emiu.picabe.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import vn.emiu.picabe.dto.ApiResponse;
import vn.emiu.picabe.dto.response.PhotoStripResponse;
import vn.emiu.picabe.service.PhotoStripService;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/photo-strips")
@RequiredArgsConstructor
public class PhotoStripController {
    private final PhotoStripService photoStripService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<PhotoStripResponse>>> getUserPhotoStrips() {
        List<PhotoStripResponse> photoStrips = photoStripService.getUserPhotoStrips();
        return ResponseEntity.ok(ApiResponse.<List<PhotoStripResponse>>builder()
                .message("User photo strips retrieved successfully")
                .data(photoStrips)
                .build());
    }

    @PostMapping("/upload")
    public ResponseEntity<ApiResponse<PhotoStripResponse>> savePhotoStrip(@RequestParam("file") MultipartFile file) throws IOException {
        PhotoStripResponse response = photoStripService.savePhotoStrip(file);
        return ResponseEntity.ok(ApiResponse.<PhotoStripResponse>builder()
                .message("Photo strip uploaded successfully")
                .data(response)
                .build());
    }

    @PostMapping("/upload-multiple")
    public ResponseEntity<ApiResponse<List<PhotoStripResponse>>> saveMultiplePhotoStrips(@RequestParam("files") List<MultipartFile> files) throws IOException {
        List<PhotoStripResponse> responses = photoStripService.saveMultiplePhotoStrips(files);
        return ResponseEntity.ok(ApiResponse.<List<PhotoStripResponse>>builder()
                .message("Multiple photo strips uploaded successfully")
                .data(responses)
                .build());
    }
}