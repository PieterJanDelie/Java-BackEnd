package be.vives.ti.imageCalender.services;

import be.vives.ti.imageCalender.domain.Gebruiker;
import be.vives.ti.imageCalender.repository.AfspraakRepository;
import be.vives.ti.imageCalender.repository.GebruikersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GebruikerService {

    private final GebruikersRepository gebruikersRepository;
    private final AfspraakRepository afspraakRepository;

    @Autowired
    public GebruikerService(GebruikersRepository gebruikersRepository, AfspraakRepository afspraakRepository) {
        this.gebruikersRepository = gebruikersRepository;
        this.afspraakRepository = afspraakRepository;
    }

    public void verwijderGebruikerEnAfspraken(Long gebruikerId) {
        Optional<Gebruiker> gebruikerOptional = gebruikersRepository.findById(gebruikerId);

        if (gebruikerOptional.isPresent()) {
            Gebruiker gebruiker = gebruikerOptional.get();

            afspraakRepository.verwijderAfsprakenVanGebruiker(gebruiker);

            gebruikersRepository.deleteById(gebruikerId);
        }
    }
}

