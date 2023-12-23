package be.vives.ti.imageCalender.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

@Entity
public class Afspraak {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "gebruiker_id", nullable = false)
    private Gebruiker gebruiker;

    @NotBlank(message = "Titel mag niet leeg zijn")
    private String titel;

    @NotNull
    private LocalDateTime begintijd;

    @NotNull
    private LocalDateTime eindtijd;

    private String locatie;

    public Afspraak() {
    }

    public Afspraak(Gebruiker gebruiker, String titel, LocalDateTime begintijd, LocalDateTime eindtijd, String locatie) {
        this.gebruiker = gebruiker;
        this.titel = titel;
        this.begintijd = begintijd;
        this.eindtijd = eindtijd;
        this.locatie = locatie;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
