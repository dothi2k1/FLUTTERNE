package vn.emiu.picabe.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import vn.emiu.picabe.config.SecurityHelper;
import vn.emiu.picabe.dto.response.PhotoStripResponse;
import vn.emiu.picabe.entity.PhotoStrip;
import vn.emiu.picabe.entity.User;
import vn.emiu.picabe.exception.UnauthorizedException;
import vn.emiu.picabe.mapper.PhotoStripMapper;
import vn.emiu.picabe.repository.PhotoStripRepository;
import vn.emiu.picabe.utils.ImageUploadService;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PhotoStripService {
    private final PhotoStripRepository photoStripRepository;
    private final SecurityHelper securityHelper;
    private final ImageUploadService imageUploadService;
    private final PhotoStripMapper photoStripMapper;
    private final SettingService settingService;

//    @Transactional
    public Page<PhotoStripResponse> getAllPhotoStrips(Pageable pageable) {
        Pageable sortedPageable = PageRequest.of(
                pageable.getPageNumber(),
                pageable.getPageSize(),
                pageable.getSort().and(Sort.by("id").descending()) // Luôn thêm sort by id
        );

        return photoStripRepository.findAll(sortedPageable).map(photoStripMapper::toResponseDto);
    }




    public List<PhotoStripResponse> getUserPhotoStrips() {
        Long userId = SecurityHelper.getCurrentAccountId();
        return photoStripMapper.toListResponseDto(photoStripRepository.findByUserId(userId));
    }

    public PhotoStripResponse savePhotoStrip(MultipartFile file) throws IOException {

        boolean allowUploadWithoutLogin = settingService.getBooleanSetting("allowUploadWithoutLogin", false);

        User user = null;
        try {
            user = securityHelper.getCurrentAccount();
        } catch (Exception e) {
            // Không làm gì nếu không lấy được user (user chưa đăng nhập)
        }

        if (user == null && !allowUploadWithoutLogin) {
            throw new UnauthorizedException("Bạn cần đăng nhập để upload ảnh.");
        }

        String imageUrl = imageUploadService.uploadImage(file);

        PhotoStrip photoStrip = new PhotoStrip();
        if (user != null) {
            photoStrip.setUser(user);
        }
        photoStrip.setImageUrl(imageUrl);

        PhotoStrip photoStripSaved = photoStripRepository.save(photoStrip);
        return photoStripMapper.toResponseDto(photoStripSaved);
    }


    public List<PhotoStripResponse> saveMultiplePhotoStrips(List<MultipartFile> files) throws IOException {
        User user = securityHelper.getCurrentAccount();
        List<String> imageUrls = imageUploadService.uploadListImage(files);

        List<PhotoStrip> photoStrips = imageUrls.stream().map(url -> {
            PhotoStrip photoStrip = new PhotoStrip();
            photoStrip.setUser(user);
            photoStrip.setImageUrl(url);
            return photoStrip;
        }).toList();
        return photoStripMapper.toListResponseDto(photoStripRepository.saveAll(photoStrips));
    }
}
