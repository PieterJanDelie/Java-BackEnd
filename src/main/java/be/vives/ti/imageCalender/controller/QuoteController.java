package be.vives.ti.imageCalender.controller;


import be.vives.ti.imageCalender.domain.Quote;
import be.vives.ti.imageCalender.services.QuoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = "http://localhost:19006")
@RestController
@RequestMapping("/quote")
public class QuoteController {


    private final QuoteService quoteService;

    @Autowired
    public QuoteController(QuoteService quoteService) {
        this.quoteService = quoteService;
    }

    @PostMapping
    public ResponseEntity<Void> addQuote(@RequestBody Quote quote) {
        quoteService.addQuote(quote);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Quote> getRandomQuote() {
        Quote randomQuote = quoteService.getRandomQuote();
        return new ResponseEntity<>(randomQuote, HttpStatus.OK);
    }
}
