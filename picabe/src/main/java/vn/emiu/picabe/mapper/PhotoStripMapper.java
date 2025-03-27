package vn.emiu.picabe.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import vn.emiu.picabe.dto.response.PhotoStripResponse;
import vn.emiu.picabe.entity.PhotoStrip;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PhotoStripMapper {
    PhotoStripMapper INSTANCE = Mappers.getMapper(PhotoStripMapper.class);

    PhotoStripResponse toResponseDto(PhotoStrip photoStrip);
    List<PhotoStripResponse> toListResponseDto(List<PhotoStrip> photoStrips);
}
