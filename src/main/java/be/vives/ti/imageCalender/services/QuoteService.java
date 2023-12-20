package be.vives.ti.imageCalender.services;

import be.vives.ti.imageCalender.domain.Quote;
import be.vives.ti.imageCalender.repository.QuoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class QuoteService {

    private final QuoteRepository quoteRepository;

    @Autowired
    public QuoteService(QuoteRepository quoteRepository) {
        this.quoteRepository = quoteRepository;
    }

    public void addQuote(Quote quote) {
        quoteRepository.save(quote);
    }

    public Quote getRandomQuote() {
        List<Quote> allQuotes = quoteRepository.findAll();
        if (allQuotes.isEmpty()) {
            return null;
        }
        int randomIndex = new Random().nextInt(allQuotes.size());
        return allQuotes.get(randomIndex);
    }
}
