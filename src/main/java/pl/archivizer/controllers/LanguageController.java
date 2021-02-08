package pl.archivizer.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pl.archivizer.models.Language;
import pl.archivizer.payload.request.CreateOrUpdateLanguageRequest;
import pl.archivizer.payload.response.*;
import pl.archivizer.repository.LanguageRepository;
import pl.archivizer.services.LanguageService;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/")
@RequiredArgsConstructor
public class LanguageController {
    private final LanguageService languageService;

//    @GetMapping("languages")
//    public ResponseEntity<LanguageListResponse> getAll(){
//        return languageService.getAll(LanguageResponse.class, LanguageListResponse.class);
//    }

    @GetMapping("languages")
    public ResponseEntity<List<LanguageResponse>> getWithPaginationAndSorting(
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "id") String sortBy) {
        return languageService.getAllWithPaginationAndSorting(pageNo, pageSize, sortBy, LanguageResponse.class);
    }

    @GetMapping("language/{id}")
    public ResponseEntity<LanguageResponse> getById(@PathVariable Long id){
        return languageService.getById(id, LanguageResponse.class);
    }

    @DeleteMapping("language/{id}")
    public ResponseEntity<DeletionSuccessResponse> deleteById(@PathVariable Long id){
        return languageService.deleteById(id);
    }

    @PostMapping("language")
    public ResponseEntity<CreateSuccessResponse> create(@RequestBody @Validated CreateOrUpdateLanguageRequest request){
        return languageService.create(request, Language.class);
    }

    @PostMapping("language/{id}")
    public ResponseEntity<UpdateSuccessResponse> update(@PathVariable Long id, @RequestBody @Validated CreateOrUpdateLanguageRequest updateRequest){
        return languageService.update(updateRequest, id, CreateOrUpdateLanguageRequest.class);
    }
}
