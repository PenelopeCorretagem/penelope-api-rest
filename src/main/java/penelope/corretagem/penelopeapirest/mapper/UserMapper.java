package penelope.corretagem.penelopeapirest.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import penelope.corretagem.penelopeapirest.data.domain.dto.UserRequest;
import penelope.corretagem.penelopeapirest.data.domain.dto.UserResponse;
import penelope.corretagem.penelopeapirest.data.domain.entity.UserEntity;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    UserEntity toUserEntity(UserRequest userRequest);

    UserResponse toUserResponse(UserEntity userEntity);

    UserEntity updateUserFromRequest(UserRequest userRequest, UserEntity userEntity);
}