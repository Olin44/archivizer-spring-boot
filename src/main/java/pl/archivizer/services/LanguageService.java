package pl.archivizer.services;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import pl.archivizer.exceptions.ConstrainsViolationsException;
import pl.archivizer.mappers.SimpleMapper;
import pl.archivizer.models.FileWithMetadata;
import pl.archivizer.models.Language;
import pl.archivizer.payload.request.CreateOrUpdateLanguageRequest;
import pl.archivizer.payload.response.DeletionSuccessResponse;
import pl.archivizer.payload.response.FileWithMetadataSmallResponse;
import pl.archivizer.payload.response.LanguageListResponse;
import pl.archivizer.payload.response.LanguageResponse;
import pl.archivizer.repository.FileWithMetadataRepository;
import pl.archivizer.repository.LanguageRepository;

import java.util.ArrayList;
import java.util.List;

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
}
