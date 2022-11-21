//  Create an application to chose random student to
//  answer a question. Once a student is chosen teacher
//  has an option to flag student as present.
//  After that teacher can grade the answer from 1 to 10.
//  Student can be randomly chosen once per lesson.

// Commands:
//  • /r – selects random student, asks if present
//  • /l – list of students who received a grade
//  • /h – help, lists commands and how to use them
//  Bonus points:
//  • Store students and grades somewhere somehow
//  • Create some kind of frontend

import com.fasterxml.jackson.core.JsonProcessingException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        if (args == null || args.length != 1) {
            System.out.println("Usage: java Main <db_filename>");
            return;
        }
        final String fileName = args[0];
        final Path absolutePath = Path.of(fileName).toAbsolutePath();

        List<Student> students = null;
        try {
            String fileData = Files.readString(absolutePath);
            students = JsonSerializer.deserialize(fileData);
        } catch (JsonProcessingException e) {
            System.out.println("Failed to get data from file, so demo list is generated.");
        } catch (IOException e) {
            System.out.println("Failed to read file: " + e.getCause());
            return;
        }

        if (students == null) {
            students = List.of(
                    new Student("John", "Doe"),
                    new Student("Jane", "Smith")
            );
        }

        StudentPicker studentPicker = new StudentPicker(students);
        ConsoleApplication application = new ConsoleApplication(studentPicker);
        application.run();

        try {
            String serialized = JsonSerializer.serialize(application.getStudents());
            Files.writeString(absolutePath, serialized);
        } catch (JsonProcessingException e) {
            System.out.println("Failed to serialize file: " + e.getCause());
        } catch (IOException e) {
            System.out.println("Failed to write file: " + e.getCause());
        }
    }
}