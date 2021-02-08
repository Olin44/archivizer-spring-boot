package pl.archivizer.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pl.archivizer.models.Qualification;
import pl.archivizer.payload.request.CreateOrUpdateQualificationRequest;
import pl.archivizer.payload.response.*;
import pl.archivizer.services.QualificationService;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/")
@RequiredArgsConstructor
public class QualificationController {

    private final QualificationService qualificationService;

    @GetMapping("qualifications")
    public ResponseEntity<List<QualificationBasicResponse>> getWithPaginationAndSorting(
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "id") String sortBy) {
        return qualificationService.getAllWithPaginationAndSorting(pageNo, pageSize, sortBy, QualificationBasicResponse.class);
    }

    @GetMapping("qualification/{id}")
    public ResponseEntity<QualificationBasicResponse> getById(@PathVariable Long id){
        return qualificationService.getById(id, QualificationBasicResponse.class);
    }

    @DeleteMapping("qualification/{id}")
    public ResponseEntity<DeletionSuccessResponse> deleteById(@PathVariable Long id){
        return qualificationService.deleteById(id);
    }

    @PostMapping("qualification")
    public ResponseEntity<CreateSuccessResponse> create(@RequestBody @Validated CreateOrUpdateQualificationRequest createQualificationRequest){
        return qualificationService.create(createQualificationRequest, Qualification.class);
    }

    @PostMapping("qualification/{id}")
    public ResponseEntity<UpdateSuccessResponse> update(@PathVariable Long id,
                                                        @RequestBody  @Validated CreateOrUpdateQualificationRequest updateRequest){
        return qualificationService.update(updateRequest, id, CreateOrUpdateQualificationRequest.class);
    }


}
