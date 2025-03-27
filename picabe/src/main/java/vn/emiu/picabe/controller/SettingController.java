package vn.emiu.picabe.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.emiu.picabe.dto.SettingResponse;
import vn.emiu.picabe.dto.SettingResquest;
import vn.emiu.picabe.entity.Setting;
import vn.emiu.picabe.service.SettingService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/admin/settings")
@RequiredArgsConstructor
public class SettingController {

    private final SettingService settingService;

    @GetMapping
    public ResponseEntity<List<SettingResponse>> getAllSettings() {
        List<SettingResponse> settings = settingService.getAllSettings().stream()
                .map(SettingResponse::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(settings);
    }

    @GetMapping("/{key}")
    public ResponseEntity<SettingResponse> getSetting(@PathVariable String key) {
        Setting setting = settingService.getSettingByKey(key);
        if (setting == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(SettingResponse.fromEntity(setting));
    }

    @PutMapping
    public ResponseEntity<String> updateSettings(@RequestBody List<SettingResquest> settingDtos) {
        settingService.saveOrUpdateSettings(settingDtos);
        return ResponseEntity.ok("Settings updated successfully");
    }

    // Xóa setting theo key
    @DeleteMapping("/{key}")
    public ResponseEntity<String> deleteSetting(@PathVariable String key) {
        settingService.deleteSetting(key);
        return ResponseEntity.ok("Setting deleted successfully!");
    }
}
