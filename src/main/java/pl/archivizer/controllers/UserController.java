package pl.archivizer.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.archivizer.payload.response.SimpleUserData;
import pl.archivizer.services.SimpleUserDataService;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/")
public class UserController {

    SimpleUserDataService simpleUserDataService;

    public UserController(SimpleUserDataService simpleUserDataService) {
        this.simpleUserDataService = simpleUserDataService;
    }

    @GetMapping("users")
    public ResponseEntity<List<SimpleUserData>> getSimpleUserData(){
        return simpleUserDataService.getSimpleUserData();
    }

    @GetMapping("users1")
    public ResponseEntity<List<SimpleUserData>> getSimpleUserDataWithPaginationAndSorting(
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "id") String sortBy) {
        return simpleUserDataService.getSimpleUserDataWithPaginationAndSorting(pageNo, pageSize, sortBy);
    }

    @GetMapping("users/count")
    public Long countUsers(){
        return simpleUserDataService.countUsers();
    }
}
