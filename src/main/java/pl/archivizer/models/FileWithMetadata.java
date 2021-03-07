package pl.archivizer.models;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
@Setter
@Getter
@Entity
@ToString
@Table(	name = "file_metadata",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "title"),
        })
public class FileWithMetadata {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne()
    @JoinColumn(name = "creator_id")
    private User creator;

    private String format;

    private String title;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(	name = "roles_with_access",
            joinColumns = @JoinColumn(name = "file_metadata_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<Role> rolesWithAccess;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(	name = "users_with_access",
            joinColumns = @JoinColumn(name = "file_metadata_id"),
            inverseJoinColumns = @JoinColumn(name = "users_id"))
    private List<User> usersWithAccess;

    private String type;

    @ManyToOne
    private FileWithMetadata parentFile;

    @OneToMany(mappedBy="parentFile")
    private Collection<FileWithMetadata> connectedFiles;

    @ManyToOne()
    @JoinColumn(name="qualification_id")
    Qualification qualification;

    @ManyToOne()
    @JoinColumn(name="language_id")
    private Language language;

    private String description;

    @Lob
    @Column(columnDefinition="bytea")
    private byte[] file;

    private Date creationDate;

    private boolean canBeDeleted;

}
