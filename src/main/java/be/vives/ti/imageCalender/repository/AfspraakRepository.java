package be.vives.ti.imageCalender.repository;

import be.vives.ti.imageCalender.domain.Afspraak;
import be.vives.ti.imageCalender.domain.Gebruiker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AfspraakRepository extends JpaRepository<Afspraak, Long> {
    List<Afspraak> findByGebruiker(Gebruiker gebruiker);

    @Modifying
    @Query("DELETE FROM Afspraak a WHERE a.gebruiker = :gebruiker")
    void verwijderAfsprakenVanGebruiker(@Param("gebruiker") Gebruiker gebruiker);
    List<Afspraak> findByGebruikerAndTitelContaining(Gebruiker gebruiker, String zoekTerm);
}