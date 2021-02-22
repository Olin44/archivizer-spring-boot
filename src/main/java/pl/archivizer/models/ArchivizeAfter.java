package pl.archivizer.models;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "archivize_after")
@Getter
@Setter
@RequiredArgsConstructor
public class ArchivizeAfter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int day;
    private int month;
    private int year;
}
