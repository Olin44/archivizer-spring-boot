package pl.archivizer.payload.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import pl.archivizer.payload.response.simple.BasicResponse;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter

public class FileWithMetadataSmallResponse extends BasicResponse {
    @NotNull
    private String creator;
    @NotNull
    @NotBlank
    private String format;
    @NotNull
    @NotBlank
    private String title;

    @Builder
    public FileWithMetadataSmallResponse(@NotNull Long id, @NotNull String creator, @NotNull @NotBlank String format, @NotNull @NotBlank String title) {
        this.creator = creator;
        this.format = format;
        this.title = title;
        this.id = id;
    }

    public FileWithMetadataSmallResponse() {
    }
}
