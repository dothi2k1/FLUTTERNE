package vn.emiu.picabe.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import vn.emiu.picabe.entity.Setting;

@Getter
@Setter
@AllArgsConstructor
public class SettingResquest {
    private String key;
    private String value;
    private String type;
}
