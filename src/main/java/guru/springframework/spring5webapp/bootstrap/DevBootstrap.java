package guru.springframework.spring5webapp.bootstrap;

import guru.springframework.spring5webapp.model.Author;
import guru.springframework.spring5webapp.model.Book;
import guru.springframework.spring5webapp.repositories.AuthorRepository;
import guru.springframework.spring5webapp.repositories.BookRepository;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 * Created by jt on 5/16/17.
 */
// LEZIONE 483 Notare l'annotazione
    // come Component in modo che sia innestato nel contest
@Component
public class DevBootstrap implements ApplicationListener<ContextRefreshedEvent> {

    // Sarebbe meglio fossero dichiarati final
    // non faccio alcun Autowiring perchè sono iniettati
    // da Spring nel costruttore.
    // Quindi quello che succede runtime è che questa classe
    // verra creata e gestita da SpringFramework
    // e i repository di Autori e libri otterranno un implementazione
    // di SpringDataJpa che saranno cablati in automatico(autowired)
    private AuthorRepository authorRepository;
    private BookRepository bookRepository;

    public DevBootstrap(AuthorRepository authorRepository, BookRepository bookRepository) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {

        initData();
    }

    private void initData(){

        //Eric
        Author eric = new Author("Eric", "Evans");
        Book  ddd = new Book("Domain Driven Design", "1234", "Harper Collins");
        // Di seguito creo l'associazioni tra i due oggetti
        eric.getBooks().add(ddd);
        ddd.getAuthors().add(eric);

        authorRepository.save(eric);
        bookRepository.save(ddd);


        //Rod fondatore dello Spring Framework con il libro
        // J2EE Development without EJB
        Author rod = new Author("Rod", "Johnson");
        Book noEJB = new Book("J2EE Development without EJB", "23444", "Wrox" );
        // Di seguito creo l'associazioni tra i due oggetti
        rod.getBooks().add(noEJB);
        noEJB.getAuthors().add(rod);

        // Per poter eseguire il metodo save
        // deve essere dichiarto come Component
        // in modo che diventi uno SpringBean e venga cablato
        // nello Spring Context

        authorRepository.save(rod);
        bookRepository.save(noEJB);
    }
}
