package be.vives.ti.imageCalender;

import be.vives.ti.imageCalender.domain.Gebruiker;
import be.vives.ti.imageCalender.repository.GebruikersRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class CommandLineRunnerAtStartup implements CommandLineRunner {

    private final GebruikersRepository gebruikersRepository;

    public CommandLineRunnerAtStartup(GebruikersRepository gebruikersRepository) {
        this.gebruikersRepository = gebruikersRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        gebruikersRepository.save(new Gebruiker("Pieter-JanDelie", "12345678"));
        gebruikersRepository.save(new Gebruiker("Pieter-JanD", "12345678"));
        gebruikersRepository.save(new Gebruiker("3SD", "12345678"));
        gebruikersRepository.save(new Gebruiker("BackEnd", "12345678"));
        gebruikersRepository.save(new Gebruiker("a", "12345678"));
    }
}
