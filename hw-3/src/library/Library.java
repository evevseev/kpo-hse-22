package library;

import java.util.List;

public class Library {
    public Library(BooksStorage storage) {
        this.storage = storage;
    }

    BooksStorage storage;

    public void returnBook(Book book) {
        storage.add(book);
    }

    public void takeBook(Book book) {
        storage.remove(book);
    }

    public List<Book> getBooks(String title) {
        return storage.get(title);
    }

    public boolean hasBook(Book book) {
        return storage.contains(book);
    }

    public boolean hasBook(String title) {
        return !storage.get(title).isEmpty();
    }

    public List<Book> getBooks() {
        return storage.getAll();
    }
}
