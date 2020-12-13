package pl.archivizer.models;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(	name = "users_details",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "id"),
        })
@RequiredArgsConstructor
@Getter
@Setter
public class UserDetailsData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date editDate;

    @NotNull(message = "Name can't be empty")
    private String name = "Must be set by admin";

    @NotNull(message = "Surname can't be empty")
    private String surname = "Must be set by admin";

    @NotNull(message = "Pesel can't be empty")
    private String pesel = "Must be set by admin";

}
