package penelope.corretagem.penelopeapirest.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import penelope.corretagem.penelopeapirest.core.user.User;
import penelope.corretagem.penelopeapirest.data.domain.dto.UserRequest;
import penelope.corretagem.penelopeapirest.data.domain.dto.UserResponse;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    User toUserEntity(UserRequest userRequest);

    UserResponse toUserResponse(User user);

    User updateUserFromRequest(UserRequest userRequest, @MappingTarget User user);
}