package my.bookstore.repository;

import java.util.List;
import my.bookstore.model.Book;

public interface BookRepository {
    Book save(Book book);

    List<Book> findAll();
}
