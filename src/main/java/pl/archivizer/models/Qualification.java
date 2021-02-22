package pl.archivizer.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "archivization_qualification",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "type"),
        })
@Getter
@Setter
public class Qualification extends BasicEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @NotNull
    private String type;

    private String description;

    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name="archivize_after_id")
    private ArchivizeAfter archivizeAfter;

    private boolean canBeDeleted;

    public Qualification() {}

    public Qualification(@NotBlank @NotNull String type, String description) {
        this.type = type;
        this.description = description;
    }

    public Qualification(@NotBlank @NotNull String type) {
        this.type = type;
    }


}
