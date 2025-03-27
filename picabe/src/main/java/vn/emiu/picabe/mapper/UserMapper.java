package vn.emiu.picabe.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import vn.emiu.picabe.dto.request.UserRequestDTO;
import vn.emiu.picabe.dto.response.UserResponseDTO;
import vn.emiu.picabe.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserResponseDTO toResponseDto(User user);
    User toEntity(UserRequestDTO userRequestDTO);
}
