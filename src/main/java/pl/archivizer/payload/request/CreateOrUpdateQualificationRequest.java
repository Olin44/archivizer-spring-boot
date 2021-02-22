package pl.archivizer.payload.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Setter
@Getter
public class CreateOrUpdateQualificationRequest extends BasicRequest{
    @NotNull
    @NotBlank
    String type;
    @NotNull
    @NotBlank
    String description;
    ArchivizeAfterRequest archivizeAfterRequest;
    private boolean canBeDeleted;
}
