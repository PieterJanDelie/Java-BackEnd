package be.vives.ti.imageCalender.controller;


import be.vives.ti.imageCalender.domain.Gebruiker;
import be.vives.ti.imageCalender.repository.GebruikersRepository;
import be.vives.ti.imageCalender.services.GebruikerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/auth")
@Validated
public class GebruikerController {

    private final GebruikersRepository gebruikersRepository;

    @Autowired
    private final GebruikerService gebruikerService;

    @Autowired
    public GebruikerController(GebruikersRepository gebruikersRepository, GebruikerService gebruikerService) {
        this.gebruikersRepository = gebruikersRepository;
        this.gebruikerService = gebruikerService;
    }

    @GetMapping
    public List<Gebruiker> getAllGebruikers() {
        return new ArrayList<>(gebruikersRepository.findAll());
    }

    @PostMapping("/login")
    public ResponseEntity<Long> login(@Valid @RequestBody Gebruiker loginCredentials) {
        Optional<Gebruiker> optionalGebruiker = gebruikersRepository.findByGebruikersnaam(loginCredentials.getGebruikersnaam());

        System.out.println("Optional user: " + optionalGebruiker.orElse(null));

        if (optionalGebruiker.isPresent()) {
            Gebruiker gebruiker = optionalGebruiker.get();

            if (loginCredentials.getWachtwoord().equals(gebruiker.getWachtwoord())) {
                System.out.println("Login successful for user: " + gebruiker.getGebruikersnaam());
                return new ResponseEntity<>(gebruiker.getId(), HttpStatus.OK);
            } else {
                System.out.println("Incorrect password for user: " + gebruiker.getGebruikersnaam());
            }
        } else {
            System.out.println("User not found: " + loginCredentials.getGebruikersnaam());
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @PostMapping("/register")
    public ResponseEntity<Long> createGebruiker(@Valid @RequestBody Gebruiker gebruiker) {
        Gebruiker createdGebruiker = gebruikersRepository.save(gebruiker);
        return new ResponseEntity<>(createdGebruiker.getId(), HttpStatus.CREATED);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Gebruiker> getGebruikerById(@PathVariable Long id) {
        Optional<Gebruiker> gebruiker = gebruikersRepository.findById(id);
        return gebruiker.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Gebruiker> updateGebruiker(@PathVariable Long id, @Valid @RequestBody Gebruiker updatedGebruiker) {
        if (!gebruikersRepository.existsById(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        updatedGebruiker.setId(id);
        Gebruiker savedGebruiker = gebruikersRepository.save(updatedGebruiker);
        return new ResponseEntity<>(savedGebruiker, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteGebruiker(@PathVariable Long id) {
        if (!gebruikersRepository.existsById(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        gebruikerService.verwijderGebruikerEnAfspraken(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}