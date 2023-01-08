package DependeniesGetter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

/**
 * Implementation of {@link IDependenciesGetter} that finds "Require" dependencies.
 */
public class RequiresGetter implements IDependenciesGetter {
    private static final String REQUIRE_REGEX = "require '(.*)'";
    private final Pattern requirePattern = Pattern.compile(REQUIRE_REGEX);

    @Override
    public List<Path> getFileDependencies(Path file) throws IOException {
        ArrayList<Path> dependencies = new ArrayList<>();

        try (Stream<String> stream = Files.lines(file)) {
            stream.forEach(line -> dependencies.addAll(getFileDependencies(line)));
        }

        return dependencies;
    }

    private List<Path> getFileDependencies(String string) {
        ArrayList<Path> dependencies = new ArrayList<>();
        Matcher matcher = requirePattern.matcher(string);

        if (matcher.find()) {
            String importPath = matcher.group(1);
            dependencies.add(Paths.get(importPath));
        }

        return dependencies;
    }
}

