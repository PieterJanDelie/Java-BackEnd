package be.vives.ti.imageCalender.domain;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public class AfspraakRequest {

    @NotNull(message = "Gebruikers ID mag niet leeg zijn")
    private Long gebruikersID;

    @NotBlank(message = "Titel mag niet leeg zijn")
    private String titel;

    @NotNull(message = "Begintijd mag niet leeg zijn")
    private LocalDateTime begintijd;

    @NotNull(message = "Eindtijd mag niet leeg zijn")
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

