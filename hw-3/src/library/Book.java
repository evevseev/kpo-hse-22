package library;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

public class Book {
    public Book(@NotNull String title, @Nullable String description, @Nullable Collection<Author> authors, @Nullable Date published) {
        this.title = title;
        this.description = description;
        this.authors = ((authors == null) ? null : new ArrayList<>(authors));
        this.published = published;
    }

    @NotNull
    private String title;
    @Nullable
    private String description;
    @Nullable
    private List<Author> authors;
    @Nullable
    private Date published;

    public @NotNull String getTitle() {
        return title;
    }

    public void setTitle(@NotNull String title) {
        this.title = title;
    }

    public @Nullable String getDescription() {
        return description;
    }

    public void setDescription(@Nullable String description) {
        this.description = description;
    }

    public @Nullable List<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(@Nullable List<Author> authors) {
        this.authors = authors;
    }

    public @Nullable Date getPublished() {
        return published;
    }

    public void setPublished(@Nullable Date published) {
        this.published = published;
    }
}
