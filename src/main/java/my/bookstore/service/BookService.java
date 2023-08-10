package my.bookstore.service;

import java.util.List;
import my.bookstore.model.Book;

public interface BookService {
    Book save(Book book);

    List<Book> findAll();
}
