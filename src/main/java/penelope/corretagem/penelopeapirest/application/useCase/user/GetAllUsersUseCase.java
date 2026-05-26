package penelope.corretagem.penelopeapirest.application.useCase.user;

import org.springframework.cache.annotation.Cacheable;
import penelope.corretagem.penelopeapirest.core.user.repository.IUserRepository;
import penelope.corretagem.penelopeapirest.application.dto.UserResponse;
import penelope.corretagem.penelopeapirest.config.CacheNames;
import penelope.corretagem.penelopeapirest.core.exception.DomainValidationException;

import java.util.List;
import java.util.stream.Collectors;

public class GetAllUsersUseCase {

    private static final int DEFAULT_PAGE = 1;
    private static final int DEFAULT_PAGE_SIZE = 10;

    private final IUserRepository userRepository;

    public GetAllUsersUseCase(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Cacheable(value = CacheNames.USERS, key = "#page + ':' + #pageSize")
    public List<UserResponse> execute(Integer page, Integer pageSize) {
        int safePage = page == null ? DEFAULT_PAGE : page;
        int safePageSize = pageSize == null ? DEFAULT_PAGE_SIZE : pageSize;

        if (safePage < 1) {
            throw new DomainValidationException("O parâmetro page deve ser maior ou igual a 1");
        }

        if (safePageSize < 1) {
            throw new DomainValidationException("O parâmetro pageSize deve ser maior ou igual a 1");
        }

        int offset = (safePage - 1) * safePageSize;

        return userRepository.findAll(offset, safePageSize).stream()
                .map(user -> new UserResponse(
                        user.getId(), user.getName(), user.getEmail(),
                        user.getCpf(), user.getDateBirth(), user.getMonthlyIncome(),
                        user.getPhone(), user.getCreci(), user.getAccessLevel().toExternalValue(), user.isActive()
                )).collect(Collectors.toList());
    }
}
