package penelope.corretagem.penelopeapirest.data.domain.dto.EstateCreateDTO;

import org.springframework.web.multipart.MultipartFile;

public record ImageUploadRequest(
        MultipartFile file
) {}
