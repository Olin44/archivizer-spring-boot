package pl.archivizer.payload.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import pl.archivizer.payload.response.simple.BasicResponse;

@Getter
@Setter
public class QualificationBasicResponse extends BasicResponse {

    private String type;
    private String description;

    public QualificationBasicResponse() {
    }

    public QualificationBasicResponse(Long id, String type, String description) {
        this.id = id;
        this.type = type;
    }


}
