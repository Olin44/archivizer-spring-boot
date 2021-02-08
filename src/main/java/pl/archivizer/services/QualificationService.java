package pl.archivizer.services;

import org.springframework.stereotype.Service;
import pl.archivizer.mappers.SimpleMapper;
import pl.archivizer.models.Qualification;
import pl.archivizer.payload.request.CreateOrUpdateQualificationRequest;
import pl.archivizer.payload.response.QualificationBasicResponse;
import pl.archivizer.payload.response.QualificationsResponse;
import pl.archivizer.repository.QualificationRepository;

@Service
public class QualificationService extends BasicDictionaryService<QualificationBasicResponse,
        QualificationsResponse, Qualification, QualificationRepository, CreateOrUpdateQualificationRequest> {

    public QualificationService(QualificationRepository repository, SimpleMapper<QualificationBasicResponse> simpleMapper) {
        super(repository, simpleMapper, "qualification");
    }

}
