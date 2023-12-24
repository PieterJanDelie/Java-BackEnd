package be.vives.ti.imageCalender.controller;

import be.vives.ti.imageCalender.domain.Afspraak;
import be.vives.ti.imageCalender.domain.AfspraakRequest;
import be.vives.ti.imageCalender.domain.AfspraakResponse;
import be.vives.ti.imageCalender.domain.Gebruiker;
import be.vives.ti.imageCalender.repository.AfspraakRepository;
import be.vives.ti.imageCalender.repository.GebruikersRepository;
import be.vives.ti.imageCalender.services.AfspraakService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/afspraken")
public class AfspraakController {

    private final AfspraakRepository afspraakRepository;
    private final GebruikersRepository gebruikersRepository;

    @Autowired
    private final AfspraakService afspraakService;

    @Autowired
    public AfspraakController(AfspraakRepository afspraakRepository, GebruikersRepository gebruikersRepository, AfspraakService afspraakService) {
        this.afspraakRepository = afspraakRepository;
        this.gebruikersRepository = gebruikersRepository;
        this.afspraakService = afspraakService;
    }

    @GetMapping
    public ResponseEntity<List<AfspraakResponse>> getAlleAfspraken() {
        List<Afspraak> alleAfspraken = afspraakRepository.findAll();
        List<AfspraakResponse> afspraakResponses = alleAfspraken.stream()
                .map(this::mapAfspraakToAfspraakResponse)
                .collect(Collectors.toList());
        return new ResponseEntity<>(afspraakResponses, HttpStatus.OK);
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

    @GetMapping("/zoeken/{gebruikersId}/{zoekTerm}")
    public ResponseEntity<List<AfspraakResponse>> zoekAfsprakenOpTitel(@PathVariable Long gebruikersId, @PathVariable String zoekTerm) {
        List<AfspraakResponse> gevondenAfspraken = afspraakService.zoekAfsprakenOpTitel(gebruikersId, zoekTerm.trim());

        if (!gevondenAfspraken.isEmpty()) {
            return new ResponseEntity<>(gevondenAfspraken, HttpStatus.OK);
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
    public ResponseEntity<Long> createAfspraak(@Valid @RequestBody AfspraakRequest afspraakRequest) {
        Optional<Gebruiker> gebruiker = gebruikersRepository.findById(afspraakRequest.getGebruikersID());
        if (gebruiker.isPresent()) {
            Afspraak afspraak = new Afspraak(
                    gebruiker.get(),
                    afspraakRequest.getTitel().toUpperCase(),
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
    public ResponseEntity<Afspraak> updateAfspraak(@PathVariable Long id, @Valid @RequestBody Afspraak updatedAfspraak) {
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
                afspraak.getId(),
                afspraak.getTitel(),
                afspraak.getBegintijd(),
                afspraak.getEindtijd()
        );
    }
}