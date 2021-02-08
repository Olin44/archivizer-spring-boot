package pl.archivizer.services;

import org.springframework.stereotype.Service;
import pl.archivizer.mappers.SimpleMapper;
import pl.archivizer.models.Language;
import pl.archivizer.payload.request.CreateOrUpdateLanguageRequest;
import pl.archivizer.payload.response.LanguageListResponse;
import pl.archivizer.payload.response.LanguageResponse;
import pl.archivizer.repository.LanguageRepository;

@Service
public class LanguageService extends BasicDictionaryService<LanguageResponse,
        LanguageListResponse, Language, LanguageRepository, CreateOrUpdateLanguageRequest> {

    public LanguageService(LanguageRepository repository, SimpleMapper<LanguageResponse> simpleMapper) {
        super(repository, simpleMapper, "language");
    }
}
