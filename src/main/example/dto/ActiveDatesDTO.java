package main.example.dto;

import java.time.LocalDate;
import com.fasterxml.jackson.annotation.JsonFormat;

public class ActiveDatesDTO {
    private LocalDate start;
    private LocalDate end;

    @JsonFormat(pattern = "yyyy-MM-dd")
    public LocalDate getStart() {
        return start;
    }

    public void setStart(LocalDate start) {
        this.start = start;
    }

    @JsonFormat(pattern = "yyyy-MM-dd")
    public LocalDate getEnd() {
        return end;
    }

    public void setEnd(LocalDate end) {
        this.end = end;
    }

    @Override
    public String toString() {
        return "ActiveDatesDTO{" +
                "start=" + start +
                ", end=" + end +
                '}';
    }
}
