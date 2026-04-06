package penelope.corretagem.penelopeapirest.core.gateway;

public interface IImageStorageGateway {
    String uploadImage(byte[] imageBytes, String originalFilename);
}
