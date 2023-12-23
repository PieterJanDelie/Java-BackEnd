package be.vives.ti.imageCalender;

import be.vives.ti.imageCalender.controller.AfspraakController;
import be.vives.ti.imageCalender.domain.Afspraak;
import be.vives.ti.imageCalender.domain.AfspraakResponse;
import be.vives.ti.imageCalender.domain.Gebruiker;
import be.vives.ti.imageCalender.repository.AfspraakRepository;
import be.vives.ti.imageCalender.repository.GebruikersRepository;
import be.vives.ti.imageCalender.services.AfspraakService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;


@WebMvcTest(AfspraakController.class)
@AutoConfigureMockMvc
public class AfspraakControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AfspraakService afspraakService;

    @MockBean
    private AfspraakRepository afspraakRepository;

    @MockBean
    private GebruikersRepository gebruikersRepository;


    @Test
    void testGetAfspraakById() throws Exception {
        long afspraakId = 1L;
        Afspraak afspraak = new Afspraak();
        afspraak.setId(afspraakId);
        Mockito.when(afspraakRepository.findById(afspraakId)).thenReturn(Optional.of(afspraak));

        mockMvc.perform(MockMvcRequestBuilders.get("/afspraken/{id}", afspraakId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(afspraakId));
    }

    @Test
    void testCreateAfspraak() throws Exception {
        Afspraak afspraak = new Afspraak();
        afspraak.setId(1L);
        Mockito.when(gebruikersRepository.findById(1L)).thenReturn(Optional.of(new Gebruiker()));
        Mockito.when(afspraakRepository.save(Mockito.any(Afspraak.class))).thenReturn(afspraak);


        mockMvc.perform(MockMvcRequestBuilders.post("/afspraken")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"gebruikersID\": 1, \"titel\": \"Afspraak1\", \"begintijd\": \"2023-12-31T12:00\", \"eindtijd\": \"2023-12-31T13:00\", \"locatie\": \"Werk\"}"))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    void testUpdateAfspraak() throws Exception {
        long afspraakId = 1L;
        Afspraak updatedAfspraak = new Afspraak();
        updatedAfspraak.setId(afspraakId);
        Mockito.when(afspraakRepository.existsById(afspraakId)).thenReturn(true);
        Mockito.when(afspraakRepository.save(Mockito.any(Afspraak.class))).thenReturn(updatedAfspraak);


        mockMvc.perform(MockMvcRequestBuilders.put("/afspraken/{id}", afspraakId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\": 1, \"titel\": \"Afspraak1\", \"begintijd\": \"2023-12-31T12:00\", \"eindtijd\": \"2023-12-31T13:00\", \"locatie\": \"Werk\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(afspraakId));
    }

    @Test
    void testDeleteAfspraak() throws Exception {
        long afspraakId = 1L;
        Mockito.when(afspraakRepository.existsById(afspraakId)).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders.delete("/afspraken/{id}", afspraakId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    void testGetAfspraakById_NotFound() throws Exception {
        long afspraakId = 1L;
        Mockito.when(afspraakRepository.findById(afspraakId)).thenReturn(Optional.empty());

        mockMvc.perform(MockMvcRequestBuilders.get("/afspraken/{id}", afspraakId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void testCreateAfspraak_InvalidGebruikerId() throws Exception {
        Mockito.when(gebruikersRepository.findById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(MockMvcRequestBuilders.post("/afspraken")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"gebruikersID\": 1, \"titel\": \"Afspraak1\", \"begintijd\": \"2023-12-31T12:00\", \"eindtijd\": \"2023-12-31T13:00\", \"locatie\": \"Werk\"}"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void testUpdateAfspraak_NotFound() throws Exception {
        long afspraakId = 1L;
        Mockito.when(afspraakRepository.existsById(afspraakId)).thenReturn(false);

        mockMvc.perform(MockMvcRequestBuilders.put("/afspraken/{id}", afspraakId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\": 1, \"titel\": \"Afspraak1\", \"begintijd\": \"2023-12-31T12:00\", \"eindtijd\": \"2023-12-31T13:00\", \"locatie\": \"Werk\"}"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void testDeleteAfspraak_NotFound() throws Exception {
        long afspraakId = 1L;
        Mockito.when(afspraakRepository.existsById(afspraakId)).thenReturn(false);

        mockMvc.perform(MockMvcRequestBuilders.delete("/afspraken/{id}", afspraakId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
    @Test
    void testGetAlleAfspraken() throws Exception {
        List<Afspraak> afspraken = Arrays.asList(
                new Afspraak(new Gebruiker(), "Afspraak1", LocalDateTime.now(), LocalDateTime.now().plusHours(1), "Locatie1"),
                new Afspraak(new Gebruiker(), "Afspraak2", LocalDateTime.now(), LocalDateTime.now().plusHours(2), "Locatie2")
        );
        Mockito.when(afspraakRepository.findAll()).thenReturn(afspraken);

        mockMvc.perform(MockMvcRequestBuilders.get("/afspraken")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(2)));
    }

    @Test
    void testCreateAfspraak_InvalidInput() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/afspraken")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"gebruikersID\": 1, \"titel\": \"\", \"begintijd\": \"2023-12-31T12:00\", \"eindtijd\": \"2023-12-31T13:00\", \"locatie\": \"Werk\"}"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
    @Test
    void testGetAfsprakenByGebruikersId() throws Exception {
        long gebruikerId = 999L;
        Gebruiker gebruiker = new Gebruiker();
        gebruiker.setId(gebruikerId);

        List<Afspraak> afspraken = Arrays.asList(
                new Afspraak(gebruiker, "Afspraak1", LocalDateTime.now(), LocalDateTime.now().plusHours(1), "Locatie1"),
                new Afspraak(gebruiker, "Afspraak2", LocalDateTime.now(), LocalDateTime.now().plusHours(2), "Locatie2")
        );

        Mockito.when(gebruikersRepository.findById(gebruikerId)).thenReturn(Optional.of(gebruiker));
        Mockito.when(afspraakRepository.findByGebruiker(gebruiker)).thenReturn(afspraken);

        mockMvc.perform(MockMvcRequestBuilders.get("/afspraken/gebruiker/{gebruikersId}", gebruikerId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(2)));
    }
    @Test
    void testZoekAfsprakenOpTitel() throws Exception {
        long gebruikerId = 1L;
        String zoekTerm = "Afspraak";

        List<AfspraakResponse> afspraken = Arrays.asList(
                new AfspraakResponse(1L, "Afspraak1", LocalDateTime.now(), LocalDateTime.now().plusHours(1)),
                new AfspraakResponse(2L, "Afspraak2", LocalDateTime.now(), LocalDateTime.now().plusHours(2))
        );

        Mockito.when(afspraakService.zoekAfsprakenOpTitel(gebruikerId, zoekTerm)).thenReturn(afspraken);

        mockMvc.perform(MockMvcRequestBuilders.get("/afspraken/zoeken/{gebruikersId}/{zoekTerm}", gebruikerId, zoekTerm)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id").value(2L));
    }
}
