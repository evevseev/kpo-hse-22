package DependenciesInjector;

import DependenciesGraph.PathVertex;
import DependenciesGraph.ProjectDependenciesGraph;
import DependenciesInjector.Exceptions.CyclicDependencyException;
import DependenciesInjector.Exceptions.FileReadingException;
import DependeniesGetter.RequiresGetter;
import Graph.GraphCycle;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DependenciesInjector {
    public final ProjectDependenciesGraph dependencyGraph;
    public final Path projectRootFolder;
    private String compilationResult = null;

    public DependenciesInjector(Path projectRoot) throws FileReadingException {
        this.projectRootFolder = projectRoot;
        this.dependencyGraph = new ProjectDependenciesGraph();

        try {
            List<Path> files = getAllProjectFiles();
            for (Path file : files) {
                insertFileWithDependencies(file, new HashSet<>());
            }
        } catch (IOException e) {
            throw new FileReadingException(e.getMessage());
        }
    }

    public void compile() throws CyclicDependencyException, FileReadingException {
        if (compilationResult != null) {
            return;
        }

        GraphCycle<PathVertex> cycle = dependencyGraph.findCycle();
        if (cycle != null) {
            throw new CyclicDependencyException(cycle);
        }

        StringBuilder result = new StringBuilder();
        List<Path> deps = getDependenciesInOrder();
        for (Path file : deps) {
            try (Stream<String> stream = Files.lines(file)) {
                stream.forEach(s -> result.append(s).append("\n"));
            } catch (IOException e) {
                throw new FileReadingException(e.getMessage());
            }
        }

        compilationResult = result.toString();
    }

    public String getCompilationResult() {
        return compilationResult;
    }

    public List<Path> getDependenciesInOrder() {
        ArrayList<Path> files = new ArrayList<>();
        for (PathVertex node : dependencyGraph.topologicalSort()) {
            files.add(node.getPath());
        }
        return files;
    }

    public List<Path> getAllProjectFiles() throws IOException {
        try (Stream<Path> paths = Files.walk(projectRootFolder)) {
            return paths.filter(Files::isRegularFile).collect(Collectors.toList());
        }
    }

    private void insertFileWithDependencies(Path file, HashSet<Path> added) throws FileReadingException {
        if (added.contains(file)) {
            return;
        }
        try {
            List<Path> dependencies = (new RequiresGetter()).getFileDependencies(file);
            Stream<Path> dependenciesStream = dependencies.stream();
            dependenciesStream = dependenciesStream.map(projectRootFolder::resolve);
            dependencies = dependenciesStream.collect(Collectors.toList());

            added.add(file);
            PathVertex fileRef = this.dependencyGraph.getOrCreateVertexReference(file);
            for (Path dependency : dependencies) {
                PathVertex depRef = this.dependencyGraph.getOrCreateVertexReference(dependency);
                dependencyGraph.addEdge(fileRef, depRef);
                insertFileWithDependencies(dependency, added);
            }
        } catch (IOException e) {
            throw new FileReadingException(e.getMessage());
        }
    }
}
