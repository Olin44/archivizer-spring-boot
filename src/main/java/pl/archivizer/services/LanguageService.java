package pl.archivizer.services;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import pl.archivizer.exceptions.ConstrainsViolationsException;
import pl.archivizer.mappers.SimpleMapper;
import pl.archivizer.models.FileWithMetadata;
import pl.archivizer.models.Language;
import pl.archivizer.models.Qualification;
import pl.archivizer.payload.request.CreateOrUpdateLanguageRequest;
import pl.archivizer.payload.response.*;
import pl.archivizer.repository.FileWithMetadataRepository;
import pl.archivizer.repository.LanguageRepository;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class LanguageService extends BasicDictionaryService<LanguageResponse,
        LanguageListResponse, Language, LanguageRepository, CreateOrUpdateLanguageRequest> {

    private final FileWithMetadataRepository fileWithMetadataRepository;
    private final SimpleMapper<FileWithMetadataSmallResponse> simpleMapper;

    public LanguageService(LanguageRepository repository, SimpleMapper<LanguageResponse> simpleMapper, FileWithMetadataRepository fileWithMetadataRepository, SimpleMapper<FileWithMetadataSmallResponse> simpleMapper1) {
        super(repository, simpleMapper, "language");
        this.fileWithMetadataRepository = fileWithMetadataRepository;
        this.simpleMapper = simpleMapper1;
    }

    @Override
    public ResponseEntity<DeletionSuccessResponse> deleteById(Long id) {
        List<FileWithMetadata> fileWithLanguage = fileWithMetadataRepository.findAllByLanguage_Id(id);
        if(fileWithLanguage.isEmpty()){
            return super.deleteById(id);
        } else{
            List<FileWithMetadataSmallResponse> fileWithMetadataSmallResponses = new ArrayList<>();
            for(FileWithMetadata file : fileWithLanguage){
                fileWithMetadataSmallResponses.add(simpleMapper.mapToDTO(file, FileWithMetadataSmallResponse.class));
            }
            throw new ConstrainsViolationsException(fileWithMetadataSmallResponses);
        }
    }

    public ResponseEntity<List<LanguageNameResponse>> getAllWithoutPagination() {
        return ResponseEntity.ok(repository.findAll()
                .stream().map(this::languageToLanguageNameResponse).collect(toList()));
    }

    private LanguageNameResponse languageToLanguageNameResponse(Language language){
        return LanguageNameResponse.builder().id(language.getId()).name(language.getName()).build();
    }
}
