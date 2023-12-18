package be.vives.ti.imageCalender.domain;

import java.time.LocalDateTime;

public class AfspraakRequest {

    private Long gebruikersID;
    private String titel;
    private LocalDateTime begintijd;
    private LocalDateTime eindtijd;
    private String locatie;

    public Long getGebruikersID() {
        return gebruikersID;
    }

    public void setGebruikersID(Long gebruikersID) {
        this.gebruikersID = gebruikersID;
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

    public String getLocatie() {
        return locatie;
    }
}

