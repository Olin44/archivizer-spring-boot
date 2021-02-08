package pl.archivizer.payload.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Setter
@Getter
public class UpdateQualificationRequest {
    @NotNull
    @NotBlank
    String type;

    String description;
}
