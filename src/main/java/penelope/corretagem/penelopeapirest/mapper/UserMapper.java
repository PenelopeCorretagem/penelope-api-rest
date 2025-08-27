package penelope.corretagem.penelopeapirest.mapper;

import org.mapstruct.Mapper;
import penelope.corretagem.penelopeapirest.dto.UserRequest;
import penelope.corretagem.penelopeapirest.dto.UserResponse;
import penelope.corretagem.penelopeapirest.entity.UserEntity;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserEntity toUserEntity(UserRequest userRequest);

    UserResponse toUserResponse(UserEntity userEntity);
}