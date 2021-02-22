package pl.archivizer.payload.response;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ArchivizeAfterResponse {
    private int day;
    private int month;
    private int year;

    public ArchivizeAfterResponse(int day, int month, int year) {
        this.day = day;
        this.month = month;
        this.year = year;
    }

    public ArchivizeAfterResponse() {
    }
}
