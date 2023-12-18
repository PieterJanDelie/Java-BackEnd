package be.vives.ti.imageCalender;

import be.vives.ti.imageCalender.controller.AfspraakController;
import be.vives.ti.imageCalender.domain.Afspraak;
import be.vives.ti.imageCalender.domain.Gebruiker;
import be.vives.ti.imageCalender.repository.AfspraakRepository;
import be.vives.ti.imageCalender.repository.GebruikersRepository;
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

import java.util.List;
import java.util.Optional;


@WebMvcTest(AfspraakController.class)
@AutoConfigureMockMvc
public class AfspraakControllerTest {
    @Autowired
    private MockMvc mockMvc;

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
    void testGetAllAfspraken() throws Exception {
        Afspraak afspraak1 = new Afspraak();
        Afspraak afspraak2 = new Afspraak();
        Mockito.when(afspraakRepository.findAll()).thenReturn(List.of(afspraak1, afspraak2));

        mockMvc.perform(MockMvcRequestBuilders.get("/afspraken")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(2));
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
}
