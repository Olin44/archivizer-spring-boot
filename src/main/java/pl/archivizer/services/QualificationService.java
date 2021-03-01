package pl.archivizer.services;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import pl.archivizer.exceptions.ConstrainsViolationsException;
import pl.archivizer.mappers.SimpleMapper;
import pl.archivizer.models.FileWithMetadata;
import pl.archivizer.models.Qualification;
import pl.archivizer.payload.request.CreateOrUpdateQualificationRequest;
import pl.archivizer.payload.response.*;
import pl.archivizer.repository.FileWithMetadataRepository;
import pl.archivizer.repository.QualificationRepository;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class QualificationService extends BasicDictionaryService<QualificationBasicResponse,
        QualificationsResponse, Qualification, QualificationRepository, CreateOrUpdateQualificationRequest> {

    private final FileWithMetadataRepository fileWithMetadataRepository;

    public QualificationService(QualificationRepository repository, SimpleMapper<QualificationBasicResponse> simpleMapper, FileWithMetadataRepository fileWithMetadataRepository) {
        super(repository, simpleMapper, "qualification");
        this.fileWithMetadataRepository = fileWithMetadataRepository;
    }

    @Override
    public ResponseEntity<DeletionSuccessResponse> deleteById(Long id) {
        List<FileWithMetadata> fileWithEntity = fileWithMetadataRepository.findAllByQualification_Id(id);
        if (fileWithEntity.isEmpty()) {
            return super.deleteById(id);
        } else {
            List<FileWithMetadataSmallResponse> fileWithMetadataSmallResponses = new ArrayList<>();
            for (FileWithMetadata file : fileWithEntity) {
                fileWithMetadataSmallResponses.add(simpleMapper.mapToDTO(file, FileWithMetadataSmallResponse.class));
            }
            throw new ConstrainsViolationsException(fileWithMetadataSmallResponses);
        }
    }

    public ResponseEntity<List<QualificationTypeResponse>> getAllWithoutPagination(){
        return ResponseEntity.ok(repository.findAll().stream().map(this::qualificationToQualificationTypeResponse).collect(toList()));
    }

    private QualificationTypeResponse qualificationToQualificationTypeResponse(Qualification qualification){
        return QualificationTypeResponse.builder().id(qualification.getId()).type(qualification.getType()).build();
    }

}
