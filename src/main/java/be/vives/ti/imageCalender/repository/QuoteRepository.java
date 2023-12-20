package be.vives.ti.imageCalender.repository;

import be.vives.ti.imageCalender.domain.Quote;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuoteRepository extends JpaRepository<Quote, Long> {
}
