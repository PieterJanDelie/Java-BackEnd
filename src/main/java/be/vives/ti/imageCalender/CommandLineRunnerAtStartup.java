package be.vives.ti.imageCalender;

import be.vives.ti.imageCalender.domain.Afspraak;
import be.vives.ti.imageCalender.domain.Gebruiker;
import be.vives.ti.imageCalender.repository.AfspraakRepository;
import be.vives.ti.imageCalender.repository.GebruikersRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class CommandLineRunnerAtStartup implements CommandLineRunner {


    private final GebruikersRepository gebruikersRepository;
    private final AfspraakRepository afspraakRepository;

    public CommandLineRunnerAtStartup(GebruikersRepository gebruikersRepository, AfspraakRepository afspraakRepository) {
        this.gebruikersRepository = gebruikersRepository;
        this.afspraakRepository = afspraakRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        Gebruiker[] gebruikers = new Gebruiker[]{
                new Gebruiker("Pieter-JanDelie", "12345678"),
                new Gebruiker("Pieter-JanD", "12345678"),
                new Gebruiker("3SD", "12345678"),
                new Gebruiker("BackEnd", "12345678"),
                new Gebruiker("a", "12345678")
        };

        for (Gebruiker gebruiker : gebruikers) {
            gebruikersRepository.save(gebruiker);
        }
        Gebruiker eersteGebruiker = gebruikersRepository.findByGebruikersnaam("a").orElse(null);
        if (eersteGebruiker != null) {
            Afspraak afspraak1 = new Afspraak(
                    eersteGebruiker,
                    "Afspraak 1",
                    LocalDateTime.now().plusDays(1),
                    LocalDateTime.now().plusDays(1).plusHours(2),
                    "Kantoor"
            );
            Afspraak afspraak2 = new Afspraak(
                    eersteGebruiker,
                    "Afspraak 2",
                    LocalDateTime.now().plusDays(2),
                    LocalDateTime.now().plusDays(2).plusHours(3),
                    "Thuis"
            );

            afspraakRepository.save(afspraak1);
            afspraakRepository.save(afspraak2);
        }
    }
}