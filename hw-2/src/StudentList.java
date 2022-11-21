import java.util.List;

public interface StudentList {
    /**
     * Returns a random student.
     */
    Student getRandomStudent();

    /**
     * Returns a list of all students who has not received a grade yet.
     */
    Student getRandomStudentToMark();

    /**
     * Returns a list of all students.
     */
    List<Student> getStudents();

    /**
     * Returns a list of all students who received a grade.
     */
    List<Student> getStudentsWithGrades();
}
