package pl.archivizer.models;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@RequiredArgsConstructor
@Entity
@Table(	name = "language",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "code"),
        })
@Getter
@Setter
public class Language extends BasicEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 3)
    private String code;

    @NotBlank
    @Size(max = 50)
    private String name;
}
