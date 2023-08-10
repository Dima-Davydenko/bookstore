package my.bookstore;

import java.math.BigDecimal;
import my.bookstore.model.Book;
import my.bookstore.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BookstoreApplication {
    @Autowired
    private BookService bookService;

    public static void main(String[] args) {
        SpringApplication.run(BookstoreApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner() {
        return new CommandLineRunner() {
            @Override
            public void run(String... args) throws Exception {
                Book kobzar = new Book();
                kobzar.setTitle("Кобзар");
                kobzar.setAuthor("Тарас Шевченко");
                kobzar.setIsbn("966-03-3462-1");
                kobzar.setPrice(BigDecimal.valueOf(199));

                bookService.save(kobzar);
                System.out.println(bookService.findAll());
            }
        };
    }

}
