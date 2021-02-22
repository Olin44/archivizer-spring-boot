package pl.archivizer.services;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import pl.archivizer.exceptions.EntityAlreadyExistException;
import pl.archivizer.exceptions.CustomEntityNotFoundException;
import pl.archivizer.mappers.SimpleMapper;
import pl.archivizer.models.BasicEntity;
import pl.archivizer.payload.request.BasicRequest;
import pl.archivizer.payload.response.CountResponse;
import pl.archivizer.payload.response.CreateSuccessResponse;
import pl.archivizer.payload.response.DeletionSuccessResponse;
import pl.archivizer.payload.response.UpdateSuccessResponse;
import pl.archivizer.payload.response.simple.BasicResponse;
import pl.archivizer.payload.response.simple.ResponsesList;

import java.util.Collections;
import java.util.List;

@Service
public abstract class BasicDictionaryService<Response extends BasicResponse, ResponseList extends ResponsesList,
        Entity extends BasicEntity, Repository extends JpaRepository<Entity, Long>, Request extends BasicRequest> {

    protected final Repository repository;
    protected final SimpleMapper<Response> simpleMapper;
    protected final String entityName;

    public BasicDictionaryService(Repository repository, SimpleMapper<Response> simpleMapper, String entityName) {
        this.repository = repository;
        this.simpleMapper = simpleMapper;
        this.entityName = entityName;
    }

    public ResponseEntity<List<Response>> getAllWithPaginationAndSorting(Integer pageNo, Integer pageSize, String sortBy, Class<Response> responseType) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));

        Page<Response> pagedResult = repository
                .findAll(paging)
                .map(s -> simpleMapper.mapToDTO(s, responseType));

        if (pagedResult.hasContent()) {
            return ResponseEntity.ok(pagedResult.getContent());
        } else {
            return ResponseEntity.ok(Collections.emptyList());
        }
    }

    protected Entity getEntityById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new CustomEntityNotFoundException(id, entityName));
    }

    public ResponseEntity<DeletionSuccessResponse> deleteById(Long id) {
        Entity entity = getEntityById(id);
        repository.delete(entity);
        return ResponseEntity.ok(new DeletionSuccessResponse());
    }

    public ResponseEntity<Response> getById(Long id, Class<Response> responseType) {
        Entity entity = getEntityById(id);
        Response response = simpleMapper
                .map(entity, responseType);
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<ResponseList> getAll(Class<Response> response, Class<ResponseList> responseListType) {
        List<Entity> entities = repository.findAll();
        List<Response> responses = simpleMapper
                .mapList(entities, response);
        ResponseList responseList = simpleMapper.map(responses, responseListType);
        return ResponseEntity.ok(responseList);
    }


    public ResponseEntity<CreateSuccessResponse> create(Request request, Class<Entity> entityType){
        Entity entity = simpleMapper
                .map(request, entityType);
        try{
            repository.save(entity);
        } catch (DataIntegrityViolationException duplicateKeyException){
            throw new EntityAlreadyExistException();
        }
        return new ResponseEntity<>(new CreateSuccessResponse(), HttpStatus.CREATED);
    }

    public ResponseEntity<UpdateSuccessResponse> update(Request request, Long id, Class<Request> entityType) {
        Entity entity = getEntityById(id);
        simpleMapper.map(request, entity);
        repository.save(entity);
        return ResponseEntity.ok(new UpdateSuccessResponse());
    }

    public CountResponse count() {
        return new CountResponse(repository.count());
    }

}
