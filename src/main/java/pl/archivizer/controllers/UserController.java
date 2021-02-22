package pl.archivizer.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pl.archivizer.models.User;
import pl.archivizer.payload.request.CreateOrUpdateUserRequest;
import pl.archivizer.payload.response.*;
import pl.archivizer.services.UserService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/")
public class UserController{

   private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @DeleteMapping("user/{id}")
    public ResponseEntity<DeletionSuccessResponse> deleteById(@PathVariable Long id){
        return userService.deleteById(id);
    }

    @PostMapping("user")
    public ResponseEntity<CreateSuccessResponse> create(@RequestBody @Validated CreateOrUpdateUserRequest createRequest){
        return userService.create(createRequest, User.class);
    }

    @PostMapping("user/{id}")
    public ResponseEntity<UpdateSuccessResponse> update(@PathVariable Long id,
                                                        @RequestBody  @Validated CreateOrUpdateUserRequest updateRequest){
        return userService.update(updateRequest, id, CreateOrUpdateUserRequest.class);
    }
}
