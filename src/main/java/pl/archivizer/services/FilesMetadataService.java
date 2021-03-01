package pl.archivizer.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import pl.archivizer.exceptions.CustomEntityNotFoundException;
import pl.archivizer.exceptions.CustomEntityNotFoundListException;
import pl.archivizer.exceptions.EntityNotFoundException;
import pl.archivizer.mappers.FileMetadataMapper;
import pl.archivizer.mappers.SimpleMapper;
import pl.archivizer.models.*;
import pl.archivizer.payload.request.CreateOrUpdateFileWithMetadataRequest;
import pl.archivizer.payload.request.DeleteFilesRequest;
import pl.archivizer.payload.request.FilesPaginationRequest;
import pl.archivizer.payload.response.*;
import pl.archivizer.repository.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FilesMetadataService {
    private final FileMetadataMapper fileMetadataMapper;
    private final FileWithMetadataRepository fileWithMetadataRepository;
    private final SimpleMapper<FileWithMetadataSmallResponse> simpleMapper;

    public ResponseEntity<CreateSuccessResponse> create(@Validated CreateOrUpdateFileWithMetadataRequest request){
        FileWithMetadata fileWithMetadata = fileMetadataMapper.mapToEntityCreate(request);
        fileWithMetadataRepository.save(fileWithMetadata);
        return ResponseEntity.ok(new CreateSuccessResponse());
    }

    public ResponseEntity<FileWithMetadataResponse> getById(Long id) {
        FileWithMetadata fileWithMetadata = fileWithMetadataRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException(id, "file"));
        return ResponseEntity.ok(fileMetadataMapper.mapToDTO(fileWithMetadata));
    }


    public ResponseEntity<UpdateSuccessResponse> update(CreateOrUpdateFileWithMetadataRequest request, Long id) {
        if(fileWithMetadataRepository.existsById(id)){
            FileWithMetadata fileWithMetadata = fileMetadataMapper.mapToEntityCreate(request);
            fileWithMetadata.setId(id);
            fileWithMetadataRepository.save(fileWithMetadata);
            return ResponseEntity.ok(new UpdateSuccessResponse());
        }
        else {
            throw new CustomEntityNotFoundException(id, "file");
        }
    }

    public ResponseEntity<DeletionSuccessResponse> deleteById(Long id) {
        if(! fileWithMetadataRepository.existsById(id)){
            fileWithMetadataRepository.deleteById(id);
            return ResponseEntity.ok(new DeletionSuccessResponse());
        }
        else {
            throw new CustomEntityNotFoundException(id, "file");
        }
    }

    public ResponseEntity<List<FileWithMetadataSmallResponse>> getAllWithPaginationAndSorting(Integer pageNo, Integer pageSize, String sortBy, Class<FileWithMetadataSmallResponse> fileWithMetadataSmallResponseClass, List<ERole> roles) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));

        Page<FileWithMetadataSmallResponse> pagedResult = fileWithMetadataRepository
                .findAllDistinctByUsersWithAccess_Roles_NameIn(roles, paging)
                .map(this::mapEntityToDTO);

        if (pagedResult.hasContent()) {
            return ResponseEntity.ok(pagedResult.getContent());
        } else {
            return ResponseEntity.ok(Collections.emptyList());
        }
    }

    public ResponseEntity<List<FileWithMetadataSmallResponse>> getFilesToDeleteWithPaginationAndSorting(Integer pageNo, Integer pageSize, String sortBy, Class<FileWithMetadataSmallResponse> fileWithMetadataSmallResponseClass, String role) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        Page<FileWithMetadataSmallResponse> pagedResult = fileWithMetadataRepository
                .findAllByUsersWithAccess_Roles_NameAndCanBeDeletedTrue(ERole.valueOf(role), paging)
                .map(this::mapEntityToDTO);

        if (pagedResult.hasContent()) {
            return ResponseEntity.ok(pagedResult.getContent());
        } else {
            return ResponseEntity.ok(Collections.emptyList());
        }
    }

    public ResponseEntity<DeletionSuccessResponse> deleteByIds(DeleteFilesRequest deleteFilesRequest) {
        List<CustomEntityNotFoundException> customEntityNotFoundExceptionList = new ArrayList<>();
        for(Long id : deleteFilesRequest.getListOfIdToDelete()){
            if(! fileWithMetadataRepository.existsById(id)){
                fileWithMetadataRepository.deleteById(id);
            }
            else {
                customEntityNotFoundExceptionList.add(new CustomEntityNotFoundException(id, "file"));
            }
        }
        if(!customEntityNotFoundExceptionList.isEmpty()){
            throw new CustomEntityNotFoundListException(customEntityNotFoundExceptionList);
        } else {
            return ResponseEntity.ok(new DeletionSuccessResponse());
        }
    }

    public ResponseEntity<List<FileWithMetadataSmallResponse>> getAllWithPaginationAndSorting2(FilesPaginationRequest request, Class<FileWithMetadataSmallResponse> fileWithMetadataSmallResponseClass) {
        Pageable paging = PageRequest.of(request.getPageNo(), request.getPageSize(), Sort.by(request.getSortBy()));

        List<ERole> roles = request.getRoles().stream().map(ERole::valueOf).collect(Collectors.toList());

        Page<FileWithMetadataSmallResponse> pagedResult = fileWithMetadataRepository
                .findAllDistinctByUsersWithAccess_Roles_NameIn(roles, paging)
                .map(s -> simpleMapper.mapToDTO(s, fileWithMetadataSmallResponseClass));

        if (pagedResult.hasContent()) {
            return ResponseEntity.ok(pagedResult.getContent());
        } else {
            return ResponseEntity.ok(Collections.emptyList());
        }
    }

    public CountResponse count(List<ERole> roles) {
        return new CountResponse(fileWithMetadataRepository.countAllDistinctByUsersWithAccess_Roles_NameIn(roles));
    }

    private FileWithMetadataSmallResponse mapEntityToDTO(FileWithMetadata fileWithMetadata){
        return new FileWithMetadataSmallResponse(
                fileWithMetadata.getId(),
                fileWithMetadata.getCreator().getUserDetailsData().getName() + " " + fileWithMetadata.getCreator().getUserDetailsData().getSurname(),
                fileWithMetadata.getFormat(),
                fileWithMetadata.getTitle());
    }
}

