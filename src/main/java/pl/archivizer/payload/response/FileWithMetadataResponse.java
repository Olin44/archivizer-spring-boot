package pl.archivizer.payload.response;

import lombok.Builder;
import lombok.Getter;
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
    public FileWithMetadataResponse(@NotNull Long id,
                                    @NotNull UserNameSurnameWithId  creator,
                                    @NotNull @NotBlank String format,
                                    @NotNull @NotBlank String title,
                                    RolesResponse rolesWithAccess,
                                    List<UserNameSurnameWithId> usersWithAccess,
                                    @NotNull @NotBlank String type,
                                    @NotNull QualificationBasicResponse qualification,
                                    @NotNull LanguageResponse language,
                                    @NotNull @NotEmpty String description,
                                    @NotNull String file,
                                    Date creationDate) {
        this.id = id;
        this.creator = creator;
        this.format = format;
        this.title = title;
        this.rolesWithAccess = rolesWithAccess;
        this.usersWithAccess = usersWithAccess;
        this.type = type;
        this.qualification = qualification;
        this.language = language;
        this.description = description;
        this.file = file;
        this.creationDate = creationDate;
    }

    public FileWithMetadataResponse() {
    }

    @NotNull
    private UserNameSurnameWithId creator;
    private RolesResponse rolesWithAccess;

    @NotNull
    @NotBlank
    private String format;
    @NotNull
    @NotBlank
    private String title;

    private List<UserNameSurnameWithId> usersWithAccess;

    @NotNull
    @NotBlank
    private String type;

    private @NotNull QualificationBasicResponse qualification;

    private @NotNull LanguageResponse language;

    @NotNull @NotEmpty
    private String description;

    @NotNull
    private String file;

    private Date creationDate;

    private boolean canBeDeleted;
}
