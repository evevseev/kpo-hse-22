package library;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ArrayListBookStorage implements BooksStorage {
    public ArrayListBookStorage() {
        this.books = new ArrayList<>();
    }

    public ArrayListBookStorage(List<Book> books) {
        if (books != null) {
            this.books = new ArrayList<>(books);
        } else {
            this.books = new ArrayList<>();
        }
    }

    private final @NotNull ArrayList<Book> books;

    @Override
    public void add(Book book) {
        books.add(book);
    }

    @Override
    public boolean remove(Book book) {
        return books.remove(book);
    }

    @Override
    public List<Book> get(@NotNull String title) {
        ArrayList<Book> result = new ArrayList<>();
        for (Book book : books) {
            if (book.getTitle().equals(title)) {
                result.add(book);
            }
        }
        return result;
    }

    @Override
    public boolean contains(Book book) {
        return books.contains(book);
    }

    @Override
    public List<Book> getAll() {
        return new ArrayList<>(books);
    }

}
