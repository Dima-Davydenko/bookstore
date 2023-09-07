package my.bookstore.repository.book;

import java.util.List;
import lombok.RequiredArgsConstructor;
import my.bookstore.model.Book;
import my.bookstore.repository.SpecificationProvider;
import my.bookstore.repository.SpecificationProviderManager;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class BookSpecificationProviderManager implements SpecificationProviderManager<Book> {
    private final List<SpecificationProvider<Book>> bookSpecificationProviders;

    @Override
    public SpecificationProvider<Book> getSpecificationProvider(String key) {
        return bookSpecificationProviders.stream()
                .filter(p -> p.getKey().equals(key))
                .findFirst()
                .orElseThrow(()
                        -> new RuntimeException("Can't find correct spec. provider for key "
                        + key));
    }
}
