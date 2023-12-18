package be.vives.ti.imageCalender.domain;

import java.time.LocalDateTime;

public class AfspraakResponse {

    private String titel;
    private LocalDateTime begintijd;

    public AfspraakResponse(String titel, LocalDateTime begintijd) {
        this.titel = titel;
        this.begintijd = begintijd;
    }

    public String getTitel() {
        return titel;
    }
}
