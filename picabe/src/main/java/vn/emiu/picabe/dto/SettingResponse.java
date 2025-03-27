package vn.emiu.picabe.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import vn.emiu.picabe.entity.Setting;

@Getter
@Setter
@AllArgsConstructor
public class SettingResponse {
    private String key;
    private String value;
    private String type; // Chỉ thêm type vào DTO, không sửa entity

    // Xác định type từ value
    public static SettingResponse fromEntity(Setting setting) {
        return new SettingResponse(setting.getKey(), setting.getValue(), detectType(setting.getValue()));
    }

    private static String detectType(String value) {
        if (value.equalsIgnoreCase("true") || value.equalsIgnoreCase("false")) {
            return "BOOLEAN";
        }
        try {
            Integer.parseInt(value);
            return "INTEGER";
        } catch (NumberFormatException ignored) {}

        return "STRING";
    }
}
