package be.vives.ti.imageCalender.services;

import be.vives.ti.imageCalender.domain.Afspraak;
import be.vives.ti.imageCalender.domain.AfspraakResponse;
import be.vives.ti.imageCalender.domain.Gebruiker;
import be.vives.ti.imageCalender.repository.AfspraakRepository;
import be.vives.ti.imageCalender.repository.GebruikersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AfspraakService {

    private final AfspraakRepository afspraakRepository;
    private final GebruikersRepository gebruikersRepository;

    @Autowired
    public AfspraakService(AfspraakRepository afspraakRepository, GebruikersRepository gebruikersRepository) {
        this.afspraakRepository = afspraakRepository;
        this.gebruikersRepository = gebruikersRepository;
    }

    public List<AfspraakResponse> zoekAfsprakenOpTitel(Long userId, String zoekTerm) {
        Optional<Gebruiker> gebruiker = gebruikersRepository.findById(userId);

        if (gebruiker.isPresent()) {
            List<Afspraak> gevondenAfspraken = afspraakRepository.findByGebruikerAndTitelContaining(gebruiker.get(), zoekTerm);

            if (!gevondenAfspraken.isEmpty()) {
                return gevondenAfspraken.stream()
                        .map(this::mapAfspraakToAfspraakResponse)
                        .collect(Collectors.toList());
            } else {
                return Collections.emptyList();
            }
        } else {
            return Collections.emptyList();
        }
    }

    private AfspraakResponse mapAfspraakToAfspraakResponse(Afspraak afspraak) {
        return new AfspraakResponse(
                afspraak.getId(),
                afspraak.getTitel(),
                afspraak.getBegintijd(),
                afspraak.getEindtijd()
        );
    }
}