package vn.emiu.picabe.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ImageUploadService {

    private final RestTemplate restTemplate;
    @Value("${upload.url}")
    private String uploadUrl;

    @Value("${upload.api-key}")
    private String apiKey;

    public String uploadImage(MultipartFile file) throws IOException {
        String response = restTemplate.exchange(
                uploadUrl,
                HttpMethod.POST,
                createMultipartBody(List.of(file)),
                String.class
        ).getBody();
        return extractSingleImageUrl(response);
    }

    public List<String> uploadListImage(List<MultipartFile> files) throws IOException {
        String response = restTemplate.exchange(
                uploadUrl,
                HttpMethod.POST,
                createMultipartBody(files),
                String.class
        ).getBody();
        return extractAllImageUrls(response);
    }

    private HttpEntity<Object> createMultipartBody(List<MultipartFile> files) throws IOException {
        MultiValueMap<String, Object> formData = new LinkedMultiValueMap<>();
        for (MultipartFile file : files) {
            ByteArrayResource byteArrayResource = new ByteArrayResource(file.getInputStream().readAllBytes()) {
                @Override
                public String getFilename() {
                    return file.getOriginalFilename();
                }
            };
            formData.add("images", byteArrayResource);
        }
        formData.add("apikey", apiKey);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        return new HttpEntity<>(formData, headers);
    }

    private String extractSingleImageUrl(String response) {
        return extractAllImageUrls(response).stream().findFirst().orElse(null);
    }

    private List<String> extractAllImageUrls(String response) {
        List<String> imageUrls = new ArrayList<>();
        try {
            JsonNode filesNode = new ObjectMapper().readTree(response).path("files");
            filesNode.forEach(fileNode -> imageUrls.add(fileNode.path("imageUrl").asText()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return imageUrls;
    }
}
