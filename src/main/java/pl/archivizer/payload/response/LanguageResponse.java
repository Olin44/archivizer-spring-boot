package pl.archivizer.payload.response;

import lombok.Getter;
import lombok.Setter;
import pl.archivizer.payload.response.simple.BasicResponse;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
public class LanguageResponse extends BasicResponse {
    @NotBlank
    @Size(max = 3)
    private String code;

    @NotBlank
    @Size(max = 50)
    private String name;

}
