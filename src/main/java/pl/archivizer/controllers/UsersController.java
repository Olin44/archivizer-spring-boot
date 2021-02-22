package pl.archivizer.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.archivizer.payload.request.ActivateAccountsRequest;
import pl.archivizer.payload.response.SimpleUserData;
import pl.archivizer.payload.response.UserDetailsDataResponse;
import pl.archivizer.payload.response.CountResponse;
import pl.archivizer.services.SimpleUserDataService;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/")
public class UsersController {
    SimpleUserDataService simpleUserDataService;

    public UsersController(SimpleUserDataService simpleUserDataService) {
        this.simpleUserDataService = simpleUserDataService;
    }

    @GetMapping("users")
    public ResponseEntity<List<SimpleUserData>> getSimpleUserDataWithPaginationAndSorting(
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "id") String sortBy) {
        return simpleUserDataService.getSimpleUserDataWithPaginationAndSorting(pageNo, pageSize, sortBy);
    }

    @GetMapping("users/count")
    public CountResponse countUsers(){
        return simpleUserDataService.countUsers();
    }

    @PostMapping("users/activateAccounts")
    public void activateAccounts(@RequestBody ActivateAccountsRequest activateAccountsRequest){
        simpleUserDataService.activateAccounts(activateAccountsRequest);
    }

    @GetMapping("users/{id}/details")
    public ResponseEntity<UserDetailsDataResponse> getUserDetails(@PathVariable Long id){
        return simpleUserDataService.getUserDetails(id);
    }

}
