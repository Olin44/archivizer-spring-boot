package pl.archivizer.payload.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ActivateAccountsRequest {
    private final List<Long> listOfIdToActivate;

    @JsonCreator
    public ActivateAccountsRequest(@JsonProperty("list_of_id_to_activate") List<Long> listOfIdToActivate) {
        this.listOfIdToActivate = listOfIdToActivate;
    }
}
