package library;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface BooksStorage {
    void add(Book book);
    boolean remove(Book book);

    List<Book> get(@NotNull String title);

    boolean contains(Book book);
    List<Book> getAll();
}
