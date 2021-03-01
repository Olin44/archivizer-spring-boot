package pl.archivizer.payload.response;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
@Builder
public class LanguageNameResponse {
    Long id;
    String name;

    public LanguageNameResponse(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
