package be.vives.ti.imageCalender.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import javax.validation.constraints.NotNull;

@Entity
public class Quote {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    private String quote;

    private String schrijver;

    public Quote() {
    }
    public Quote(String quote) {
        this.quote = quote;
    }

    public Quote(String quote, String schrijver) {
        this.quote = quote;
        this.schrijver = schrijver;
    }

    public String getQuote() {
        return quote;
    }

    public void setQuote(String quote) {
        this.quote = quote;
    }

    public String getSchrijver() {
        return schrijver;
    }

    public void setSchrijver(String schrijver) {
        this.schrijver = schrijver;
    }
}
