package pl.archivizer.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pl.archivizer.payload.request.CreateOrUpdateFileWithMetadataRequest;
import pl.archivizer.payload.request.DeleteFilesRequest;
import pl.archivizer.payload.response.*;
import pl.archivizer.services.FilesMetadataService;

import java.io.IOException;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/")
@RequiredArgsConstructor
public class FileController {
    @GetMapping("files")
    public ResponseEntity<List<FileWithMetadataSmallResponse>> getWithPaginationAndSorting(
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "USER") String role){
        return filesMetadataService.getAllWithPaginationAndSorting(pageNo, pageSize, sortBy, FileWithMetadataSmallResponse.class, role);
    }

    private final FilesMetadataService filesMetadataService;

    @GetMapping("file/{id}")
    public ResponseEntity<FileWithMetadataResponse> getById(@PathVariable Long id){
        return filesMetadataService.getById(id);
    }

    @DeleteMapping("file_metadata/{id}")
    public ResponseEntity<DeletionSuccessResponse> deleteById(@PathVariable Long id){
        return filesMetadataService.deleteById(id);
    }

    @PostMapping(value= "file")
    public ResponseEntity<CreateSuccessResponse> create(@Validated CreateOrUpdateFileWithMetadataRequest request) throws IOException {
        return filesMetadataService.create(request);
    }

    @PostMapping("file/{id}")
    public ResponseEntity<UpdateSuccessResponse> update(@PathVariable Long id, @RequestBody @Validated CreateOrUpdateFileWithMetadataRequest request){
        return filesMetadataService.update(request, id);
    }

    @PostMapping("files_to_delete")
    public ResponseEntity<List<FileWithMetadataSmallResponse>> getFilesToDeleteWithPaginationAndSorting(
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "USER") String role){
        return filesMetadataService.getFilesToDeleteWithPaginationAndSorting(pageNo, pageSize, sortBy, FileWithMetadataSmallResponse.class, role);
    }

    @DeleteMapping("delete_files_by_ids")
    public ResponseEntity<DeletionSuccessResponse> deleteByIds(DeleteFilesRequest deleteFilesRequest){
        return filesMetadataService.deleteByIds(deleteFilesRequest);
    }
}
