import DependenciesInjector.DependenciesInjector;
import DependenciesInjector.Exceptions.CyclicDependencyException;
import DependenciesInjector.Exceptions.FileReadingException;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class DependenciesInjectorCLI {
    public static void main(String[] args) {
        Path rootFolder = validateArgsInput(args);

        if (rootFolder == null) {
            return;
        }

        try {
            DependenciesInjector compiler = new DependenciesInjector(rootFolder);
            compiler.compile();

            System.out.println("---= Topological sort =---");
            List<Path> files = compiler.getDependenciesInOrder();
            for (Path file : files) {
                System.out.println(file);
            }

            System.out.println("---= Dependencies =---");
            System.out.println(compiler.dependencyGraph);


            System.out.println("---= Compiled data =---");
            System.out.println(compiler.getCompilationResult());

        } catch (CyclicDependencyException e) {
            System.out.println(e.getMessage());
        } catch (FileReadingException e) {
            System.out.println("Error reading file: " + e.getMessage());
            System.out.println("Check if the path is correct and the file is not corrupted.");
        }
    }

    private static Path validateArgsInput(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java DependencyInjector <path>");
            return null;
        }

        Path rootFolder = Paths.get(args[0]);
        if (!Files.isDirectory(rootFolder)) {
            System.out.println("Provide folder");
            return null;
        }

        return rootFolder;
    }
}
