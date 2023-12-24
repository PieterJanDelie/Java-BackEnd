package be.vives.ti.imageCalender;

import be.vives.ti.imageCalender.controller.GebruikerController;
import be.vives.ti.imageCalender.domain.Gebruiker;
import be.vives.ti.imageCalender.repository.GebruikersRepository;
import be.vives.ti.imageCalender.services.GebruikerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GebruikerControllerTest {

    @Mock
    private GebruikersRepository gebruikersRepository;

    @Mock
    private GebruikerService gebruikerService;

    @InjectMocks
    private GebruikerController gebruikerController;

    @Test
    void getAllGebruikers() {
        when(gebruikersRepository.findAll()).thenReturn(new ArrayList<>());

        List<Gebruiker> gebruikers = gebruikerController.getAllGebruikers();

        assertNotNull(gebruikers);
        assertEquals(0, gebruikers.size());
    }

    @Test
    void loginSuccess() {
        Gebruiker mockGebruiker = new Gebruiker("testuser", "password");
        when(gebruikersRepository.findByGebruikersnaam("testuser")).thenReturn(Optional.of(mockGebruiker));

        Gebruiker loginCredentials = new Gebruiker("testuser", "password");
        ResponseEntity<Long> response = gebruikerController.login(loginCredentials);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void loginIncorrectPassword() {
        Gebruiker mockGebruiker = new Gebruiker("testuser", "password");
        when(gebruikersRepository.findByGebruikersnaam("testuser")).thenReturn(Optional.of(mockGebruiker));

        Gebruiker loginCredentials = new Gebruiker("testuser", "wrongpassword");
        ResponseEntity<Long> response = gebruikerController.login(loginCredentials);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

    @Test
    void loginUserNotFound() {
        when(gebruikersRepository.findByGebruikersnaam("nonexistentuser")).thenReturn(Optional.empty());

        Gebruiker loginCredentials = new Gebruiker("nonexistentuser", "password");
        ResponseEntity<Long> response = gebruikerController.login(loginCredentials);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

    @Test
    void createGebruiker() {
        Gebruiker newGebruiker = new Gebruiker("newuser", "newpassword");
        when(gebruikersRepository.save(any())).thenReturn(newGebruiker);

        ResponseEntity<Long> response = gebruikerController.createGebruiker(newGebruiker);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    void loginNullCredentials() {
        assertThrows(NullPointerException.class, () -> gebruikerController.login(null));
    }

    @Test
    void createGebruikerNullGebruiker() {
        assertThrows(NullPointerException.class, () -> gebruikerController.createGebruiker(null));
    }

    @Test
    void getGebruikerByIdInvalidId() {
        ResponseEntity<Gebruiker> response = gebruikerController.getGebruikerById(-1L);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void updateGebruikerNotFound() {
        ResponseEntity<Gebruiker> response = gebruikerController.updateGebruiker(999L, new Gebruiker());
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void deleteGebruikerNotFound() {
        ResponseEntity<Void> response = gebruikerController.deleteGebruiker(999L);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}