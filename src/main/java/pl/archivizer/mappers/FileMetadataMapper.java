package pl.archivizer.mappers;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import pl.archivizer.exceptions.CustomEntityNotFoundException;
import pl.archivizer.exceptions.EntityAlreadyExistException;
import pl.archivizer.exceptions.EntityNotFoundException;
import pl.archivizer.models.*;
import pl.archivizer.payload.request.CreateOrUpdateFileWithMetadataRequest;
import pl.archivizer.payload.response.FileWithMetadataResponse;
import pl.archivizer.repository.*;

import java.util.stream.Collectors;

@Service
public class FileMetadataMapper {
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final QualificationRepository qualificationRepository;
    private final LanguageRepository languageRepository;
    private final FileWithMetadataRepository fileWithMetadataRepository;

    public FileMetadataMapper(RoleRepository roleRepository, UserRepository userRepository, QualificationRepository qualificationRepository, LanguageRepository languageRepository, FileWithMetadataRepository fileWithMetadataRepository) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.qualificationRepository = qualificationRepository;
        this.languageRepository = languageRepository;
        this.fileWithMetadataRepository = fileWithMetadataRepository;
    }

    private String encode(byte[] file){
        return Base64.encodeBase64String(file);
    }

    public FileWithMetadataResponse mapToDTO(FileWithMetadata fileWithMetadata){
        return FileWithMetadataResponse
                .builder()
                .id(fileWithMetadata.getId())
                .creatorId(fileWithMetadata.getCreator().getId())
                .file(encode(fileWithMetadata.getFile()))
                .format(fileWithMetadata.getFormat())
                .description(fileWithMetadata.getDescription())
                .languageId(fileWithMetadata.getLanguage().getId())
                .qualificationId(fileWithMetadata.getQualification().getId())
                .rolesWithAccessId(fileWithMetadata.getRolesWithAccess().stream().map(Role::getId).collect(Collectors.toList()))
                .title(fileWithMetadata.getTitle())
                .usersWithAccess(fileWithMetadata.getUsersWithAccess().stream().map(User::getId).collect(Collectors.toList()))
                .type(fileWithMetadata.getType())
                .build();
    }

    public FileWithMetadata mapToEntityCreate(CreateOrUpdateFileWithMetadataRequest request) {
        byte[] file = Base64.decodeBase64(request.getFile());
        User user = userRepository.findById(request.getCreatorId())
                .orElseThrow(() -> new EntityNotFoundException(request.getCreatorId(), "user"));

        FileWithMetadata fileWithMetadata = new FileWithMetadata();
        fileWithMetadata.setCreator(user);
        fileWithMetadata.setFile(file);

        if(! fileWithMetadataRepository.existsByTitle(request.getTitle())){
            fileWithMetadata.setTitle(request.getTitle());
        } else {
            throw new EntityAlreadyExistException("file", request.getTitle());
        }

        fileWithMetadata.setType("image");
        fileWithMetadata.setFormat(request.getFormat());
        fileWithMetadata.setRolesWithAccess(roleRepository.findByIdIn(request.getRolesWithAccessId()));
        fileWithMetadata.setUsersWithAccess(userRepository.findByIdIn(request.getUsersWithAccess()));

        Qualification qualification = qualificationRepository
                .findById(request.getQualificationId())
                .orElseThrow(() -> new CustomEntityNotFoundException(request.getQualificationId(), "qualification"));

        Language language = languageRepository
                .findById(request.getLanguageId())
                .orElseThrow(() -> new CustomEntityNotFoundException(request.getLanguageId(), "language"));
        fileWithMetadata.setFormat(request.getFormat());
        fileWithMetadata.setLanguage(language);
        fileWithMetadata.setQualification(qualification);
        fileWithMetadata.setDescription(request.getDescription());
        return fileWithMetadata;
    }
}
