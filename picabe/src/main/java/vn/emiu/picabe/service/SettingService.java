package vn.emiu.picabe.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.emiu.picabe.dto.SettingResquest;
import vn.emiu.picabe.entity.Setting;
import vn.emiu.picabe.repository.SettingRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SettingService {

    private final SettingRepository settingRepository;

    public boolean getBooleanSetting(String key, boolean defaultValue) {
        return settingRepository.findByKey(key)
                .map(setting -> Boolean.parseBoolean(setting.getValue()))
                .orElse(defaultValue);
    }

    public int getIntSetting(String key, int defaultValue) {
        return settingRepository.findByKey(key)
                .map(setting -> {
                    try {
                        return Integer.parseInt(setting.getValue());
                    } catch (NumberFormatException e) {
                        return defaultValue;
                    }
                })
                .orElse(defaultValue);
    }

    public String getStringSetting(String key, String defaultValue) {
        return settingRepository.findByKey(key)
                .map(Setting::getValue)
                .orElse(defaultValue);
    }

    public Setting getSettingByKey(String key) {
        return settingRepository.findByKey(key).orElse(null);
    }


    public void saveOrUpdateSettings(List<SettingResquest> settingDtos) {
        List<Setting> updatedSettings = new ArrayList<>();

        for (SettingResquest dto : settingDtos) {
            Setting setting = settingRepository.findByKey(dto.getKey())
                    .orElseGet(() -> Setting.builder()
                            .key(dto.getKey())
                            .value(dto.getValue())
                            .build());

            setting.setValue(dto.getValue());
            updatedSettings.add(setting);
        }

        settingRepository.saveAll(updatedSettings);
    }

    public List<Setting> getAllSettings() {
        return settingRepository.findAll();
    }

    public void deleteSetting(String key) {
        settingRepository.findByKey(key).ifPresent(settingRepository::delete);
    }
}
