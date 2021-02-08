package pl.archivizer.payload.request;


import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
public class CreateOrUpdateFileWithMetadataRequest {
    public CreateOrUpdateFileWithMetadataRequest(@NotNull Long creatorId,
                                                 @NotNull @NotBlank String format,
                                                 @NotNull @NotBlank String title,
                                                 List<Integer> rolesWithAccessId,
                                                 List<Long> usersWithAccess,
                                                 String type,
                                                 @NotNull Long qualificationId,
                                                 @NotNull Long languageId,
                                                 @NotNull @NotEmpty String description,
                                                 @NotNull String file) {
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
    }

    @NotNull
    private final Long creatorId;

    @NotNull
    @NotBlank
    private final String format;
    @NotNull
    @NotBlank
    private final String title;

    private final List<Integer> rolesWithAccessId;

    private final List<Long> usersWithAccess;

    @NotNull
    @NotBlank
    private final String type;

    @NotNull
    private final Long qualificationId;

    @NotNull
    private final Long languageId;

    @NotNull @NotEmpty
    private final String description;

    @NotNull
    private final String file;






}
