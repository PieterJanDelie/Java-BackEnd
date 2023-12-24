package be.vives.ti.imageCalender.domain;

import java.time.LocalDateTime;

public class AfspraakResponse {

    private final Long id;

    private final String titel;
    private final LocalDateTime begintijd;
    private final LocalDateTime eindtijd;

    public AfspraakResponse(Long id, String titel, LocalDateTime begintijd, LocalDateTime eindtijd) {
        this.id = id;
        this.titel = titel;
        this.begintijd = begintijd;
        this.eindtijd = eindtijd;
    }

    public Long getId() {
        return id;
    }

    public String getTitel() {
        return titel;
    }

    public LocalDateTime getBegintijd() {
        return begintijd;
    }

    public LocalDateTime getEindtijd() {
        return eindtijd;
    }
}
