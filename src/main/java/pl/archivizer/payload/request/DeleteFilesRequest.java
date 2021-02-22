package pl.archivizer.payload.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class DeleteFilesRequest {
    private final List<Long> listOfIdToDelete;

    @JsonCreator
    public DeleteFilesRequest(@JsonProperty("list_of_id_to_activate") List<Long> listOfIdToDelete) {
        this.listOfIdToDelete = listOfIdToDelete;
    }
}
