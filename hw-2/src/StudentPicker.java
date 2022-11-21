import java.util.List;
import java.util.Random;

public class StudentPicker implements StudentList {
    private final Random rand = new Random();
    private final List<Student> students;

    /**
     * Creates a new student picker.
     *
     * @param students a list of all students
     */
    public StudentPicker(List<Student> students) {
        this.students = students;
    }

    public List<Student> getStudents() {
        return List.copyOf(students);
    }

    public Student getRandomStudent() {
        if (students.isEmpty()) {
            throw new IllegalStateException("No students in the list.");
        }
        return students.get(rand.nextInt(students.size()));
    }

    public Student getRandomStudentToMark() {
        var result = students.stream()
                .filter(student -> student.getLessonGrade() == null && (student.isPresent() == null || student.isPresent())).toList();
        if (result.isEmpty()) {
            throw new IllegalStateException("All students have been graded.");
        }
        return result.get(rand.nextInt(result.size()));
    }

    public List<Student> getStudentsWithGrades() {
        return students.stream().filter(student -> student.getLessonGrade() != null).toList();
    }
}
