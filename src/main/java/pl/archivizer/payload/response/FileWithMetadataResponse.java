package pl.archivizer.payload.response;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import pl.archivizer.payload.response.simple.BasicResponse;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class FileWithMetadataResponse extends BasicResponse {
    @Builder
    public FileWithMetadataResponse(@NotNull Long id, @NotNull Long creatorId, @NotNull @NotBlank String format, @NotNull @NotBlank String title, List<Integer> rolesWithAccessId, List<Long> usersWithAccess, @NotNull @NotBlank String type, @NotNull Long qualificationId, @NotNull Long languageId, @NotNull @NotEmpty String description, @NotNull String file, Date creationDate) {
        this.id = id;
        this.creatorId = creatorId;
        this.format = format;
        this.title = title;
        this.rolesWithAccessId = rolesWithAccessId;
        this.usersWithAccess = usersWithAccess;
        this.type = type;
        this.qualificationId = qualificationId;
        this.languageId = languageId;
        this.description = description;
        this.file = file;
        this.creationDate = creationDate;
    }

    public FileWithMetadataResponse() {
    }

    @NotNull
    private Long creatorId;

    @NotNull
    @NotBlank
    private String format;
    @NotNull
    @NotBlank
    private String title;

    private List<Integer> rolesWithAccessId;

    private List<Long> usersWithAccess;

    @NotNull
    @NotBlank
    private String type;

    @NotNull
    private Long qualificationId;

    @NotNull
    private Long languageId;

    @NotNull @NotEmpty
    private String description;

    @NotNull
    private String file;

    private Date creationDate;

    private boolean canBeDeleted;
}
