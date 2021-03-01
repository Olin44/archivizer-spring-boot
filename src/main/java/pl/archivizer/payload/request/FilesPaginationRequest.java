package pl.archivizer.payload.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.List;

@Setter
@Getter
@RequiredArgsConstructor
public class FilesPaginationRequest {
    @NotNull
    private final Integer pageNo;
    @NotNull
    private final Integer pageSize;
    @NotNull
    private final String sortBy;
    @NotNull
    private final List<String> roles;

}
