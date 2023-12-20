package be.vives.ti.imageCalender;

import be.vives.ti.imageCalender.domain.Afspraak;
import be.vives.ti.imageCalender.domain.Gebruiker;
import be.vives.ti.imageCalender.domain.Quote;
import be.vives.ti.imageCalender.repository.AfspraakRepository;
import be.vives.ti.imageCalender.repository.GebruikersRepository;
import be.vives.ti.imageCalender.repository.QuoteRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class CommandLineRunnerAtStartup implements CommandLineRunner {


    private final GebruikersRepository gebruikersRepository;
    private final AfspraakRepository afspraakRepository;
    private  final QuoteRepository quoteRepository;

    public CommandLineRunnerAtStartup(GebruikersRepository gebruikersRepository, AfspraakRepository afspraakRepository, QuoteRepository quoteRepository) {
        this.gebruikersRepository = gebruikersRepository;
        this.afspraakRepository = afspraakRepository;
        this.quoteRepository = quoteRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        addGebruikers();

        Gebruiker gebruiker = gebruikersRepository.findByGebruikersnaam("a").orElse(null);
        addAfspraken(gebruiker);

        addQuotes();
    }
    private void addGebruikers() {
        Gebruiker[] gebruikers = new Gebruiker[]{
                new Gebruiker("Pieter-JanDelie", "12345678"),
                new Gebruiker("Pieter-JanD", "12345678"),
                new Gebruiker("3SD", "12345678"),
                new Gebruiker("BackEnd", "12345678"),
                new Gebruiker("a", "12345678")
        };

        for (Gebruiker gebruiker : gebruikers) {
            gebruikersRepository.save(gebruiker);
        }
    }
    private void addAfspraken(Gebruiker gebruiker) {
        if (gebruiker != null) {
            Afspraak afspraak1 = new Afspraak(
                    gebruiker,
                    "Afspraak 1",
                    LocalDateTime.now().plusDays(1),
                    LocalDateTime.now().plusDays(1).plusHours(2),
                    "Kantoor"
            );
            Afspraak afspraak2 = new Afspraak(
                    gebruiker,
                    "Afspraak 2",
                    LocalDateTime.now().plusDays(2),
                    LocalDateTime.now().plusDays(2).plusHours(3),
                    "Thuis"
            );

            afspraakRepository.save(afspraak1);
            afspraakRepository.save(afspraak2);
        }
    }

    private void addQuotes() {
        Quote[] quotes = {
                new Quote("The purpose of our lives is to be happy.", "Dalai Lama"),
                new Quote("Life is what happens when you're busy making other plans.", "John Lennon"),
                new Quote("Get busy living or get busy dying.", "Stephen King"),
                new Quote("You only live once, but if you do it right, once is enough.", "Mae West"),
                new Quote("Many of life’s failures are people who did not realize how close they were to success when they gave up.", "Thomas A. Edison"),
                new Quote("If you want to live a happy life, tie it to a goal, not to people or things.", "Albert Einstein"),
                new Quote("Never let the fear of striking out keep you from playing the game.", "Babe Ruth"),
                new Quote("Money and success don’t change people; they merely amplify what is already there.", "Will Smith"),
                new Quote("Not how long, but how well you have lived is the main thing.", "Seneca"),
                new Quote("If life were predictable it would cease to be life, and be without flavor.", "Eleanor Roosevelt"),
                new Quote("The whole secret of a successful life is to find out what is one’s destiny to do, and then do it.", "Henry Ford"),
                new Quote("In order to write about life first you must live it.", "Ernest Hemingway"),
                new Quote("The big lesson in life, baby, is never be scared of anyone or anything.", "Frank Sinatra"),
                new Quote("Sing like no one’s listening, love like you’ve never been hurt, dance like nobody’s watching, and live like it’s heaven on earth.", "Attributed to various sources"),
                new Quote("Curiosity about life in all of its aspects, I think, is still the secret of great creative people.", "Leo Burnett"),
                new Quote("Life is not a problem to be solved, but a reality to be experienced.", "Soren Kierkegaard"),
                new Quote("The unexamined life is not worth living.", "Socrates"),
                new Quote("Turn your wounds into wisdom.", "Oprah Winfrey"),
                new Quote("The way I see it, if you want the rainbow, you gotta put up with the rain.", "Dolly Parton"),
                new Quote("Do all the good you can, for all the people you can, in all the ways you can, as long as you can.", "Hillary Clinton (inspired by John Wesley quote)"),
                new Quote("Don’t settle for what life gives you; make life better and build something.", "Ashton Kutcher"),
                new Quote("Everything negative – pressure, challenges – is all an opportunity for me to rise.", "Kobe Bryant"),
                new Quote("I like criticism. It makes you strong.", "LeBron James"),
                new Quote("You never really learn much from hearing yourself speak.", "George Clooney"),
                new Quote("Life imposes things on you that you can’t control, but you still have the choice of how you’re going to live through this.", "Celine Dion"),
                new Quote("Life is never easy. There is work to be done and obligations to be met – obligations to truth, to justice, and to liberty.", "John F. Kennedy (JFK Quotes)"),
                new Quote("Live for each second without hesitation.", "Elton John"),
                new Quote("Life is like riding a bicycle. To keep your balance, you must keep moving.", "Albert Einstein"),
                new Quote("Life is really simple, but men insist on making it complicated.", "Confucius"),
                new Quote("Life is a succession of lessons which must be lived to be understood.", "Helen Keller"),
                new Quote("When we do the best we can, we never know what miracle is wrought in our life or the life of another.", "Helen Keller"),
                new Quote("The healthiest response to life is joy.", "Deepak Chopra"),
                new Quote("Life is like a coin. You can spend it any way you wish, but you only spend it once.", "Lillian Dickson"),
                new Quote("The best portion of a good man's life is his little nameless, unencumbered acts of kindness and of love.", "Wordsworth"),
                new Quote("In three words I can sum up everything I've learned about life: It goes on.", "Robert Frost"),
                new Quote("Life is ten percent what happens to you and ninety percent how you respond to it.", "Charles Swindoll"),
                new Quote("Keep calm and carry on.", "Winston Churchill"),
                new Quote("Maybe that’s what life is… a wink of the eye and winking stars.", "Jack Kerouac"),
                new Quote("Life is a flower of which love is the honey.", "Victor Hugo"),
                new Quote("Keep smiling, because life is a beautiful thing and there’s so much to smile about.", "Marilyn Monroe"),
                new Quote("Health is the greatest gift, contentment the greatest wealth, faithfulness the best relationship.", "Buddha"),
                new Quote("You have brains in your head. You have feet in your shoes. You can steer yourself any direction you choose.", "Dr. Seuss"),
                new Quote("Good friends, good books, and a sleepy conscience: this is the ideal life.", "Mark Twain"),
                new Quote("Life would be tragic if it weren’t funny.", "Stephen Hawking"),
                new Quote("Live in the sunshine, swim the sea, drink the wild air.", "Ralph Waldo Emerson"),
                new Quote("The greatest pleasure of life is love.", "Euripides"),
                new Quote("Life is what we make it, always has been, always will be.", "Grandma Moses"),
                new Quote("Life’s tragedy is that we get old too soon and wise too late.", "Benjamin Franklin"),
                new Quote("Life is about making an impact, not making an income.", "Kevin Kruse"),
                new Quote("Every strike brings me closer to the next home run.", "Babe Ruth"),
                new Quote("The two most important days in your life are the day you are born and the day you find out why.", "Mark Twain"),
                new Quote("Life shrinks or expands in proportion to one's courage.", "Anais Nin"),
                new Quote("Too many of us are not living our dreams because we are living our fears.", "Les Brown")
        };
        for (Quote quote : quotes) {
            quoteRepository.save(quote);
        }
    }
}