package vn.emiu.picabe.dto.response;

import lombok.Getter;
import lombok.Setter;
import vn.emiu.picabe.entity.BaseEntity;

@Getter
@Setter
public class PhotoStripResponseBasic extends BaseEntity {
    private Long id;
    private String imageUrl;
}
