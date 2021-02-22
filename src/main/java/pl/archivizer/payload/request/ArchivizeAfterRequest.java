package pl.archivizer.payload.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ArchivizeAfterRequest {
    private int day;
    private int month;
    private int year;

    public ArchivizeAfterRequest(int day, int month, int year) {
        this.day = day;
        this.month = month;
        this.year = year;
    }

    public ArchivizeAfterRequest() {
    }
}
