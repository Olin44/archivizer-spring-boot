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
    private ArchivizeAfterResponse archivizeAfterResponse;
    private boolean canBeDeleted;

    public QualificationBasicResponse(Long id, String type, String description, ArchivizeAfterResponse archivizeAfterResponse, boolean canBeDeleted) {
        this.id = id;
        this.type = type;
        this.description = description;
        this.archivizeAfterResponse = archivizeAfterResponse;
        this.canBeDeleted = canBeDeleted;
    }


    public QualificationBasicResponse() {
    }

}
