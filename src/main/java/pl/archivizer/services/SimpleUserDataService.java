package pl.archivizer.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import pl.archivizer.exceptions.UserNotFoundException;
import pl.archivizer.models.ERole;
import pl.archivizer.models.Role;
import pl.archivizer.models.User;
import pl.archivizer.models.UserDetailsData;
import pl.archivizer.payload.request.ActivateAccountsRequest;
import pl.archivizer.payload.response.SimpleUserData;
import pl.archivizer.payload.response.UserDetailsDataResponse;
import pl.archivizer.payload.response.UsersCountResponse;
import pl.archivizer.repository.RoleRepository;
import pl.archivizer.repository.UserRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class SimpleUserDataService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public SimpleUserDataService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    public ResponseEntity<List<SimpleUserData>> getSimpleUserData() {
        List<User> users = userRepository.findAll();
        return ResponseEntity.ok(users.stream()
                .map(this::convertUserToSimpleUserData)
                .collect(Collectors.toList()));
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

        Page<SimpleUserData> pagedResult = userRepository.findAll(paging).map(this::convertUserToSimpleUserData);

        if (pagedResult.hasContent()) {
            return ResponseEntity.ok(pagedResult.getContent());
        } else {
            return ResponseEntity.ok(Collections.emptyList());
        }
    }

    public UsersCountResponse countUsers() {
        return new UsersCountResponse(userRepository.count());
    }

    public void activateAccounts(ActivateAccountsRequest activateAccountsRequest) {
        List<User> users = userRepository.findByIdIn(activateAccountsRequest.getListOfIdToActivate());
        Role role = roleRepository.findByName(ERole.ROLE_USER).get();
        for (User user : users) {
            if (!user.getRoles().contains(role)) {
                user.getRoles().add(role);
                userRepository.save(user);
            }
        }
    }

    public ResponseEntity<UserDetailsDataResponse> getUserDetails(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
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
            .creationDate(userDetailsData.getCreationDate())
            .editDate(userDetailsData.getEditDate())
            .roles(user.getRoles())
            .build();
    }
}
