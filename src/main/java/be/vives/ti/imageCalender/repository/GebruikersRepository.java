package be.vives.ti.imageCalender.repository;


import be.vives.ti.imageCalender.domain.Gebruiker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;


public interface GebruikersRepository extends JpaRepository<Gebruiker, Long> {

    @Query("SELECT g FROM Gebruiker g WHERE g.gebruikersnaam = :gebruikersnaam")
    Optional<Gebruiker> findByGebruikersnaam(@Param("gebruikersnaam") String gebruikersnaam);

}
