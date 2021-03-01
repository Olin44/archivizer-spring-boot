package pl.archivizer.payload.response;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
@Builder
public class QualificationTypeResponse {
    Long id;
    String type;

    public QualificationTypeResponse(Long id, String type) {
        this.id = id;
        this.type = type;
    }
}
