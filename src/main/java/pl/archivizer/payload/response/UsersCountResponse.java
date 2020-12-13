package pl.archivizer.payload.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Getter
@Setter
public class UsersCountResponse {
    private final Long usersCount;
}
