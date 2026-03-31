package penelope.corretagem.penelopeapirest.application.useCase.images;

public record UploadImageCommand(byte[] content, String originalFilename) {
}
