package pl.archivizer.payload.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
public class CreateOrUpdateLanguageRequest extends BasicRequest {
    @NotBlank
    @Size(max = 3)
    private String code;

    @NotBlank
    @Size(max = 50)
    private String name;
}
