package pl.archivizer.mappers;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Service;
import pl.archivizer.exceptions.CustomEntityNotFoundException;
import pl.archivizer.exceptions.EntityAlreadyExistException;
import pl.archivizer.exceptions.EntityNotFoundException;
import pl.archivizer.models.*;
import pl.archivizer.payload.request.CreateOrUpdateFileWithMetadataRequest;
import pl.archivizer.payload.response.*;
import pl.archivizer.repository.*;

import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Service
public class FileMetadataMapper {
    private final RoleRepository roleRepository;
    private final UsersRepository usersRepository;
    private final QualificationRepository qualificationRepository;
    private final LanguageRepository languageRepository;
    private final FileWithMetadataRepository fileWithMetadataRepository;
    private final SimpleMapper simpleMapper;

    public FileMetadataMapper(RoleRepository roleRepository, UsersRepository usersRepository, QualificationRepository qualificationRepository, LanguageRepository languageRepository, FileWithMetadataRepository fileWithMetadataRepository, SimpleMapper simpleMapper) {
        this.roleRepository = roleRepository;
        this.usersRepository = usersRepository;
        this.qualificationRepository = qualificationRepository;
        this.languageRepository = languageRepository;
        this.fileWithMetadataRepository = fileWithMetadataRepository;
        this.simpleMapper = simpleMapper;
    }

    private String encode(byte[] file){
        return Base64.encodeBase64String(file);
    }

    private UserNameSurnameWithId mapUserToUserToUserNameAndSurnameWithId(User user){
        return UserNameSurnameWithId.builder().creator(user.getUserDetailsData().getName() + " " + user.getUserDetailsData().getSurname()).id(user.getId()).build();
    }

    public FileWithMetadataResponse mapToDTO(FileWithMetadata fileWithMetadata){
        return FileWithMetadataResponse
                .builder()
                .id(fileWithMetadata.getId())
                .creator(mapUserToUserToUserNameAndSurnameWithId(fileWithMetadata.getCreator()))
                .file(encode(fileWithMetadata.getFile()))
                .format(fileWithMetadata.getFormat())
                .description(fileWithMetadata.getDescription())
                .language((LanguageResponse) simpleMapper.mapToDTO(fileWithMetadata.getLanguage(), LanguageResponse.class))
                .qualification((QualificationBasicResponse) simpleMapper.mapToDTO(fileWithMetadata.getQualification(), QualificationBasicResponse.class))
                .rolesWithAccess(new RolesResponse(fileWithMetadata.getRolesWithAccess()))
                .title(fileWithMetadata.getTitle())
                .usersWithAccess(fileWithMetadata.getUsersWithAccess().stream().map(this::mapUserToUserToUserNameAndSurnameWithId).collect(toList()))
                .type(fileWithMetadata.getType())
                .build();
    }

    public FileWithMetadata mapToEntityCreate(CreateOrUpdateFileWithMetadataRequest request) {
        byte[] file = Base64.decodeBase64(request.getFile());
        User user = usersRepository.findById(request.getCreatorId())
                .orElseThrow(() -> new EntityNotFoundException(request.getCreatorId(), "user"));

        FileWithMetadata fileWithMetadata = new FileWithMetadata();
        fileWithMetadata.setCreator(user);
        fileWithMetadata.setFile(file);

        if(! fileWithMetadataRepository.existsByTitle(request.getTitle())){
            fileWithMetadata.setTitle(request.getTitle());
        } else {
            throw new EntityAlreadyExistException("file", request.getTitle());
        }

        fileWithMetadata.setType(request.getType());
        fileWithMetadata.setFormat(request.getFormat());
        fileWithMetadata.setRolesWithAccess(roleRepository.findByIdIn(request.getRolesWithAccessId()));
        fileWithMetadata.setUsersWithAccess(usersRepository.findByIdIn(request.getUsersWithAccess()));

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

    public FileWithMetadata mapToEntityUpdate(CreateOrUpdateFileWithMetadataRequest request) {
        byte[] file = Base64.decodeBase64(request.getFile());
        User user = usersRepository.findById(request.getCreatorId())
                .orElseThrow(() -> new EntityNotFoundException(request.getCreatorId(), "user"));

        FileWithMetadata fileWithMetadata = new FileWithMetadata();
        fileWithMetadata.setCreator(user);
        fileWithMetadata.setFile(file);
        fileWithMetadata.setType(request.getType());
        fileWithMetadata.setTitle(request.getTitle());
        fileWithMetadata.setFormat(request.getFormat());
        fileWithMetadata.setRolesWithAccess(roleRepository.findByIdIn(request.getRolesWithAccessId()));
        fileWithMetadata.setUsersWithAccess(usersRepository.findByIdIn(request.getUsersWithAccess()));
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
