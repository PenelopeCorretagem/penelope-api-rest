package penelope.corretagem.penelopeapirest.infrastructure.adapter;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.stereotype.Component;
import penelope.corretagem.penelopeapirest.core.exception.IntegrationException;
import penelope.corretagem.penelopeapirest.core.gateway.IImageStorageGateway;

import java.util.Map;

@Component
public class CloudinaryImageGatewayAdapter implements IImageStorageGateway {

    private final Cloudinary cloudinary;

    public CloudinaryImageGatewayAdapter(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    @Override
    public String uploadImage(byte[] imageBytes, String originalFilename) {
        try {
            Map<?, ?> uploadResult = cloudinary.uploader().upload(
                    imageBytes,
                    ObjectUtils.asMap("folder", "imoveis")
            );

            String secureUrl = uploadResult.get("secure_url").toString();
            return optimizeDeliveryUrl(secureUrl);

        } catch (Exception e) {
            throw new IntegrationException("Falha ao fazer upload da imagem: " + originalFilename, e);
        }
    }

    private String optimizeDeliveryUrl(String secureUrl) {
        if (secureUrl == null || secureUrl.isBlank()) {
            return secureUrl;
        }

        String marker = "/image/upload/";
        String optimizedSegment = "/image/upload/f_auto,q_auto/";

        if (secureUrl.contains(optimizedSegment)) {
            return secureUrl;
        }

        return secureUrl.contains(marker)
                ? secureUrl.replace(marker, optimizedSegment)
                : secureUrl;
    }
}