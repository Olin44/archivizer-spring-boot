package pl.archivizer.payload.response;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;
import pl.archivizer.payload.response.simple.ResponsesList;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
@Builder
@Validated
public class LanguageListResponse extends ResponsesList {
    List<LanguageResponse> languages = new ArrayList<>();

    public LanguageListResponse(List<LanguageResponse> languages) {
        this.languages = languages;
    }
}
