package be.vives.ti.imageCalender.repository;

import be.vives.ti.imageCalender.domain.Afspraak;
import be.vives.ti.imageCalender.domain.Gebruiker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AfspraakRepository extends JpaRepository<Afspraak, Long> {
    List<Afspraak> findByGebruiker(Gebruiker gebruiker);
}