package pl.archivizer.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import pl.archivizer.exceptions.EntityNotFoundException;
import pl.archivizer.models.ERole;
import pl.archivizer.models.Role;
import pl.archivizer.models.User;
import pl.archivizer.models.UserDetailsData;
import pl.archivizer.payload.request.ActivateAccountsRequest;
import pl.archivizer.payload.response.SimpleUserData;
import pl.archivizer.payload.response.UserDetailsDataResponse;
import pl.archivizer.payload.response.CountResponse;
import pl.archivizer.payload.response.UserNameSurnameWithId;
import pl.archivizer.repository.RoleRepository;
import pl.archivizer.repository.UsersRepository;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Service
public class SimpleUserDataService {
    private final UsersRepository usersRepository;
    private final RoleRepository roleRepository;

    public SimpleUserDataService(UsersRepository usersRepository, RoleRepository roleRepository) {
        this.usersRepository = usersRepository;
        this.roleRepository = roleRepository;
    }

    public ResponseEntity<List<SimpleUserData>> getSimpleUserData() {
        List<User> users = usersRepository.findAll();
        return ResponseEntity.ok(users.stream()
                .map(this::convertUserToSimpleUserData)
                .collect(toList()));
    }

    private SimpleUserData convertUserToSimpleUserData(User user) {
        return SimpleUserData.builder()
                .id(user.getId())
                .login(user.getUsername())
                .email(user.getEmail())
                .isActive(isActive(user.getRoles()))
                .build();
    }

    private boolean isActive(Set<Role> roles) {
        for (Role role : roles) {
            if (role.getName() == ERole.ROLE_USER) {
                return true;
            }
        }
        return false;
    }

    public ResponseEntity<List<SimpleUserData>> getSimpleUserDataWithPaginationAndSorting(
            Integer pageNo, Integer pageSize, String sortBy) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));

        Page<SimpleUserData> pagedResult = usersRepository.findAll(paging).map(this::convertUserToSimpleUserData);

        if (pagedResult.hasContent()) {
            return ResponseEntity.ok(pagedResult.getContent());
        } else {
            return ResponseEntity.ok(Collections.emptyList());
        }
    }

    public CountResponse countUsers() {
        return new CountResponse(usersRepository.count());
    }

    public void activateAccounts(ActivateAccountsRequest activateAccountsRequest) {
        List<User> users = usersRepository.findByIdIn(activateAccountsRequest.getListOfIdToActivate());
        Role role = roleRepository.findByName(ERole.ROLE_USER).get();
        for (User user : users) {
            if (!user.getRoles().contains(role)) {
                user.getRoles().add(role);
                usersRepository.save(user);
            }
        }
    }

    public ResponseEntity<UserDetailsDataResponse> getUserDetails(Long id) {
        User user = usersRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(id, "user"));
        return ResponseEntity.ok(createUserDetailsDataResponse(user));
    }

    public UserDetailsDataResponse createUserDetailsDataResponse(User user) {
        UserDetailsData userDetailsData = user.getUserDetailsData();
        return UserDetailsDataResponse
            .builder()
            .id(user.getId())
            .login(user.getUsername())
            .email(user.getEmail())
            .isActive(isActive(user.getRoles()))
            .name(userDetailsData.getName())
            .surname(userDetailsData.getSurname())
            .pesel(userDetailsData.getPesel())
            .creationDate(LocalDateTime.ofInstant(userDetailsData.getCreationDate().toInstant(), ZoneId.systemDefault()))
            .editDate(LocalDateTime.ofInstant(userDetailsData.getEditDate().toInstant(), ZoneId.systemDefault()))
            .roles(user.getRoles())
            .build();
    }

    public ResponseEntity<List<UserNameSurnameWithId>> getAll() {
        return ResponseEntity.ok(usersRepository.findAll().stream().map(this::mapUserToUserNameSurnameWithId).collect(toList()));
    }

    public UserNameSurnameWithId mapUserToUserNameSurnameWithId(User user){
        return UserNameSurnameWithId.builder()
                .id(user.getId())
                .nameAndSurname(user.getUserDetailsData().getName() + " " + user.getUserDetailsData().getSurname()).build();
    }
}
