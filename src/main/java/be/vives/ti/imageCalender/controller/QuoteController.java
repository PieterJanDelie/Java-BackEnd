package be.vives.ti.imageCalender.controller;


import be.vives.ti.imageCalender.domain.Quote;
import be.vives.ti.imageCalender.services.QuoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/quote")
public class QuoteController {
    private final QuoteService quoteService;

    @Autowired
    public QuoteController(QuoteService quoteService) {
        this.quoteService = quoteService;
    }

    @GetMapping
    public ResponseEntity<Quote> getRandomQuote() {
        Quote randomQuote = quoteService.getRandomQuote();
        return new ResponseEntity<>(randomQuote, HttpStatus.OK);
    }
}
