package penelope.corretagem.penelopeapirest.mapper;

import org.springframework.web.bind.annotation.Mapping;
import penelope.corretagem.penelopeapirest.dto.UserRequest;
import penelope.corretagem.penelopeapirest.dto.UserResponse;
import penelope.corretagem.penelopeapirest.entity.UserEntity;

public class UserMapper {

    public static UserEntity toUserEntity(UserRequest userRequest){
        UserEntity userEntity = new UserEntity();

        userEntity.setNome(userRequest.getNome());
        userEntity.setCpf(userRequest.getCpf());
        userEntity.setEmail(userRequest.getEmail());
        userEntity.setDtNascimento(userRequest.getDtNascimento());
        userEntity.setRendaMensal(userRequest.getRendaMensal());

        return userEntity;
    }

    public static UserEntity toUserEntityWithId(UserRequest userRequest){
        UserEntity userEntity = new UserEntity();

        userEntity.setNome(userRequest.getNome());
        userEntity.setCpf(userRequest.getCpf());
        userEntity.setEmail(userRequest.getEmail());
        userEntity.setDtNascimento(userRequest.getDtNascimento());
        userEntity.setRendaMensal(userRequest.getRendaMensal());

        return userEntity;
    }

    public static UserResponse toUserResponse(UserEntity userEntity){
        UserResponse userResponse = new UserResponse();

        userResponse.setNome(userEntity.getNome());
        userResponse.setCpf(userEntity.getCpf());
        userResponse.setEmail(userEntity.getEmail());
        userResponse.setDtNascimento(userEntity.getDtNascimento());
        userResponse.setRendaMensal(userEntity.getRendaMensal());

        return userResponse;
    }
}
