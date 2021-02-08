package pl.archivizer.payload.response;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import pl.archivizer.payload.response.simple.ResponsesList;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
@Builder
public class QualificationsResponse extends ResponsesList {
    List<QualificationBasicResponse> qualifications = new ArrayList<>();

    public QualificationsResponse(List<QualificationBasicResponse> qualifications) {
        this.qualifications = qualifications;
    }
}
