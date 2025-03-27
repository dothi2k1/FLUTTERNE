package vn.emiu.picabe.dto.response;

import lombok.Getter;
import lombok.Setter;
import vn.emiu.picabe.entity.BaseEntity;
import vn.emiu.picabe.entity.PhotoStrip;

import java.util.List;

@Getter
@Setter
public class UserResponseDTO extends BaseEntity {
    private Long id;
    private String name;
    private String email;
    private String username;
    private List<PhotoStripResponseBasic> photoStrips;
}
