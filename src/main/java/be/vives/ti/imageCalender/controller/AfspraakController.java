package be.vives.ti.imageCalender.controller;

import be.vives.ti.imageCalender.domain.Afspraak;
import be.vives.ti.imageCalender.domain.AfspraakRequest;
import be.vives.ti.imageCalender.domain.AfspraakResponse;
import be.vives.ti.imageCalender.domain.Gebruiker;
import be.vives.ti.imageCalender.repository.AfspraakRepository;
import be.vives.ti.imageCalender.repository.GebruikersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@CrossOrigin(origins = "http://localhost:19006")
@RestController
@RequestMapping("/afspraken")
public class AfspraakController {

    private final AfspraakRepository afspraakRepository;
    private final GebruikersRepository gebruikersRepository;

    @Autowired
    public AfspraakController(AfspraakRepository afspraakRepository, GebruikersRepository gebruikersRepository) {
        this.afspraakRepository = afspraakRepository;
        this.gebruikersRepository = gebruikersRepository;
    }

    @GetMapping("/gebruiker/{gebruikersId}")
    public ResponseEntity<List<AfspraakResponse>> getAfsprakenByGebruikersId(@PathVariable Long gebruikersId) {
        Optional<Gebruiker> gebruiker = gebruikersRepository.findById(gebruikersId);

        if (gebruiker.isPresent()) {
            List<Afspraak> afspraken = afspraakRepository.findByGebruiker(gebruiker.get());
            List<AfspraakResponse> afspraakResponses = afspraken.stream()
                    .map(this::mapAfspraakToAfspraakResponse)
                    .collect(Collectors.toList());
            return new ResponseEntity<>(afspraakResponses, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Afspraak> getAfspraakById(@PathVariable Long id) {
        Optional<Afspraak> afspraak = afspraakRepository.findById(id);
        return afspraak.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<Long> createAfspraak(@RequestBody AfspraakRequest afspraakRequest) {
        Optional<Gebruiker> gebruiker = gebruikersRepository.findById(afspraakRequest.getGebruikersID());
        if (gebruiker.isPresent()) {
            Afspraak afspraak = new Afspraak(
                    gebruiker.get(),
                    afspraakRequest.getTitel(),
                    afspraakRequest.getBegintijd(),
                    afspraakRequest.getEindtijd(),
                    afspraakRequest.getLocatie()
            );
            Afspraak createdAfspraak = afspraakRepository.save(afspraak);
            return new ResponseEntity<>(createdAfspraak.getId(), HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Afspraak> updateAfspraak(@PathVariable Long id, @RequestBody Afspraak updatedAfspraak) {
        if (!afspraakRepository.existsById(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        updatedAfspraak.setId(id);
        Afspraak savedAfspraak = afspraakRepository.save(updatedAfspraak);
        return new ResponseEntity<>(savedAfspraak, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAfspraak(@PathVariable Long id) {
        if (!afspraakRepository.existsById(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        afspraakRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    private AfspraakResponse mapAfspraakToAfspraakResponse(Afspraak afspraak) {
        return new AfspraakResponse(
                afspraak.getTitel(),
                afspraak.getBegintijd()
        );
    }
}