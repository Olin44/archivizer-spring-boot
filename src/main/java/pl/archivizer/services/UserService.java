package pl.archivizer.services;

import org.springframework.stereotype.Service;
import pl.archivizer.mappers.SimpleMapper;
import pl.archivizer.models.User;
import pl.archivizer.payload.request.BasicRequest;
import pl.archivizer.payload.request.CreateOrUpdateUserRequest;
import pl.archivizer.payload.response.UserDetailsDataResponse;
import pl.archivizer.payload.response.simple.ResponsesList;
import pl.archivizer.repository.UserRepository;

@Service
public class UserService extends BasicDictionaryService<UserDetailsDataResponse, ResponsesList, User, UserRepository, CreateOrUpdateUserRequest> {
    public UserService(UserRepository repository, SimpleMapper<UserDetailsDataResponse> simpleMapper, String entityName) {
        super(repository, simpleMapper, entityName);
    }
}
